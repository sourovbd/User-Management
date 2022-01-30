package com.aes.corebackend.service;

import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserCredentialRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {

        UserCredential user = repository.findByEmployeeId(employeeId);
        logger.info("employeId: "+employeeId);
        logger.info("user: "+user);
        logger.info("Username/emloyeeId: "+user.getEmployeeId());
        logger.info("Pass hash: "+user.getPassword());
        logger.info("Role: "+user.getRoles());

        if (user.equals(null)) {
            throw  new UsernameNotFoundException("Username is not found.");
        }
        return new CustomUserDetails(user);
    }
}
