package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.PostCreateDto;
import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostCreateDto postCreateDto) {
        return postService.createPost(postCreateDto);
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody PostDto postUpdateDto) {
        return postService.updatePost(id, postUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }
}
