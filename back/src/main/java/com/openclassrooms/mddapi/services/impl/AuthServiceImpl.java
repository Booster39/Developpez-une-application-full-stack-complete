package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payloads.request.LoginRequest;
import com.openclassrooms.mddapi.payloads.request.SignupRequest;
import com.openclassrooms.mddapi.payloads.response.JwtResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the AuthService interface for handling user authentication and registration.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    /**
     * Authenticates a user using their email and password.
     *
     * @param loginRequest the login request containing the user's email and password
     * @return a JwtResponse containing the generated JWT token
     * @throws Exception if authentication fails or the user is not found
     */
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        User user = this.userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        if (user == null) {
            throw new Exception("User not found");
        }

        return new JwtResponse(jwt);
    }

    /**
     * Registers a new user in the system.
     *
     * @param signUpRequest the signup request containing user details
     * @return a JwtResponse containing the generated JWT token
     * @throws Exception if the user already exists
     */
    @Override
    public JwtResponse registerUser(SignupRequest signUpRequest) throws Exception {
        if (userRepository.existsByUsername(signUpRequest.getEmail())) {
            throw new Exception("User already exists");
        }

        User user = new User(
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        userRepository.save(user);
        return new JwtResponse(jwtUtils.generateJwtToken(user.getUsername()));
    }

    /**
     * Retrieves the current authenticated user.
     *
     * @param auth the authentication object containing user details
     * @return an Optional containing the current user's details as UserDto
     */
    @Override
    public Optional<UserDto> getCurrentUser(Authentication auth) {
        final Optional<User> user = this.userRepository.findByUsername(auth.getName());
        return user.map(userMapper::toDto);
    }
}
