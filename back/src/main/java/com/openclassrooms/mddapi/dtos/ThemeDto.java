package com.openclassrooms.mddapi.dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {
    @MongoId
    @Id
    private String id;
    private String name;
    private Set<ArticleDto> posts;
    private Set<SubscriptionDto> subscriptions;

}

