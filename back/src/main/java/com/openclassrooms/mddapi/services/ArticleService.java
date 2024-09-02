package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ThemeRepository themeRepository;

    public ArticleDto getPostById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return articleMapper.toDto(article);
    }

    public ArticleDto createPost(String title, Long theme_id, String content, User author) {
        Theme theme = themeRepository.findById(theme_id).orElse(null);
        Article article = Article.builder()
                .title(title)
                .theme(theme)
                .author(author)
                .content(content)
                .created_at(LocalDateTime.now())
                .build();
        articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    public List<ArticleDto> getAllPosts() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }
}
