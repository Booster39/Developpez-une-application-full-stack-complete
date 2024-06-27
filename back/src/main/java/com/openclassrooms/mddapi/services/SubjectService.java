package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
}
