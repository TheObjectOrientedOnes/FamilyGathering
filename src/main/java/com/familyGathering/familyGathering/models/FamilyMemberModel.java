package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
public class FamilyMemberModel extends StaticUserModel implements UserDetails {

    // Need a Family Member Id as well
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberId;

    @ManyToOne
    public FamilyModel myFamily;

    //Many to many relationship between family member and the events they are attending

    public FamilyMemberModel() {
        super();
    }

    //Generate Constructor

    public FamilyMemberModel(Long memberId) {
        this.memberId = memberId;
    }

    public FamilyMemberModel(String fName, String lName, String surname, String userName, Integer age, FamilyModel myFamily) {
        super(fName, lName, surname, userName, age);
        this.myFamily = myFamily;

    }


    //Generate Getters and Setters

    public Long getMemberId() {
        return memberId;
    }


    //Generate ToString Overide IF WE GET STACKOVERFLOW ERRORS CHECK HERE


    @Override
    public String toString() {
        return "FamilyMemberModel{" +
                "memberId=" + memberId +
                ", familyId=" + familyId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
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
