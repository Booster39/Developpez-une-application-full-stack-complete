package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long author_id;
    private Long article_id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
