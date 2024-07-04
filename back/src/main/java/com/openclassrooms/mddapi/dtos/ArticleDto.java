package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private Long author_id;
    private Long subject_id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Set<CommentDto> comments;
}

