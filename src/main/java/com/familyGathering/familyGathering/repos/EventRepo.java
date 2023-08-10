package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.EventModel;
import com.familyGathering.familyGathering.models.FamilyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<EventModel,Long> {

    public EventModel findById(long id);

    public EventModel deleteById(long id);

    List<EventModel> findByFamily(FamilyModel family);

}
