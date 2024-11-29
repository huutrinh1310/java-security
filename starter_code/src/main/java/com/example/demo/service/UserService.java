package com.example.demo.service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.model.responses.UserDTO;

public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    UserDTO createUser(CreateUserRequest createUserRequest);

    UserDTO login(LoginRequest loginRequest);

}
