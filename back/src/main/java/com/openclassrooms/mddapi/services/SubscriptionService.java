package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
}
