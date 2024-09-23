package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper implements EntityMapper<CommentDto, Comment> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public Comment toEntity(CommentDto dto) {
        if (dto == null) {
            return null;
        }

        User author = userRepository.findById(dto.getAuthor_id()).orElse(null);
        Article article = articleRepository.findById(dto.getArticle_id()).orElse(null);
        return Comment.builder()
                .id(dto.getId())
                .author(author)
                .article(article)
                .content(dto.getContent())
                .created_at(dto.getCreated_at())
                .updated_at(dto.getUpdated_at())
                .build();
    }

    @Override
    public CommentDto toDto(Comment entity) {
        if (entity == null) {
            return null;
        }

        return CommentDto.builder()
                .id(entity.getId())
                .author_id(entity.getAuthor().getId())
                .article_id(entity.getArticle().getId())
                .content(entity.getContent())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }

    @Override
    public List<Comment> toEntity(List<CommentDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> toDto(List<Comment> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
