package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.payloads.request.LoginRequest;
import com.openclassrooms.mddapi.payloads.request.SignupRequest;
import com.openclassrooms.mddapi.payloads.response.JwtResponse;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest) throws Exception;
    JwtResponse registerUser(SignupRequest signUpRequest) throws Exception;
    Optional<UserDto> getCurrentUser(Authentication auth);
}
