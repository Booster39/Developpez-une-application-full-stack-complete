package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private Long author_id;
    private Long theme_id;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}