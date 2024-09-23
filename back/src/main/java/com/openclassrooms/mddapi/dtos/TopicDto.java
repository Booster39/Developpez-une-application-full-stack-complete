package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDto {
    private Long id;
    private String name;
    private String description;
}

