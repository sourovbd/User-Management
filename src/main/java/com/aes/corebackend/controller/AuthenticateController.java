package com.aes.corebackend.controller;

import com.aes.corebackend.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

   /* @Autowired
    private AuthenticationManager authenticationManager;*/

    @Autowired
    private MyUserDetailsService userDetailsService;

    /*@Autowired
    private JwtUtil jwtTokenUtil;*/

    @GetMapping("/hello")
    public String hello() {
        return "Hello AES.";
    }
    /*@PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPassword(), authenticationRequest.getPassword()));
        } catch(BadCredentialsException e) {
             throw new Exception("Incorrect username or password.", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }*/

}
