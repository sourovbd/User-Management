package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalBasicInfoRepository extends JpaRepository<PersonalBasicInfo, Long> {
    public PersonalBasicInfo findPersonalBasicInfoByUser(User user);
}
