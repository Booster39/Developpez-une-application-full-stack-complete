package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentCreateDto;
import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public CommentDto createComment(String authorId, String postId, String content) {
        Comment comment = Comment.builder()
                .article(articleRepository.findById(postId).orElseThrow(() -> new RuntimeException("Article not found")))
                .author(userRepository.findById(postId).orElseThrow(() -> new RuntimeException("Author not found")))
                .content(content)
                .build();
        comment.setCreated_at(LocalDateTime.now());
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }


    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}