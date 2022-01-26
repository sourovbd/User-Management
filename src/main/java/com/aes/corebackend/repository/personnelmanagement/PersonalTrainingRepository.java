package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTrainingRepository extends JpaRepository<PersonalTrainingInfo, Long> {
}
