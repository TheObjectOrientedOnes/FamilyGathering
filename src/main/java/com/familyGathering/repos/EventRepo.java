package com.familyGathering.repos;

import com.familyGathering.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<EventModel,Long> {

    public EventModel findById(long id);

    public EventModel deleteById(long id);
}
