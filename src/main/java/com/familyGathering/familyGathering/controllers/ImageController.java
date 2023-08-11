package com.familyGathering.familyGathering.controllers;

import com.familyGathering.familyGathering.config.ImageUploadService;
import com.familyGathering.familyGathering.models.FamilyMemberModel;
import com.familyGathering.familyGathering.models.ImageModel;
import com.familyGathering.familyGathering.repos.FamilyMemberRepo;
import com.familyGathering.familyGathering.repos.ImageRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ImageController {
    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private FamilyMemberRepo familyMemberRepo;

    @Autowired
    private ImageRepo imageRepo;

    @GetMapping("/uploadImage")
    public String showUploadForm(Model model, Principal p) {
        String userName = p.getName();
        FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
        model.addAttribute("userId", familyMemberModel.getMemberId());

        return "uploadForm.html";
    }



    @GetMapping("/image")
    @Transactional
    public ResponseEntity<byte[]> getUserImage( Principal p) {
        String userName = p.getName();

        try {
            FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
            if (familyMemberModel != null) {
                ArrayList<Optional> imageOptional = imageRepo.findByFamilyMemberModel(familyMemberModel);
                if (!imageOptional.isEmpty()) {
                    ImageModel image = (ImageModel) imageOptional.get(0).get();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type
                    return new ResponseEntity<>(image.getImageBytes(), headers, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //----------------POST Mapping----------------------------------

    @PostMapping("/imageUpload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Principal p) {
        String userName = p.getName();
        FamilyMemberModel familyMemberModel = familyMemberRepo.findByUsername(userName);
        try {
            familyMemberModel.setHasProfilePicture(true);
            System.out.println("made it here");
            imageUploadService.uploadImage(file, familyMemberModel.getMemberId());
            return "redirect:/myPage";
        } catch (IOException e) {
            return "redirect:/";
        }
    }

}
