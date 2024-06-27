package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, Long> {
}
