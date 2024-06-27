package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.models.User;

import com.openclassrooms.mddapi.payloads.request.LoginRequest;
import com.openclassrooms.mddapi.payloads.request.SignupRequest;
import com.openclassrooms.mddapi.payloads.response.JwtResponse;
import com.openclassrooms.mddapi.payloads.response.StringResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
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

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API pour l'authentification et l'inscription des utilisateurs")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;


  @Operation(summary = "Authentifier l'utilisateur", description = "Authentifie l'utilisateur et retourne un token JWT.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Authentification réussie",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide",
      content = @Content)
  })
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
        return ResponseEntity.badRequest().body(new StringResponse("error"));
      }
      return ResponseEntity.ok(new JwtResponse(jwt));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Crée un nouvel utilisateur et retourne un token JWT.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Inscription réussie",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide ou utilisateur déjà existant",
      content = @Content)
  })
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    try {
      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return ResponseEntity.badRequest().body("{}");
      }
      // Create new user's account
      User user = new User(
        signUpRequest.getEmail(),
        signUpRequest.getName(),
        passwordEncoder.encode(signUpRequest.getPassword())
      );
      userRepository.save(user);
      return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(user.getEmail())));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Obtenir les informations de l'utilisateur authentifié", description = "Retourne les informations de l'utilisateur actuellement authentifié.")
  @SecurityRequirement(name = "Bearer Authentication")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Informations de l'utilisateur",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide",
      content = @Content)
  })
  @GetMapping("/me")
  public ResponseEntity<Optional<UserDto>> me() {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      final Optional<User> user = this.userRepository.findByEmail(auth.getName());

      // Map User to UserDto using modelMapper
      Optional<UserDto> userDto = user.map(u -> modelMapper.map(u, UserDto.class));

      return ResponseEntity.ok().body(userDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
