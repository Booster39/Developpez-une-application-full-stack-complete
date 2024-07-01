package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleCreateDto;
import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id}")
    public ArticleDto getPost(@PathVariable Long id) {
        return articleService.getPostById(id);
    }

    @PostMapping
    public ArticleDto createPost(@RequestBody ArticleCreateDto articleCreateDto) {
        return articleService.createPost(articleCreateDto);
    }

    @PutMapping("/{id}")
    public ArticleDto updatePost(@PathVariable Long id, @RequestBody ArticleDto postUpdateDto) {
        return articleService.updatePost(id, postUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        articleService.deletePost(id);
    }

    @GetMapping
    public List<ArticleDto> getAllPosts() {
        return articleService.getAllPosts();
    }
}
