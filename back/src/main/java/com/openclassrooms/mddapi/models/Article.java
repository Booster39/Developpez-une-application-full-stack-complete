package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ARTICLES")
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id"})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 8)
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @NonNull
    @Size(max = 255)
    private String title;

    @NonNull
    @Size(max = 2000)
    @Column(name = "content", insertable = true)
    private String content;

}
