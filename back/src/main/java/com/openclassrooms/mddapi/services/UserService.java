package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.models.User;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, String name, String email);
    void deleteUser(Long id);
    List<UserDto> getAllUsers();
    User me();
}
