package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public ArticleDto getPostById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return articleMapper.toDto(article);
    }

    @Override
    public ArticleDto createPost(String title, Long topicId, String content, User author) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        Article article = Article.builder()
                .title(title)
                .topic(topic)
                .author(author)
                .content(content)
                .created_at(LocalDateTime.now())
                .build();
        articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    @Override
    public List<ArticleDto> getAllPosts() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }
}
