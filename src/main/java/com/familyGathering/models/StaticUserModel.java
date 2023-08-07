package com.familyGathering.models;

import jakarta.persistence.*;

@Entity
public abstract class StaticUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long familyId;
    String fName;
    String lName;
    String surname;
    String userName;
    Integer age;

    @ManyToOne
    FamilyMemberModel myFamily;


    public StaticUserModel() {
    }

    //Generate Constructor

    public StaticUserModel(String fName, String lName, String surname, String userName, Integer age) {
        this.fName = fName;
        this.lName = lName;
        this.surname = surname;
        this.userName = userName;
        this.age = age;
    }


    //Generate Getters and Setters


    public Long getFamilyId() {
        return familyId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    //Generate ToString Overide


    @Override
    public String toString() {
        return "StaticUserModel{" +
                "familyId=" + familyId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
