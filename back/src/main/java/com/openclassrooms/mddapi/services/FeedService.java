package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.repository.FeedRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
}
