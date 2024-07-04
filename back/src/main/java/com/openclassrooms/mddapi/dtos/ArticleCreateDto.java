package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {

    private Long author_id;
    private Long subject_id;
    private String title;
    private String content;

    // Getters and setters
}
