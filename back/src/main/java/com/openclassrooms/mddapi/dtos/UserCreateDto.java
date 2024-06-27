package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String email;
    private String password;
    private String username;

    // Getters and setters
}
