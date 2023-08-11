package com.familyGathering.familyGathering.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AdminRequestModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long memberId;

    public Long familyId;
    public String status; // Pending, Approved, etc.
    public String fName;
    public String lName;

    public AdminRequestModel() {
    }

    public AdminRequestModel(Long id, Long memberId, String status) {
        this.id = id;
        this.memberId = memberId;
        this.status = status;
    }

    // Getters and Setters


    public Long getFamilyId() {
        return this.familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getfName() {
        return this.fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return this.lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminRequstModel{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", status='" + status + '\'' +
                '}';
    }
}


