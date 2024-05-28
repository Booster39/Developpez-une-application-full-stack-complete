package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payloads.request.LoginRequest;
import com.openclassrooms.mddapi.payloads.request.SignupRequest;
import com.openclassrooms.mddapi.payloads.response.JwtResponse;
import com.openclassrooms.mddapi.payloads.response.StringResponse;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.securities.jwt.JwtUtils;
import com.openclassrooms.mddapi.securities.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
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


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
      try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
          return ResponseEntity.badRequest().body(new StringResponse("error" ));
        }
        return ResponseEntity.ok(new JwtResponse(jwt));
      } catch (Exception e)
      {
        return ResponseEntity.badRequest().build();
      }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      try {
      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return ResponseEntity
          .badRequest().body("{}");
      }
      // Create new user's account
        User user = new User(
          signUpRequest.getEmail(),
          signUpRequest.getName(),
          passwordEncoder.encode(signUpRequest.getPassword())
        );
        userRepository.save(user);
        return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(user.getEmail())));
      } catch (Exception e)
      {
        return ResponseEntity.badRequest().build();
      }
    }

  @GetMapping("/me")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<Optional<UserDto>> me() {
      try {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
   final Optional<User> user = this.userRepository.findByEmail(auth.getName());
   return  ResponseEntity.ok().body(user.map(this.userMapper::toDto));
      } catch (Exception e)
      {
        return ResponseEntity.badRequest().build();
      }
  }
}
