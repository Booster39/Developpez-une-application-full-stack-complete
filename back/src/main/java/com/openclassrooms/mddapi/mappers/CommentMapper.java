package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper  implements EntityMapper<CommentDto, Comment> {
    @Override
    public Comment toEntity(CommentDto dto) {
        if ( dto == null ) {
            return null;
        }

        Comment.CommentBuilder feed = Comment.builder();

        feed.id( dto.getId() );

        return feed.build();
    }

    @Override
    public CommentDto toDto(Comment entity) {
        if ( entity == null ) {
            return null;
        }

        CommentDto feedDto = new CommentDto();

        feedDto.setId( entity.getId() );

        return feedDto;
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
