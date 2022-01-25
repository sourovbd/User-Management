package com.aes.corebackend.repository;

import com.aes.corebackend.entity.PersonalAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAddressInfoRepository extends JpaRepository<PersonalAddressInfo, Long> {
}
