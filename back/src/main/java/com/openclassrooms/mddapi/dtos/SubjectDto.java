package com.openclassrooms.mddapi.dtos;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 255)
    private String topic;
}
