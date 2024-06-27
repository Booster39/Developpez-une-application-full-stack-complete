package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
    private Subject subject;
}
