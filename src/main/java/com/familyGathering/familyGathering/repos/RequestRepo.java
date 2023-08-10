package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.RequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepo extends JpaRepository<RequestModel, Long> {

    public RequestModel findByRequestMemberId(Long requestMemberId);
    List<RequestModel> findAllByRequestFamilyId(Long requestFamilyId);
}
