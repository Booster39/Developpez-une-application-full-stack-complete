package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
