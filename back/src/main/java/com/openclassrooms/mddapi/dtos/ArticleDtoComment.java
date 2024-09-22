package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDtoComment {
    private Long id;
    private Long author_id;
    private Long theme_id;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private List<CommentDto> comments;
}