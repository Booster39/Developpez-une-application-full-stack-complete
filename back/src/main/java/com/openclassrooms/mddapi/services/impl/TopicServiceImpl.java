package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TopicDto getSubjectById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        return topicMapper.toDto(topic);
    }

    @Override
    public TopicDto createSubject(TopicDto topicDto) {
        Topic topic = topicMapper.toEntity(topicDto);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Override
    public TopicDto updateSubject(Long id, TopicDto topicDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        topicMapper.toEntity(topicDto);  // Cette ligne semble inutile car elle ne change pas le topic original
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Override
    public void deleteSubject(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public List<TopicDto> getAllSubjects() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void likeTopic(long topicId) {
        User loggedUser = userService.me();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Le topic n'existe pas."));

        // Si l'utilisateur suit déjà le topic, on ne fait rien
        if (loggedUser.getFollowedTopics().stream().anyMatch(t -> t.getId() == topic.getId())) {
            return;
        }

        // Sinon, on ajoute le topic aux suivis
        loggedUser.getFollowedTopics().add(topic);
        userRepository.save(loggedUser);
    }

    @Override
    public void dislikeTopic(long topicId) {
        User loggedUser = userService.me();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Le topic n'existe pas."));

        // Retire le topic des suivis de l'utilisateur
        loggedUser.getFollowedTopics().removeIf(t -> t.getId() == topic.getId());
        userRepository.save(loggedUser);
    }
}
