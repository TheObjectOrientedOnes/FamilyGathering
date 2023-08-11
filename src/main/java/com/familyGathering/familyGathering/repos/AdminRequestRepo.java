package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.AdminRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRequestRepo extends JpaRepository<AdminRequestModel, Long> {
    AdminRequestModel findByMemberId(Long memberId);
    List<AdminRequestModel> findAllByFamilyId(Long familyId);

}
