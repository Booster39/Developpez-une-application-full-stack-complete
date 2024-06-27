package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostCreateDto;
import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto createPost(PostCreateDto postCreateDto) {
        Post post = modelMapper.map(postCreateDto, Post.class);
        post.setCreated_at(LocalDateTime.now());
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto updatePost(Long id, PostDto postUpdateDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        modelMapper.map(postUpdateDto, post);
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
