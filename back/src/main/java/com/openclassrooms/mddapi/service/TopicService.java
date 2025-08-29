package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Optional<Topic> getById(Long id) {
        return topicRepository.findById(id);
    }

    public List<TopicResponse> getAllTopics(String username) {
    	User user = userRepository.findByUsername(username)
    		        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return topicRepository.findAll().stream()
            .map(topic -> {
            	boolean isSubscribed = subscriptionRepository.findByUserAndTopic(user, topic).isPresent();
                TopicResponse dto = new TopicResponse();
                dto.setId(topic.getId());
                dto.setName(topic.getName());
                dto.setDescription(topic.getDescription());
                dto.setIsSubscribed(isSubscribed);
                return dto;
            })
            .collect(Collectors.toList());
    }
}
