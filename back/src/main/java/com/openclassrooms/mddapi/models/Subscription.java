package com.openclassrooms.mddapi.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document(collection = "subscriptions")
@Data
public class Subscription {
    @Id
    private Long id;

    @DBRef
    private User user;

    @DBRef
    private Theme theme;
}
