package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PersonalJobExperienceRepository extends JpaRepository<PersonalJobExperience, Long> {
    PersonalJobExperience findPersonalJobExperienceById(Long Id);
    ArrayList<PersonalJobExperience> findPersonalJobExperiencesByUserId(Long Id);
    PersonalJobExperience findPersonalJobExperienceByIdAndUserId(Long experienceId, Long userId);
}
