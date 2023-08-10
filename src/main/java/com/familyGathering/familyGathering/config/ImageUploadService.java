package com.familyGathering.familyGathering.config;

import com.familyGathering.familyGathering.models.FamilyMemberModel;
import com.familyGathering.familyGathering.models.ImageModel;
import com.familyGathering.familyGathering.repos.FamilyMemberRepo;
import com.familyGathering.familyGathering.repos.ImageRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageUploadService {

    @Autowired
    ImageRepo imageRepo;

    @Autowired
    FamilyMemberRepo familyMemberRepo;

    public void uploadImage(MultipartFile file, Long userId) throws IOException {
        FamilyMemberModel user = familyMemberRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        ImageModel image = new ImageModel();
        image.setFamilyMemberModel(user);
        image.setImageName(file.getOriginalFilename());
        image.setImageBytes(file.getBytes());

        imageRepo.save(image);
    }
}
