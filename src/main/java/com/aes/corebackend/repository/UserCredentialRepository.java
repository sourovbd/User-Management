package com.aes.corebackend.repository;


import com.aes.corebackend.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    @Query("select u from UserCredential u where u.employeeId=:employeeId")
    public UserCredential findByEmployeeId(@Param("employeeId") String employeeId);
}
