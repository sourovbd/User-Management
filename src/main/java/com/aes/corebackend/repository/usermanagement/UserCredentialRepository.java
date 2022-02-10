package com.aes.corebackend.repository.usermanagement;


import com.aes.corebackend.entity.usermanagement.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

    Optional<UserCredential> findByEmployeeId(String employeeId);
}
