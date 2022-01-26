package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAddressInfoRepository extends JpaRepository<PersonalAddressInfo, Long> {
}
