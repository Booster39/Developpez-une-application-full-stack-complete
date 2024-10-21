package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.TopicDto;

import java.util.List;

public interface TopicService {
    TopicDto getSubjectById(Long id);
    TopicDto createSubject(TopicDto topicDto);
    TopicDto updateSubject(Long id, TopicDto topicDto);
    void deleteSubject(Long id);
    List<TopicDto> getAllSubjects();
    void likeTopic(long topicId);
    void dislikeTopic(long topicId);
}
