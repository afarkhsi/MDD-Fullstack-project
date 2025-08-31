package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Optional<Topic> getByIdEntity(Long id) {
        return topicRepository.findById(id);
    }
    
    public TopicResponse getById(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic introuvable"));

        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setId(topic.getId());
        topicResponse.setName(topic.getName());
        topicResponse.setDescription(topic.getDescription());
        topicResponse.setIsSubscribed(user.getSubscribedTopics().contains(topic));

        return topicResponse;
    }

    public List<TopicResponse> getAllTopics(String username) {
    	User user = userRepository.findByUsername(username)
    		        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return topicRepository.findAll().stream()
            .map(topic -> {
            	boolean isSubscribed = user.getSubscribedTopics().contains(topic);
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
