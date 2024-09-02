package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper implements EntityMapper<ArticleDto, Article> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThemeRepository themeRepository;
    @Override
    public Article toEntity(ArticleDto dto) {
        if (dto == null) {
            return null;
        }
        User author = userRepository.findById(dto.getAuthor_id()).orElse(null);
        Theme theme = themeRepository.findById(dto.getTheme_id()).orElse(null);

        return Article.builder()
                .id(dto.getId())
                .author(author)
                .theme(theme)
                .title(dto.getTitle())
                .content(dto.getContent())
                .created_at(dto.getCreated_at())
                .updated_at(dto.getUpdated_at())
                .build();
    }

    @Override
    public ArticleDto toDto(Article entity) {
        if (entity == null) {
            return null;
        }

        return ArticleDto.builder()
                .id(entity.getId())
                .author_id(entity.getAuthor().getId())
                .theme_id(entity.getTheme().getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }

    @Override
    public List<Article> toEntity(List<ArticleDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> toDto(List<Article> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
