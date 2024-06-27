package com.openclassrooms.mddapi.dtos;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private Set<SubscriptionDto> subscriptions;
    private Set<PostDto> posts;
    private Set<CommentDto> comments;
}

