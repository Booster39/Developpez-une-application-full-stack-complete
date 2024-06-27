package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Document(collection = "subjects")
@Data
public class Subject {
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @DBRef
    private Set<Post> posts;

    @DBRef
    private Set<Subscription> subscriptions;
}
