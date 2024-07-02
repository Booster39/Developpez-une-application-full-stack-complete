package com.openclassrooms.mddapi.dtos;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
   /* private Set<SubscriptionDto> subscriptions;
    private Set<ArticleDto> posts;
    private Set<CommentDto> comments;*/
}

