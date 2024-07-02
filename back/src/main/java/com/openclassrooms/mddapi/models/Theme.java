package com.openclassrooms.mddapi.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Set;

@Document(collection = "subjects")
@Data
public class Theme {
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @DBRef
    private Set<Article> articles;

    @DBRef
    private Set<Subscription> subscriptions;
}
