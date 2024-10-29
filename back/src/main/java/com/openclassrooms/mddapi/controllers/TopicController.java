package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@Tag(name = "Topics", description = "API pour la gestion des sujets")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Operation(summary = "Obtenir un sujet par ID", description = "Récupère un sujet spécifique en utilisant son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sujet récupéré avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de format de requête"),
            @ApiResponse(responseCode = "404", description = "Sujet non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getSubject(@PathVariable String id)
    {
        try {
            TopicDto topicDto = topicService.getSubjectById(Long.valueOf(id));
            if (topicDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(topicDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Récupérer tous les sujets", description = "Récupère la liste de tous les sujets disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des sujets récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la récupération des sujets")
    })
    @GetMapping
    public  ResponseEntity<HashMap<String, List<TopicDto>>> getAllSubjects() {
        try {
            List<TopicDto> topicDtos = topicService.getAllSubjects();
            var response = new HashMap<String, List<TopicDto>>();
            response.put("topics", topicDtos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
