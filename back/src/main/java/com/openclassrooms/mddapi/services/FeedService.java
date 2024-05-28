package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Feed;
import com.openclassrooms.mddapi.repositories.FeedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
}
