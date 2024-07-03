package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {

    private String authorId;
    private String subjectId;
    private String title;
    private String content;

    // Getters and setters
}
