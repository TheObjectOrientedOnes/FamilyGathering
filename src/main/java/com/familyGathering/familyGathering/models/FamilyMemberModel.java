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
    protected String username;
    protected Integer age;
    protected String password;

    protected String email;

    protected boolean isAdmin;



    //Generate Constructor

    protected FamilyMemberModel(){};

    public FamilyMemberModel(String fName, String lName, String surname, String username, Integer age, String email) {
        this.fName = fName;
        this.lName = lName;
        this.surname = surname;
        this.username = username;
        this.age = age;
        this.email = email;
        this.myFamily = null;
        this.myFamilyEvents = new HashSet<>();
        this.isAdmin = false;
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


    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FamilyModel getMyFamily() {
        return this.myFamily;
    }

    public void setMyFamily(FamilyModel myFamily) {
        this.myFamily = myFamily;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public Set<EventModel> getMyFamilyEvents() {
        return this.myFamilyEvents;
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
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    @Override
    public String toString() {
        return "FamilyMemberModel{" +
                "memberId=" + memberId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + username + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", myFamily=" + myFamily +
                ", myFamilyEvents=" + myFamilyEvents +
                '}';
    }
}
