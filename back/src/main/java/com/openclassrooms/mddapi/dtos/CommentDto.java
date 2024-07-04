package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private Long id;
    private Long author_id;
    private Long post_id;
    private String content;
    private LocalDateTime createdAt;
}

