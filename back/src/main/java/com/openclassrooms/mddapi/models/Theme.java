package com.openclassrooms.mddapi.models;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Document(collection = "subjects")
@Data
@Builder
public class Theme {
    @MongoId
    @Id
    private String id;

    private String name;

    @DBRef
    private Set<Article> articles;

    @DBRef
    private Set<Subscription> subscriptions;
}
