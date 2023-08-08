package com.familyGathering.familyGathering.controllers;

import com.familyGathering.familyGathering.repos.EventRepo;
import com.familyGathering.familyGathering.repos.FamiliesRepo;
import com.familyGathering.familyGathering.repos.FamilyMemberRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/")
    String getHomePage() {

//        String username = p.getName();
//        FamilyMemberModel familyMemberModel = familyMemberRepo.findByUserName(username);

        return "splash.html";
    }

    @GetMapping("/login")
    String getPage(){
        return "splash";
    }

//    @GetMapping("/testA")
//    String getPage(){
//        return "myPage";
//    }
}

