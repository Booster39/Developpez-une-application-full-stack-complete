package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.User;

import java.util.List;

public interface ArticleService {
    ArticleDto getPostById(Long id);
    ArticleDto createPost(String title, Long topicId, String content, User author);
    List<ArticleDto> getAllPosts();
}
