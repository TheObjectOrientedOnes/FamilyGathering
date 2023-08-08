package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<EventModel,Long> {

    public EventModel findById(long id);

    public EventModel deleteById(long id);
}
