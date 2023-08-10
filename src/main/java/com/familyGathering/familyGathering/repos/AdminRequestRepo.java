package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.AdminRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRequestRepo extends JpaRepository<AdminRequestModel, Long> {
    AdminRequestModel findByMemberId(Long memberId);
}
