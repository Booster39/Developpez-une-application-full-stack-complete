package com.openclassrooms.mddapi.dtos;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @MongoId
    @Id
    private String id;
    private String email;
    private String username;
   /* private Set<SubscriptionDto> subscriptions;
    private Set<ArticleDto> posts;
    private Set<CommentDto> comments;*/
}

