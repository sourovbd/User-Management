package com.aes.corebackend.repository;

import com.aes.corebackend.entity.PersonalBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalBasicInfoRepository extends JpaRepository<PersonalBasicInfo, Long> {
}
