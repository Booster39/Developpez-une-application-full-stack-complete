package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapping for Article to ArticleDto
       /* modelMapper.addMappings(new PropertyMap<Article, ArticleDto>() {
            @Override
            protected void configure() {
                map().setAuthor_id(source.getAuthor() != null ? source.getAuthor().getId() : null);
                map().setTopic_id(source.getTopic() != null ? source.getTopic().getId() : null);
            }
        });*/

        return modelMapper;
    }
}