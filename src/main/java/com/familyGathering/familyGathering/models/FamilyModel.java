package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class FamilyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long familyId;

    String familyName;
    int zipcode;

    @OneToMany(mappedBy = "family",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<EventModel> familyEvents;

    @OneToMany(mappedBy = "myFamily", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FamilyMemberModel> familyMembers;

    protected FamilyModel(){};

    public FamilyModel(String familyName) {
        this.familyName = familyName;
        this.familyEvents = new ArrayList<>();
        this.familyMembers = new ArrayList<>();
    }


    public Long getFamilyId() {
        return this.familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public List<EventModel> getFamilyEvents() {
        return this.familyEvents;
    }

    public void setFamilyEvents(List<EventModel> familyEvents) {
        this.familyEvents = familyEvents;
    }

    public List<FamilyMemberModel> getFamilyMembers() {
        return this.familyMembers;
    }

    public void setFamilyMembers(List<FamilyMemberModel> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public void setFamilyMember(FamilyMemberModel familyMember){
        this.familyMembers.add(familyMember);
    }
}
