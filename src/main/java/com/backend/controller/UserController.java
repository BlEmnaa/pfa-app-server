package com.backend.controller;



import com.backend.payload.*;

import com.backend.repository.UserRepository;
import com.backend.security.services.UserPrinciple;
import com.backend.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrinciple currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getFullName(), 
        		currentUser.getEmail(), currentUser.getBirthDate(), currentUser.getAddress());
        return userSummary;
    }
}
