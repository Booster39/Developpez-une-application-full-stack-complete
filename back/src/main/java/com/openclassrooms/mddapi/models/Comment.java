package com.openclassrooms.mddapi.models;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
@Builder
public class Comment {
    @Id
    @MongoId
    private String id;

    @DBRef
    private User author;

    @DBRef
    private Article article;

    private String content;

    @CreatedDate
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
