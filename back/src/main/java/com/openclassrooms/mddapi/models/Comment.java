package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
public class Comment {
    @Id
    private Long id;

    @DBRef
    private User author;

    @DBRef
    private Post post;

    private String content;

    @CreatedDate
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
