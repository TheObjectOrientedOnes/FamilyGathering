package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;

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

    public Long getFamilyIdId() {
        return familyId;
    }

    public void setFamilyIdId(Long id) {
        this.familyId = id;
    }


}
