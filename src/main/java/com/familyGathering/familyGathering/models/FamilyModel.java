package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FamilyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String familyName;
    int zipcode;

    @OneToMany(mappedBy = "family",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<EventModel> familyEvents;

    @OneToMany(mappedBy = "myFamily", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<FamilyMemberModel> familyMembers;
}
