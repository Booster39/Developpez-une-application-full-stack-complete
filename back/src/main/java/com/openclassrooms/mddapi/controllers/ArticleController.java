package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payloads.response.StringResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Articles", description = "API pour la gestion des articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Obtenir tous les articles", description = "Récupère la liste de tous les articles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la récupération des articles")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, List<ArticleDto>>> getAllPosts()
    {
        try {
            List<ArticleDto> articleDtos = articleService.getAllPosts();
            var response = new HashMap<String, List<ArticleDto>>();
            response.put("articles", articleDtos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Obtenir un article par ID", description = "Récupère un article spécifique en utilisant son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article récupéré avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de format de requête"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> getPost(@PathVariable String id)
    {
        try {
            ArticleDto articleDto = articleService.getPostById(Long.valueOf(id));
            if (articleDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(articleDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Créer un nouvel article", description = "Crée un nouvel article en utilisant les informations fournies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la création de l'article")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> createPost(
            @RequestParam("title") @NotBlank @Size(max = 63) String title,
            @RequestParam("topic_id") @Min(0) Long topic_id,
            @RequestParam("content") @Size(max = 2000) String content,
            @RequestHeader(value = "Authorization", required = false) String jwt
    ) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
            User author = this.userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            ArticleDto articleDto = articleService.createPost(title, topic_id, content, author);
            return ResponseEntity.ok().body(new StringResponse("Article created !"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
