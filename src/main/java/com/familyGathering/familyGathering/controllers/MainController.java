package com.familyGathering.familyGathering.controllers;

import com.familyGathering.familyGathering.models.EventModel;
import com.familyGathering.familyGathering.models.FamilyMemberModel;
import com.familyGathering.familyGathering.models.FamilyModel;
import com.familyGathering.familyGathering.repos.EventRepo;
import com.familyGathering.familyGathering.repos.FamiliesRepo;
import com.familyGathering.familyGathering.repos.FamilyMemberRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MainController {
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
    String getSignupPage(){
        return "signup.html";
    }

    @GetMapping("/login")
    String getPage(){
        return "splash";
    }

    @GetMapping("/myPage")
    String getMyPage(Principal p, Model m){
        if(p != null){
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            List<FamilyModel> allFamilies = familiesRepo.findAll();
            m.addAttribute("userName", userName);
            m.addAttribute("user",familyMemberModel);
            m.addAttribute("families", allFamilies);
        }
        return "myPage.html";
    }

    @GetMapping("/createEvent")
    String getCreateEventPage(Principal p, Model m){
        if(p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            m.addAttribute("user", familyMemberModel);

            return "addEvent.html";
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    String getAdminPage(Principal p, Model m){
        if(p != null) {
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);

            if (familyMemberModel.isAdmin()) {
                m.addAttribute("userName", userName);
                m.addAttribute("user", familyMemberModel);

                FamilyModel adminCreatedFamily = familyMemberModel.getMyFamily();
                if (adminCreatedFamily != null ){
                    m.addAttribute("familyName", adminCreatedFamily.getFamilyName());
                    m.addAttribute("adminStatus", familyMemberModel.isAdmin());
                }

                return "admin";
            }
        }

        return "redirect:/myPage";
    }

    @GetMapping("/family/{id}")
    String getFamilyPage(Principal p, Model m, @PathVariable Long id){
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

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new RedirectView("/");
    }

    //need to getMapping for the

    //------------------Post Mappings Below---------------------------

    @PostMapping("/signup")
    public RedirectView signUpFamilyMember(String email,String firstname, String lastname,String username ,String password, String surname, String age){
        FamilyMemberModel familyMemberModel = new FamilyMemberModel(firstname,lastname,surname,username,Integer.parseInt(age),email);
        String encryptedPassword = passwordEncoder.encode(password);
        familyMemberModel.setPassword(encryptedPassword);
        familyMemberRepo.save(familyMemberModel);

//        System.out.println("@Post Mapping: "+ username+ " "+ password);
        authWithHttpServletRequest(username, password);


        return new RedirectView("/myPage");
    }


    @PostMapping("/createFamily")
    public RedirectView createFamily(String familyName, Principal p, Model m){
        if (p != null ){
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            if(!familyMemberModel.isAdmin()&& familyMemberModel.getMyFamily() ==null){
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
    public RedirectView createFamilyEvent(String eventName, String eventDate, String location, Principal p, Model m){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Received eventDate Before Parse: " + eventDate);
        LocalDate dateTime = LocalDate.parse(eventDate, formatter);
        System.out.println("Received eventDate After Parse: " + eventDate);
        if (p != null){
            String userName = p.getName();
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            EventModel newEvent = new EventModel(eventName,dateTime,familyMemberModel.getUsername(),familyMemberModel.getMyFamily().getFamilyId());
            eventRepo.save(newEvent);
            return new RedirectView("/myPage");
        }

        return new RedirectView("/");
    }


    @GetMapping("/familyPage")
    public String getFamilyPage(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            FamilyMemberModel familyMember = familyMemberRepo.findByUsername(username);
            FamilyModel family = familyMember.getMyFamily();
            if (family != null) {
                m.addAttribute("familyName", family.getFamilyName());
                m.addAttribute("events", family.getFamilyEvents());
            }
        }
        return "familyPage";
    }


}

