package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.payloads.response.CommentResponse;
import com.openclassrooms.mddapi.payloads.response.StringResponse;
import com.openclassrooms.mddapi.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "API pour la gestion des commentaires")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Créer un commentaire", description = "Ajoute un nouveau commentaire sur un article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commentaire créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur dans les données envoyées")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentResponse commentResponse) {
        try {
            CommentDto commentDto = commentService.createComment(commentResponse.getAuthor_id(), commentResponse.getArticle_id(), commentResponse.getContent());
            return new ResponseEntity<>(new StringResponse("Message sent with success"), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Récupérer tous les commentaires", description = "Récupère la liste de tous les commentaires disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des commentaires récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la récupération des commentaires")
    })
    @GetMapping
    public ResponseEntity<HashMap<String, List<CommentDto>>> getAllComments() {
        try {
            List<CommentDto> commentDtos = commentService.getAllComments();
            var response = new HashMap<String, List<CommentDto>>();
            response.put("comments", commentDtos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
