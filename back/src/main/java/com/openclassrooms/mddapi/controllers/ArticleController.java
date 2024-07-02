package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleCreateDto;
import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payloads.response.StringResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> getAllPosts() {
        return articleService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ArticleDto getPost(@PathVariable Long id) {
        return articleService.getPostById(id);
    }

  /*  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArticleDto createPost(@RequestBody ArticleCreateDto articleCreateDto) {
        return articleService.createPost(articleCreateDto);
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createPost(
            @RequestParam("title") @NotBlank @Size(max = 63) String title,
            @RequestParam("author") @Min(0) String author,
            @RequestParam("theme") @Min(0) String theme,
            @RequestParam("content") @Size(max = 2000) String content,
            @RequestHeader(value = "Authorization", required = false) String jwt
    ) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
        User owner = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
