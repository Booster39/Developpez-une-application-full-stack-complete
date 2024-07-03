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
import java.util.Set;

@Document(collection = "posts")
@Data
@Builder
public class Article {
    @Id
    @MongoId
    private String id;

    @CreatedDate
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @DBRef
    private User author;

    @DBRef
    private Theme theme;


    private String title;


    private String content;


    @DBRef
    private Set<Comment> comments;
}
