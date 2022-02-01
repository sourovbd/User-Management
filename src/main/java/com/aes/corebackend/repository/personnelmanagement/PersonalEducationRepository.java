package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PersonalEducationRepository extends JpaRepository<PersonalEducationInfo, Long> {
    PersonalEducationInfo findPersonalEducationInfoById(Long Id);
    ArrayList<PersonalEducationInfo> findPersonalEducationInfoByUserId(Long userId);
    PersonalEducationInfo findPersonalEducationInfoByIdAndUserId(Long educationId, Long userId);
}
