package com.openclassrooms.mddapi.services;


import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    public TopicDto getSubjectById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        return topicMapper.toDto(topic);
    }

    public TopicDto createSubject(TopicDto topicDto) {
        Topic topic = topicMapper.toEntity(topicDto);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    public TopicDto updateSubject(Long id, TopicDto topicDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        topicMapper.toEntity(topicDto);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    public void deleteSubject(Long id) {
        topicRepository.deleteById(id);
    }

    public List<TopicDto> getAllSubjects() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }
}
