package com.example.demo.controllers;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.model.responses.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request");
        return ResponseEntity.ok(userService.login(loginRequest));

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody CreateUserRequest createUserRequest) {
        log.info("Signup request");
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

}
