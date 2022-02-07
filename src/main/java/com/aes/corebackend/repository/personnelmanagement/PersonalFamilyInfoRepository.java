package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalFamilyInfoRepository extends JpaRepository<PersonalFamilyInfo, Long> {
    public PersonalFamilyInfo findPersonalFamilyInfoByUserId(Long UserId);

}
