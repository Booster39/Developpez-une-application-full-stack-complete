package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicMapper implements EntityMapper<TopicDto, Topic> {

    @Override
    public Topic toEntity(TopicDto dto) {
        if (dto == null) {
            return null;
        }

        return Topic.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public TopicDto toDto(Topic entity) {
        if (entity == null) {
            return null;
        }

        return TopicDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<Topic> toEntity(List<TopicDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TopicDto> toDto(List<Topic> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
