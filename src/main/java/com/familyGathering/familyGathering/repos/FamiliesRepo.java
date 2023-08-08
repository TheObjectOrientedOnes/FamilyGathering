package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.FamilyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliesRepo extends JpaRepository<FamilyModel,Long> {
}
