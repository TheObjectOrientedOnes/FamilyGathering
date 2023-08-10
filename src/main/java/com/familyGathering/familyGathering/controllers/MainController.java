package com.familyGathering.familyGathering.controllers;

import com.familyGathering.familyGathering.models.*;
import com.familyGathering.familyGathering.repos.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    AdminRequestRepo adminRequestRepo;
    @Autowired
    RequestRepo requestRepo;
    @Autowired
    EventRepo eventRepo;
    @Autowired
    FamiliesRepo familiesRepo;
    @Autowired
    FamilyMemberRepo familyMemberRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    String getHomePage() {
        return "splash.html";
    }

    @GetMapping("/signup")
    String getSignupPage() {
        return "signup.html";
    }

    @GetMapping("/login")
    String getPage() {
        return "splash";
    }

    @GetMapping("/myPage")
    String getMyPage(Principal p, Model m) {
        if (p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            List<FamilyModel> allFamilies = familiesRepo.findAll();
            // Get the events the family member has RSVPed to
            Set<EventModel> rsvpEvents = familyMemberModel.getMyFamilyEvents();
            // Sort the events by date
            List<EventModel> sortedEvents = rsvpEvents.stream()
                    .sorted(Comparator.comparing(EventModel::getDateOfEvent))
                    .collect(Collectors.toList());
            RequestModel request = requestRepo.findByRequestMemberId(familyMemberModel.getMemberId());
            String requestStatus = (request != null) ? request.getStatus() : null;

            // Get the admin request status for this user
            AdminRequestModel adminRequest = adminRequestRepo.findByMemberId(familyMemberModel.getMemberId());
            String adminRequestStatus = (adminRequest != null) ? adminRequest.getStatus() : null;

            m.addAttribute("adminRequestStatus", adminRequestStatus);
            m.addAttribute("userName", userName);
            m.addAttribute("user", familyMemberModel);
            m.addAttribute("families", allFamilies);
            m.addAttribute("requestStatus", requestStatus);
            m.addAttribute("rsvpEvents", sortedEvents); // Add the sorted events to the model
        }
        return "myPage.html";
    }

    @GetMapping("/createEvent")
    String getCreateEventPage(Principal p, Model m) {
        if (p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            m.addAttribute("user", familyMemberModel);

            return "addEvent.html";
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    String getAdminPage(Principal p, Model m) {
        if (p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);

            if (familyMemberModel.isAdmin()) {
                Long adminFamilyId = familyMemberModel.getMyFamily().getFamilyId(); // Get the admin's family ID

                // Filter family member access requests by the admin's family ID
                List<RequestModel> familyAccessRequests = requestRepo.findAllByRequestFamilyId(adminFamilyId);

                // Get all admin requests
                List<AdminRequestModel> adminRequests = adminRequestRepo.findAll();

                m.addAttribute("userName", userName);
                m.addAttribute("user", familyMemberModel);
                m.addAttribute("familyName", familyMemberModel.getMyFamily().getFamilyName());
                m.addAttribute("adminStatus", familyMemberModel.isAdmin());
                m.addAttribute("familyAccessRequests", familyAccessRequests);
                m.addAttribute("adminRequests", adminRequests);

                return "admin";
            }
        }

        return "redirect:/myPage";
    }


    @GetMapping("/family/{id}")
    String getFamilyPage(Principal p, Model m, @PathVariable Long id) {
        return "familyPage.html";
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println("Error while logging in.");
            e.printStackTrace();
        }
    }

    @GetMapping("/familyPage")
    public String getFamilyPage(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            FamilyMemberModel familyMember = familyMemberRepo.findByUsername(username);
            FamilyModel family = familyMember.getMyFamily();
            if (family != null) {
                m.addAttribute("familyName", family.getFamilyName());

                // Get the events for this family
                List<EventModel> events = eventRepo.findByFamily(family);
                m.addAttribute("events", events);

                // Get the event IDs that the user has RSVPed to
                Set<Long> rsvpEventIds = familyMember.getMyFamilyEvents().stream()
                        .map(EventModel::getEventId)
                        .collect(Collectors.toSet());
                m.addAttribute("rsvpEventIds", rsvpEventIds); // Add the RSVPed event IDs to the model
            }
        }
        return "familyPage";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new RedirectView("/");
    }

    //need to getMapping for the

    //------------------Post Mappings Below---------------------------

    @PostMapping("/signup")
    public RedirectView signUpFamilyMember(String email, String firstname, String lastname, String username, String password, String surname, String age) {
        FamilyMemberModel familyMemberModel = new FamilyMemberModel(firstname, lastname, surname, username, Integer.parseInt(age), email);
        String encryptedPassword = passwordEncoder.encode(password);
        familyMemberModel.setPassword(encryptedPassword);
        familyMemberRepo.save(familyMemberModel);

//        System.out.println("@Post Mapping: "+ username+ " "+ password);
        authWithHttpServletRequest(username, password);


        return new RedirectView("/myPage");
    }


    @PostMapping("/createFamily")
    public RedirectView createFamily(String familyName, Principal p, Model m) {
        if (p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            if (!familyMemberModel.isAdmin() && familyMemberModel.getMyFamily() == null) {
                FamilyModel familyModel = new FamilyModel(familyName);
                familyMemberModel.setAdmin(true);
                familyMemberModel.setMyFamily(familyModel);
                familyModel.setFamilyMember(familyMemberModel);

                familiesRepo.save(familyModel);
                familyMemberRepo.save(familyMemberModel);

                return new RedirectView("/admin");
            }
        }

        return new RedirectView("/myPage");
    }

    @PostMapping("/addEvent")
    public RedirectView createFamilyEvent(String eventName, String eventDate, String location, Principal p, Model m) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Received eventDate Before Parse: " + eventDate);
        LocalDate dateTime = LocalDate.parse(eventDate, formatter);
        System.out.println("Received eventDate After Parse: " + eventDate);
        if (p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            EventModel newEvent = new EventModel(eventName, dateTime, familyMemberModel.getUsername(), familyMemberModel.getMyFamily().getFamilyId());
            eventRepo.save(newEvent);
            return new RedirectView("/myPage");
        }

        return new RedirectView("/");
    }


    @PostMapping("/requestFamilyAccess")
    public RedirectView requestFamilyAccess(@RequestParam Long familyId, Principal p) {
        FamilyModel requestedFamily = familiesRepo.findById(familyId).orElse(null);
        String username = p.getName();
        FamilyMemberModel familyMember = familyMemberRepo.findByUsername(username);
        if (requestedFamily == null) {
            System.out.println("Family not found");
            return new RedirectView("/error");
        }

        RequestModel request = new RequestModel(null, familyMember.getMemberId(), familyId, true, familyMember.getfName(), familyMember.getlName());
        request.setStatus("Pending");
        requestRepo.save(request);

        return new RedirectView("/myPage");
    }

    @PostMapping("/approveRequest")
    public RedirectView approveRequest(@RequestParam Long requestId) {
        RequestModel request = requestRepo.findById(requestId).orElse(null);
        if (request != null) {
            // Get the FamilyId from the request
            Long familyId = request.getRequestFamilyId();

            // Find the family by its ID
            FamilyModel family = familiesRepo.findById(familyId).orElse(null);

            // Find the requesting user by their ID
            Long memberId = request.getRequestMemberId();
            FamilyMemberModel member = familyMemberRepo.findById(memberId).orElse(null);

            if (family != null && member != null) {
                // Set the family for the requesting user
                member.setMyFamily(family);

                // Save the updated member
                familyMemberRepo.save(member);
            }

            // Set the request status to approved
            request.setStatus("Approved");
            requestRepo.save(request);
        }
        return new RedirectView("/admin"); // Redirect back to the admin page
    }

    @PostMapping("/rsvp")
    public RedirectView rsvpToEvent(@RequestParam Long eventId, Principal p) {
        if (p != null) {
            String username = p.getName();
            FamilyMemberModel familyMember = familyMemberRepo.findByUsername(username);
            EventModel event = eventRepo.findById(eventId).orElse(null);

            if (event != null && familyMember != null) {
                event.addFamilyMemberToEvent(familyMember);
                eventRepo.save(event);
            }
        }
        return new RedirectView("/familyPage"); // Redirect back to the family page
    }

    @PostMapping("/leaveFamily")
    public RedirectView leaveFamily(Principal p) {
        if (p != null) {
            String username = p.getName();
            FamilyMemberModel familyMember = familyMemberRepo.findByUsername(username);

            if (familyMember != null) {
                familyMember.setMyFamily(null);
                familyMemberRepo.save(familyMember);
            }
        }
        return new RedirectView("/myPage");
    }

    @PostMapping("/requestAdminStatus")
    public RedirectView requestAdminStatus(Principal p) {
        if (p != null) {
            String username = p.getName();
            FamilyMemberModel familyMember = familyMemberRepo.findByUsername(username);

            if (familyMember != null) {
                AdminRequestModel adminRequest = new AdminRequestModel();
                adminRequest.setMemberId(familyMember.getMemberId());
                adminRequest.setStatus("Pending");
                adminRequestRepo.save(adminRequest);
            }
        }
        return new RedirectView("/myPage"); // Redirect back to the My Page
    }

    @PostMapping("/approveAdminRequest")
    public RedirectView approveAdminRequest(@RequestParam Long requestId) {
        AdminRequestModel request = adminRequestRepo.findById(requestId).orElse(null);
        if (request != null) {
            Long memberId = request.getMemberId();
            FamilyMemberModel member = familyMemberRepo.findById(memberId).orElse(null);

            if (member != null) {
                member.setAdmin(true); // Grant admin status to the member
                familyMemberRepo.save(member);
            }

            request.setStatus("Approved"); // Update the request status to approved
            adminRequestRepo.save(request);
        }
        return new RedirectView("/admin"); // Redirect back to the admin page
    }


}

