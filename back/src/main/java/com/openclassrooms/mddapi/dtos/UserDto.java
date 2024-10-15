package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Topic;
import lombok.*;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private List<Topic> followedTopics;
}