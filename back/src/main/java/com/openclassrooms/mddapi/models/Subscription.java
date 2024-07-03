package com.openclassrooms.mddapi.models;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document(collection = "subscriptions")
@Data
@Builder
public class Subscription {
    @MongoId
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Theme theme;
}
