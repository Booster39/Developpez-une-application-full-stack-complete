package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
