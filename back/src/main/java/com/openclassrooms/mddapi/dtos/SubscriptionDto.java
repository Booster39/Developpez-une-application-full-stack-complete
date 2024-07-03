package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @MongoId
    @Id
    private String id;
    private String userId;
    private String subjectId;

}

