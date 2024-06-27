package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
