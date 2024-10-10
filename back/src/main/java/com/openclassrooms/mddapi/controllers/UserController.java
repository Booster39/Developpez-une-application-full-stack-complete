package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.services.TopicService;
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

@RestController
@RequestMapping("/api/user")
@Log4j2
@Tag(name = "Users", description = "API pour la gestion des utilisateurs")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private TopicService topicService;


  @Operation(summary = "Obtenir un utilisateur par ID", description = "Retourne un utilisateur spécifique basé sur l'ID fourni.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Détails de l'utilisateur",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                  content = @Content)
  })
  @GetMapping(value = "/{me}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> findById(@PathVariable("me") String id) {
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

  @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour les détails d'un utilisateur existant basé sur l'ID fourni.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                  content = @Content)
  })

  @PutMapping(value = "/{me}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> updateUser(@PathVariable("me") String id, @RequestBody UserDto userDto) {
    try {
      UserDto updatedUser = userService.updateUser(Long.valueOf(id), userDto);
      return ResponseEntity.ok().body(updatedUser);
    } catch (NumberFormatException e) {
      log.error("Invalid ID format: {}", id, e);
      return ResponseEntity.badRequest().build();
    } catch (RuntimeException e) {
      log.error("User not found with ID: {}", id, e);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      log.error("Error updating user with ID: {}", id, e);
      return ResponseEntity.status(500).build();
    }
  }




 /**
 * Like a topic by its id.
 *
 * @param id The id of the topic.
 * @return Empty no content response.
 follow
**/
@PutMapping("/users/me/topics/{id}")
public ResponseEntity<?> likeTopic(@PathVariable long id) {
        topicService.likeTopic(id);
        return ResponseEntity.noContent().build();
        }


/**
 * Dislike a topic by its id.
 *
 * @param id The id of the topic.
 * @return Empty no content response.
 **/
@DeleteMapping("/users/me/topics/{id}")
public ResponseEntity<?> dislikeTopic(@PathVariable long id) {
        topicService.dislikeTopic(id);
        return ResponseEntity.noContent().build();
        }
}