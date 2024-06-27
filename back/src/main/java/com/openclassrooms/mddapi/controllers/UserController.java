package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.UserCreateDto;
import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.dtos.UserUpdateDto;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Log4j2
@Tag(name = "Users", description = "API pour la gestion des utilisateurs")
public class UserController {

  @Autowired
  private UserService userService;


  @Operation(summary = "Obtenir un utilisateur par ID", description = "Retourne un utilisateur spécifique basé sur l'ID fourni.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Détails de l'utilisateur",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                  content = @Content)
  })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> findById(@PathVariable("id") String id) {
    try {
      UserDto userDto = userService.getUserById(Long.valueOf(id));
      if (userDto == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(userDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
    return userService.createUser(userCreateDto);
  }

  @PutMapping("/{id}")
  public UserDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
    return userService.updateUser(id, userUpdateDto);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }
}
