package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentCreateDto;
import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public CommentDto createComment(@RequestBody CommentCreateDto commentCreateDto) {
        return commentService.createComment(commentCreateDto);
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentDto commentUpdateDto) {
        return commentService.updateComment(id, commentUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }
}
