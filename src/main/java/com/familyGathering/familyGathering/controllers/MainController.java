package com.familyGathering.familyGathering.controllers;

import com.familyGathering.familyGathering.models.FamilyMemberModel;
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
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    String getHomePage() {

//        String username = p.getName();
//        FamilyMemberModel familyMemberModel = familyMemberRepo.findByUserName(username);
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
            System.out.println(userName + " This is the username");
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            m.addAttribute("userName", userName);
            m.addAttribute("user",familyMemberModel);
//            m.addAttribute("lastName", familyMemberModel.getlName());
        }
        return "myPage.html";
    }

    @GetMapping("/createEvent")
    String getCreateEventPage(Principal p, Model m){
        return "addEvent.html";
    }

    @GetMapping("/admin")
    String getAdminPage(Principal p, Model m){

        return "admin.html";
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


}

