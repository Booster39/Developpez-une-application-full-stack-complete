package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.payloads.response.CommentResponse;
import com.openclassrooms.mddapi.payloads.response.StringResponse;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentResponse commentResponse) {
        try {
            CommentDto commentDto = commentService.createComment(commentResponse.getAuthorId(), commentResponse.getArticleId(), commentResponse.getContent());
            return new ResponseEntity<>(new StringResponse("Message sent with success"), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<HashMap<String, List<CommentDto>>> getAllComments() {
        try {
            List<CommentDto> commentDtos = commentService.getAllComments();
            var response = new HashMap<String, List<CommentDto>>();
            response.put("comments", commentDtos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
