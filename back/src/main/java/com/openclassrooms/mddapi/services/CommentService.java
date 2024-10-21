package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long authorId, Long articleId, String content);
    List<CommentDto> getAllComments();
}
