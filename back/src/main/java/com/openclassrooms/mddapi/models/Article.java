package com.openclassrooms.mddapi.models;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "articles")
@Data
public class Article {
    @Id
    private Long id;

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
