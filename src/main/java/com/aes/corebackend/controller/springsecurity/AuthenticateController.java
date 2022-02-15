package com.aes.corebackend.controller.springsecurity;

import com.aes.corebackend.dto.springsecurity.AuthenticationRequest;
import com.aes.corebackend.dto.springsecurity.AuthenticationResponse;
import com.aes.corebackend.service.springsecurity.CustomUserDetailsService;
import com.aes.corebackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticateController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtTokenUtil;

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> hello() {

        return ResponseEntity.ok("Hello AES.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch(BadCredentialsException e) {
            throw new Exception("Incorrect username or password.", e);
        }
        logger.info("username: "+authenticationRequest.getUsername());
        logger.info("password: "+authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}