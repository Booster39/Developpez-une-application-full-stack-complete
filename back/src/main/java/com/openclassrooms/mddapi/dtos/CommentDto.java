package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @MongoId
    @Id
    private String id;
    private String authorId;
    private String postId;
    private String content;
    private LocalDateTime createdAt;
}

