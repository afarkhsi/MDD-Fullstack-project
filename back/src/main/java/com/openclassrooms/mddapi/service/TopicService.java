package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Optional<Topic> getById(Long id) {
        return topicRepository.findById(id);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
