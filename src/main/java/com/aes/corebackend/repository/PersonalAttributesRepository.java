package com.aes.corebackend.repository;

import com.aes.corebackend.entity.PersonalAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAttributesRepository extends JpaRepository<PersonalAttributes, Long> {
}
