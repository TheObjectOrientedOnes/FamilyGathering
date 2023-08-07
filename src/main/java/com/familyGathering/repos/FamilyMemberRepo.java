package com.familyGathering.repos;

import com.familyGathering.models.FamilyMemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepo extends JpaRepository<FamilyMemberModel, Long> {

    public FamilyMemberModel findById(long id);

    public FamilyMemberModel deleteById(long id);
}
