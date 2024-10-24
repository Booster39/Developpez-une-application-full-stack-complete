package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements EntityMapper<UserDto, User> {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.name( dto.getName() );
        user.username( dto.getEmail() );
        user.created_at(dto.getCreated_at());
        user.updated_at(dto.getUpdated_at());
        user.followedTopics(dto.getFollowedTopics());

        return user.build();
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( entity.getId() );
        userDto.setName( entity.getName() );
        userDto.setEmail( entity.getUsername() );
        userDto.setCreated_at(entity.getCreated_at());
        userDto.setUpdated_at(entity.getUpdated_at());
        userDto.setFollowedTopics(entity.getFollowedTopics());


        return userDto;
    }


    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> toDto(List<User> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}