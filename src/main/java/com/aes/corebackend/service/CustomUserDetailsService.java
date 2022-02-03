package com.aes.corebackend.service;

import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {

        Optional<UserCredential> user = repository.findByEmployeeId(employeeId);
        logger.info("user: "+user);

        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(employeeId + " doesn't exist in system."));
    }
}
