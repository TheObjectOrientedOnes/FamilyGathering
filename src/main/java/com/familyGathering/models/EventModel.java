package com.familyGathering.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String eventName;
    LocalDateTime dateOfEvent;
    String organizer;
    @ManyToOne
    FamilyMemberModel eventToFamily;

    List <FamilyMemberModel> eventAttendees = null;

    protected EventModel(){};

    public EventModel(String eventName, LocalDateTime dateOfEvent, String organizer, Long idOfFamily) {
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.organizer = organizer;
        this.eventAttendees = new ArrayList<FamilyMemberModel>();
    }

    boolean addFamilyMemberToEvent(FamilyMemberModel member){
        if (member == null){
            return false;
        }else{
            this.eventAttendees.add(member);
            return true;
        }
    }

    boolean removeFamilyMemberFromEvent(FamilyMemberModel member){
        if (member == null){
            return false;
        }

        this.eventAttendees.remove(member);
        return true;
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


}
