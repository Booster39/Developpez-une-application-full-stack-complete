package com.openclassrooms.mddapi.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Document(collection = "users")
@Data
public class User {
    @Id
    private Long id;

    @NonNull
    @Size(max = 255)
    @Email
    private String email;

    @NonNull
    @Size(max = 255)
    private String name;

    @NonNull
    @Size(min = 8, max = 255)
    private String password;

    @DBRef
    private Set<Subscription> subscriptions;

    @DBRef
    private Set<Article> articles;

    @DBRef
    private Set<Comment> comments;
}
