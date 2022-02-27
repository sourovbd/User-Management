package com.aes.corebackend.service.permission;

import com.aes.corebackend.entity.usermanagement.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Identification {
    public boolean isAuthorized(User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isSameUser = user.getEmployeeId().equals(userDetails.getUsername());
        boolean isSysAdmin = userDetails.getAuthorities().stream().findAny().get().toString().equals("SYS_ADMIN");
        if (!isSameUser && !isSysAdmin) {
            return false;
        }
        else return true;
    }
}
