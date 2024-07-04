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
    private Long authorId;
    private Long articleId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
