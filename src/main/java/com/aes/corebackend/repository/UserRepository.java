package com.aes.corebackend.repository;

import com.aes.corebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //User save(User user);
    //User getUserBy(long id);
    //User findOne(int employeeId);
    @Query("select u from User u where u.employeeId=:employeeId")
    public User findByEmployeeId(@Param("employeeId") String employeeId);
}
