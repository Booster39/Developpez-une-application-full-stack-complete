package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper  implements EntityMapper<PostDto, Post>{
    @Override
    public Post toEntity(PostDto dto) {
        if ( dto == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.id( dto.getId() );
        post.title(dto.getTitle());
        post.content(dto.getContent());
        post.author(dto.getAuthor());
        post.comment(dto.getComment());
        post.created_at(dto.getCreated_at());

        return post.build();
    }

    @Override
    public PostDto toDto(Post entity) {
        if ( entity == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setId( entity.getId() );
        postDto.setAuthor( entity.getAuthor() );
        postDto.setTitle( entity.getTitle() );
        postDto.setComment(entity.getComment());
        postDto.setContent(entity.getContent());
        postDto.setCreated_at(entity.getCreated_at());


        return postDto;
    }


    @Override
    public List<Post> toEntity(List<PostDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> toDto(List<Post> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
