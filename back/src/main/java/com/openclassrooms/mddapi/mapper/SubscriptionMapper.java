package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.SubscriptionDto;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubscriptionMapper implements EntityMapper<SubscriptionDto, Subscription> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ThemeRepository themeRepository;
    @Override
    public Subscription toEntity(SubscriptionDto dto) {
        if (dto == null) {
            return null;
        }

        User author = userRepository.findById(dto.getAuthor_id()).orElse(null);
        Theme theme = themeRepository.findById(dto.getTheme_id()).orElse(null);

        return Subscription.builder()
                .id(dto.getId())
                .author(author)
                .theme(theme)
                .build();
    }

    @Override
    public SubscriptionDto toDto(Subscription entity) {
        if (entity == null) {
            return null;
        }

        return SubscriptionDto.builder()
                .id(entity.getId())
                .author_id(entity.getAuthor().getId())
                .theme_id(entity.getTheme().getId())
                .build();
    }

    @Override
    public List<Subscription> toEntity(List<SubscriptionDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionDto> toDto(List<Subscription> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
