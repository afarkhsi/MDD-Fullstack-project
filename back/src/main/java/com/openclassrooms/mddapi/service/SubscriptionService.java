package com.openclassrooms.mddapi.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class SubscriptionService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public SubscriptionService(UserRepository userRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public Optional<TopicResponse> subscribe(Long topicId, String username) {
        return getUserAndTopic(username, topicId).map(pair -> {
            User user = pair.getKey();
            Topic topic = pair.getValue();

            if (!user.getSubscribedTopics().contains(topic)) {
                user.getSubscribedTopics().add(topic);
                userRepository.save(user);
            }

            return TopicMapper.toDto(topic, true);
        });
    }

    public Optional<TopicResponse> unsubscribe(Long topicId, String username) {
        return getUserAndTopic(username, topicId).map(pair -> {
            User user = pair.getKey();
            Topic topic = pair.getValue();

            if (user.getSubscribedTopics().contains(topic)) {
                user.getSubscribedTopics().remove(topic);
                userRepository.save(user);
            }

            return TopicMapper.toDto(topic, false);
        });
    }
    
    public Set<Topic> getSubscriptions(String username) {
        return userRepository.findByUsername(username)
                .map(User::getSubscribedTopics)
                .orElse(Collections.emptySet());
    }

    private Optional<AbstractMap.SimpleEntry<User, Topic>> getUserAndTopic(String username, Long topicId) {
        return userRepository.findByUsername(username)
                .flatMap(user -> topicRepository.findById(topicId)
                        .map(topic -> new AbstractMap.SimpleEntry<>(user, topic)));
    }
}
