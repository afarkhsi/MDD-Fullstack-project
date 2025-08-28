package com.openclassrooms.mddapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
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

    public Optional<Topic> subscribe(Long topicId, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Topic> topicOpt = topicRepository.findById(topicId);

        if (userOpt.isPresent() && topicOpt.isPresent()) {
            User user = userOpt.get();
            Topic topic = topicOpt.get();

            user.getSubscribedTopics().add(topic);
            userRepository.save(user);
            return Optional.of(topic);
        }

        return Optional.empty();
    }

    public Optional<Topic> unsubscribe(Long topicId, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Topic> topicOpt = topicRepository.findById(topicId);

        if (userOpt.isPresent() && topicOpt.isPresent()) {
            User user = userOpt.get();
            Topic topic = topicOpt.get();

            user.getSubscribedTopics().remove(topic);
            userRepository.save(user);
            return Optional.of(topic);
        }

        return Optional.empty();
    }
}
