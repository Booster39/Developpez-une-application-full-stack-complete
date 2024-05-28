package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.SubscriptionDto;
import com.openclassrooms.mddapi.dtos.SubscriptionDto;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubscriptionMapper  implements EntityMapper<SubscriptionDto, Subscription>{
    @Override
    public Subscription toEntity(SubscriptionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Subscription.SubscriptionBuilder subscription = Subscription.builder();

        subscription.id( dto.getId() );
        subscription.isSubscribed(dto.isSubscribed());

        return subscription.build();
    }

    @Override
    public SubscriptionDto toDto(Subscription entity) {
        if ( entity == null ) {
            return null;
        }

        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId( entity.getId() );
        subscriptionDto.setSubscribed(entity.isSubscribed());

        return subscriptionDto;
    }


    @Override
    public List<Subscription> toEntity(List<SubscriptionDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionDto> toDto(List<Subscription> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
