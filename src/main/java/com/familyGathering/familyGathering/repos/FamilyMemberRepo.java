package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.FamilyMemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepo extends JpaRepository<FamilyMemberModel, Long> {

    public FamilyMemberModel findByMemberId(long id);

    public FamilyMemberModel deleteByMemberId(long id);

    public FamilyMemberModel findByUsername(String userName);
}
