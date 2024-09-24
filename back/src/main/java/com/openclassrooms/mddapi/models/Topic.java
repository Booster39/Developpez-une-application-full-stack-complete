package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "TOPICS")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id"})
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Size(max = 255)
    private String name;
    @NonNull
    @Size(max = 2000)
    @Column(name = "description", insertable = true)
    private String description;


    //like and dislike
}
