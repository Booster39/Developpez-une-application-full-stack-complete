package com.openclassrooms.mddapi.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private Long authorId;
    private Long subjectId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Set<CommentDto> comments;
}

