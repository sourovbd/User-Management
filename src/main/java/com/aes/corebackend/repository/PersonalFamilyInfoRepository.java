package com.aes.corebackend.repository;

import com.aes.corebackend.entity.PersonalFamilyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalFamilyInfoRepository extends JpaRepository<PersonalFamilyInfo, Long> {
}
