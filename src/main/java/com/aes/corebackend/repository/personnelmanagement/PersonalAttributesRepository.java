package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAttributesRepository extends JpaRepository<PersonalAttributes, Long> {
    public PersonalAttributes findPersonalAttributesById(Long id);
}
