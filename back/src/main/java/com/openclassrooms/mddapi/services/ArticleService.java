package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleCreateDto;
import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
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


    public ArticleDto getPostById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        return modelMapper.map(article, ArticleDto.class);
    }

    public ArticleDto createPost(ArticleCreateDto articleCreateDto) {
        Article article = modelMapper.map(articleCreateDto, Article.class);
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
