package com.openclassrooms.mddapi.dtos;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {
    private Long id;
    private String name;
    private Set<ArticleDto> posts;
    private Set<SubscriptionDto> subscriptions;

}

