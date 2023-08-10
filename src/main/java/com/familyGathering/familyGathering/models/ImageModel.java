package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;

@Entity
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;
    String imageName;
    @Lob
    byte[] imageBytes;

    @ManyToOne
    @JoinColumn(name = "memberId")
    FamilyMemberModel familyMemberModel;

    public ImageModel(){};

    public ImageModel(String imageName, byte[] imageBytes, FamilyMemberModel familyMemberModel) {
        this.imageName = imageName;
        this.imageBytes = imageBytes;
        this.familyMemberModel = familyMemberModel;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageBytes() {
        return this.imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public FamilyMemberModel getFamilyMemberModel() {
        return this.familyMemberModel;
    }

    public void setFamilyMemberModel(FamilyMemberModel familyMemberModel) {
        this.familyMemberModel = familyMemberModel;
    }

    public Long getImageId() {
        return this.imageId;
    }
}
