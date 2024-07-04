package com.openclassrooms.mddapi.dtos;

import lombok.*;

import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String username;
}

