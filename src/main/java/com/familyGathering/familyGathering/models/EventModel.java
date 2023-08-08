package com.familyGathering.familyGathering.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long eventId;

    String eventName;
    LocalDateTime dateOfEvent;
    String organizer;
    @ManyToOne
    @JoinColumn(name = "familyId")
    FamilyModel family;


    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "events_to_family_members",
            joinColumns = {@JoinColumn(name = "eventId")},
            inverseJoinColumns = {@JoinColumn(name="memberId")}
    )
    Set<FamilyMemberModel> eventAttendees = new HashSet<>();


//      Chat GPT helper methods
//    public void addFamilyMember(FamilyMemberModel member) {
//        eventAttendees.add(member);
//        member.getMyFamilyEvents().add(this);
//    }
//
//    public void removeFamilyMember(FamilyMemberModel member) {
//        eventAttendees.remove(member);
//        member.getMyFamilyEvents().remove(this);
//    }

    protected EventModel(){};

    public EventModel(String eventName, LocalDateTime dateOfEvent, String organizer, Long idOfFamily) {
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.organizer = organizer;
        if (idOfFamily != null){
            FamilyModel familyModel = new FamilyModel();
            familyModel.setFamilyIdId(idOfFamily);
            this.family = familyModel;

        }
//        this.eventAttendees = new ArrayList<FamilyMemberModel>();
    }

    boolean addFamilyMemberToEvent(FamilyMemberModel member){
        if (member == null){
            return false;
        }
        boolean added = eventAttendees.add(member);

        if(added){
            member.getMyFamilyEvents().add(this);
        }
        return added;
    }

    boolean removeFamilyMemberFromEvent(FamilyMemberModel member){
        if (member == null){
            return false;
        }

        boolean removed = eventAttendees.remove(member);
        if (removed) {
            member.getMyFamilyEvents().remove(this);
        }

        return removed;


    }

    boolean changeDateOfEvent(LocalDateTime newDate){
        LocalDateTime now = LocalDateTime.now();
        if (newDate.isBefore(now)){
            return false;
        }else{
            this.dateOfEvent = newDate;
            return true;
        }
    }

    public long getEventId() {
        return this.eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getDateOfEvent() {
        return this.dateOfEvent;
    }

    public void setDateOfEvent(LocalDateTime dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public String getOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public FamilyModel getFamily() {
        return this.family;
    }

    public void setFamily(FamilyModel family) {
        this.family = family;
    }

    public Set<FamilyMemberModel> getEventAttendees() {
        return this.eventAttendees;
    }

    public void setEventAttendees(Set<FamilyMemberModel> eventAttendees) {
        this.eventAttendees = eventAttendees;
    }
}
