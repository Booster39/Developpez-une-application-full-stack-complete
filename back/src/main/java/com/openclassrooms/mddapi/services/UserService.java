package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDto findUserDtoById(Long id) {
        User user = findById(id);
        if (user == null) {
            return null;
        }
        return userMapper.toDto(user);
    }
}