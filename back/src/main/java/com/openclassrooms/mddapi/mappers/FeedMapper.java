package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.FeedDto;
import com.openclassrooms.mddapi.dtos.FeedDto;
import com.openclassrooms.mddapi.models.Feed;
import com.openclassrooms.mddapi.models.Feed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedMapper  implements EntityMapper<FeedDto, Feed> {
    @Override
    public Feed toEntity(FeedDto dto) {
        if ( dto == null ) {
            return null;
        }

        Feed.FeedBuilder feed = Feed.builder();

        feed.id( dto.getId() );

        return feed.build();
    }

    @Override
    public FeedDto toDto(Feed entity) {
        if ( entity == null ) {
            return null;
        }

        FeedDto feedDto = new FeedDto();

        feedDto.setId( entity.getId() );

        return feedDto;
    }


    @Override
    public List<Feed> toEntity(List<FeedDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedDto> toDto(List<Feed> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
