package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    private Long authorId;
    private Long postId;
    private String content;

    // Getters and setters
}
