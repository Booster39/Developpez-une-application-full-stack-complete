package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.SubscriptionCreateDto;
import com.openclassrooms.mddapi.dtos.SubscriptionDto;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SubscriptionDto getSubscriptionById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Subscription not found"));
        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    public SubscriptionDto createSubscription(SubscriptionCreateDto subscriptionCreateDto) {
        Subscription subscription = modelMapper.map(subscriptionCreateDto, Subscription.class);
        subscriptionRepository.save(subscription);
        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    public SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionUpdateDto) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Subscription not found"));
        modelMapper.map(subscriptionUpdateDto, subscription);
        subscriptionRepository.save(subscription);
        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionDto.class))
                .collect(Collectors.toList());
    }
}