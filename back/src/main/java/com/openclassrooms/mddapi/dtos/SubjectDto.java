package com.openclassrooms.mddapi.dtos;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
    private Long id;
    private String name;
    private Set<PostDto> posts;
    private Set<SubscriptionDto> subscriptions;

}

