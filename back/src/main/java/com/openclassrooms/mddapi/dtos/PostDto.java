package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @NonNull
    @Size(max = 255)
    private String title;

    /*private Subject topic;*/

    @NonNull
    @Size(max = 255)
    private String author;

    @NonNull
    @Size(max = 255)
    private String comment;

    @NonNull
    @Size(max = 2000)
    private String content;
}
