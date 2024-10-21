package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentDto createComment(Long authorId, Long articleId, String content) {
        Comment comment = Comment.builder()
                .article(articleRepository.findById(articleId)
                        .orElseThrow(() -> new RuntimeException("Article not found")))
                .author(userRepository.findById(authorId)
                        .orElseThrow(() -> new RuntimeException("Author not found")))
                .content(content)
                .created_at(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
