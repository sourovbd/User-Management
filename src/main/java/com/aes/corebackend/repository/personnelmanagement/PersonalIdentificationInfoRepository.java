package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalIdentificationInfoRepository extends JpaRepository<PersonalIdentificationInfo, Long> {
}
