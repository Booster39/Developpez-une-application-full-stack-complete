package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private ThemeRepository themeRepository;


    public ArticleDto getPostById(Long id) {
        Article article = articleRepository.findById((id)).orElseThrow(() -> new RuntimeException("Article not found"));
        return modelMapper.map(article, ArticleDto.class);
    }

    public ArticleDto createPost(String title, Long theme_id, String content, User author) {
        Theme theme = themeRepository.findById(theme_id).orElse(null);
        Article article = Article.builder()
                .title(title)
                .theme(theme)
                .author(author)
                .content(content)
                .build();
        article.setCreated_at(LocalDateTime.now());
        articleRepository.save(article);
        return modelMapper.map(article, ArticleDto.class);
    }


    public List<ArticleDto> getAllPosts() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(post -> modelMapper.map(post, ArticleDto.class))
                .collect(Collectors.toList());
    }
}
