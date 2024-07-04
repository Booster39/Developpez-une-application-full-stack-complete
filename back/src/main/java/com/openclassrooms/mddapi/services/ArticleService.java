package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleCreateDto;
import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ArticleDto getPostById(Long id) {
        Article article = articleRepository.findById((id)).orElseThrow(() -> new RuntimeException("Article not found"));
        return modelMapper.map(article, ArticleDto.class);
    }

    public ArticleDto createPost(String title, Theme theme, String content, User author) {
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
