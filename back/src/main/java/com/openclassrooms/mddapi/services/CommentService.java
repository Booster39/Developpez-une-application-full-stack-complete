package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentCreateDto;
import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
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

    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        return modelMapper.map(comment, CommentDto.class);
    }

    public CommentDto createComment(CommentCreateDto commentCreateDto) {
        Comment comment = modelMapper.map(commentCreateDto, Comment.class);
        comment.setCreated_at(LocalDateTime.now());
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    public CommentDto updateComment(Long id, CommentDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        modelMapper.map(commentUpdateDto, comment);
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}