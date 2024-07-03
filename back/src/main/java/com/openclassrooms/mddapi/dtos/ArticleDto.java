package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    @MongoId
    @Id
    private String id;
    private String authorId;
    private String subjectId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Set<CommentDto> comments;
}

