package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.models.Theme;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThemeMapper implements EntityMapper<ThemeDto, Theme> {

    @Override
    public Theme toEntity(ThemeDto dto) {
        if (dto == null) {
            return null;
        }

        return Theme.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public ThemeDto toDto(Theme entity) {
        if (entity == null) {
            return null;
        }

        return ThemeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<Theme> toEntity(List<ThemeDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ThemeDto> toDto(List<Theme> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
