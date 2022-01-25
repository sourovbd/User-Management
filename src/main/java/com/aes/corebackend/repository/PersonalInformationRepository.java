package com.aes.corebackend.repository;

import com.aes.corebackend.entity.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {

}
