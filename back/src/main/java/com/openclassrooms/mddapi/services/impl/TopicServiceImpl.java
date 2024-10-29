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

/**
 * Implementation of the TopicService interface for managing topics.
 */
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

    /**
     * Retrieves a topic by its ID.
     *
     * @param id the ID of the topic to retrieve
     * @return a TopicDto representing the retrieved topic
     * @throws RuntimeException if the topic is not found
     */
    @Override
    public TopicDto getSubjectById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        return topicMapper.toDto(topic);
    }

    /**
     * Creates a new topic based on the provided TopicDto.
     *
     * @param topicDto the TopicDto containing the information for the new topic
     * @return a TopicDto representing the created topic
     */
    @Override
    public TopicDto createSubject(TopicDto topicDto) {
        Topic topic = topicMapper.toEntity(topicDto);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Updates an existing topic based on the provided ID and TopicDto.
     *
     * @param id       the ID of the topic to update
     * @param topicDto the TopicDto containing the new information for the topic
     * @return a TopicDto representing the updated topic
     * @throws RuntimeException if the topic is not found
     */
    @Override
    public TopicDto updateSubject(Long id, TopicDto topicDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        topicMapper.toEntity(topicDto); // Cette ligne semble inutile car elle ne change pas le topic original
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Deletes a topic by its ID.
     *
     * @param id the ID of the topic to delete
     */
    @Override
    public void deleteSubject(Long id) {
        topicRepository.deleteById(id);
    }

    /**
     * Retrieves all topics.
     *
     * @return a list of TopicDto representing all topics
     */
    @Override
    public List<TopicDto> getAllSubjects() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Allows the logged-in user to like (follow) a topic.
     *
     * @param topicId the ID of the topic to like
     * @throws RuntimeException if the topic is not found
     */
    @Override
    public void likeTopic(long topicId) {
        User loggedUser = userService.me();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Le topic n'existe pas."));

        // If the user already follows the topic, do nothing
        if (loggedUser.getFollowedTopics().stream().anyMatch(t -> t.getId() == topic.getId())) {
            return;
        }

        // Otherwise, add the topic to the user's followed topics
        loggedUser.getFollowedTopics().add(topic);
        userRepository.save(loggedUser);
    }

    /**
     * Allows the logged-in user to dislike (unfollow) a topic.
     *
     * @param topicId the ID of the topic to dislike
     * @throws RuntimeException if the topic is not found
     */
    @Override
    public void dislikeTopic(long topicId) {
        User loggedUser = userService.me();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Le topic n'existe pas."));

        // Remove the topic from the user's followed topics
        loggedUser.getFollowedTopics().removeIf(t -> t.getId() == topic.getId());
        userRepository.save(loggedUser);
    }
}
