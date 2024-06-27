package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    private Long authorId;
    private Long subjectId;
    private String title;
    private String content;

    // Getters and setters
}
