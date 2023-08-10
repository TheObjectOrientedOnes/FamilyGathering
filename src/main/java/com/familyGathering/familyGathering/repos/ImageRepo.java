package com.familyGathering.familyGathering.repos;

import com.familyGathering.familyGathering.models.FamilyMemberModel;
import com.familyGathering.familyGathering.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ImageRepo extends JpaRepository<ImageModel,Long> {

    List<ImageModel> findByImageId(Long memberId);

    //FamilyMemberModel findByFamilyMemberModel(FamilyMemberModel familyMember);

    ArrayList <Optional> findByFamilyMemberModel(FamilyMemberModel familyMemberModel);
}
