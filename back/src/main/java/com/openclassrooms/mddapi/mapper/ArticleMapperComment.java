package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.dtos.ArticleDtoComment;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapperComment implements EntityMapper<ArticleDtoComment, Article> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicRepository topicRepository;
    @Override
    public Article toEntity(ArticleDtoComment dto) {
        if (dto == null) {
            return null;
        }
        User author = userRepository.findById(dto.getAuthor_id()).orElse(null);
        Topic topic = topicRepository.findById(dto.getTopic_id()).orElse(null);

        return Article.builder()
                .id(dto.getId())
                .author(author)
                .topic(topic)
                .title(dto.getTitle())
                .content(dto.getContent())
                .created_at(dto.getCreated_at())
                .updated_at(dto.getUpdated_at())
                .build();
    }

    @Override
    public ArticleDtoComment toDto(Article entity) {
        if (entity == null) {
            return null;
        }

        return ArticleDtoComment.builder()
                .id(entity.getId())
                .author_id(entity.getAuthor().getId())
                .topic_id(entity.getTopic().getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }

    @Override
    public List<Article> toEntity(List<ArticleDtoComment> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDtoComment> toDto(List<Article> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
