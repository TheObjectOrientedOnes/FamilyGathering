package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FamilyMemberModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long memberId;
    protected String fName;
    protected String lName;
    protected String surname;
    protected String userName;
    protected Integer age;

    //Generate Constructor

    protected FamilyMemberModel(){};

    public FamilyMemberModel(String fName, String lName, String surname, String userName, Integer age, FamilyModel myFamily, Set<EventModel> myFamilyEvents) {
        this.fName = fName;
        this.lName = lName;
        this.surname = surname;
        this.userName = userName;
        this.age = age;
        this.myFamily = myFamily;
        this.myFamilyEvents = myFamilyEvents;
    }

    @ManyToOne
    public FamilyModel myFamily;

    //Many-to-many relationship between family member and the events they are attending
    @ManyToMany(mappedBy = "eventAttendees")
    Set<EventModel> myFamilyEvents = new HashSet<>();

    //Chat GPT Helper Methods
//    public void addEvent(EventModel event) {
//        myFamilyEvents.add(event);
//        event.getEventAttendees().add(this);
//    }
//
//    public void removeEvent(EventModel event) {
//        myFamilyEvents.remove(event);
//        event.getEventAttendees().remove(this);
//    }


    //Generate Getters and Setters

    public Long getMemberId() {
        return memberId;
    }

    public Set<EventModel> getMyFamilyEvents() {
        return myFamilyEvents;
    }

    public void setMyFamilyEvents(Set<EventModel> myFamilyEvents) {
        this.myFamilyEvents = myFamilyEvents;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
