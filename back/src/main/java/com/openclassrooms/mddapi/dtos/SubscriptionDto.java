package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long id;
    private String user_id;
    private String subject_id;

}

