package com.example.demo.service.impl;

import com.example.demo.exception.PasswordInvalid;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.model.responses.UserDTO;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final static int PASSWORD_MIN_SIZE = 7;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public UserDTO createUser(CreateUserRequest createUserRequest) {
        if (isPasswordValid(createUserRequest.getPassword())) {
            throw new PasswordInvalid("Password is invalid");
        }
        User user = new User(createUserRequest.getUsername(), passwordEncoder.encode(createUserRequest.getPassword()));

        Cart cart = new Cart();
        user.setCart(cartRepository.save(cart));
        User saved = userRepository.save(user);

        UserDTO userDTO = UserDTO.builder()
                .user(saved)
                .build();

        return userDTO;
    }

    @Override
    public UserDTO login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authentication;
        authentication = authenticationManager.authenticate(authenticationToken);
        User user = findByUsername(loginRequest.getUsername());

        UserDTO userDTO = UserDTO.builder()
                .user(user)
                .accessToken(jwtService.generateToken(authentication))
                .build();
        return userDTO;
    }

    private Boolean isPasswordValid(String password) {
        return password.length() < PASSWORD_MIN_SIZE;
    }
}
