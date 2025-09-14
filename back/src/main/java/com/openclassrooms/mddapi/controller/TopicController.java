package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.exception.ApiExceptions;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.service.TopicService;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Retrieve all topics
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TopicResponse>> getAllTopics(Principal principal) {
    	String username = principal.getName();
    	List<TopicResponse> topics = topicService.getAllTopics(username);
		if (topics.isEmpty()) {
	        throw new ApiExceptions.ResourceNotFoundException("Aucun thème trouvé");
	    }
        return ResponseEntity.ok(topics);
    }
    
    /**
     * Retrieve a topic by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id, Principal principal) {
    	TopicResponse topic = topicService.getById(id, principal.getName());
    	if (topic == null) {
            throw new ApiExceptions.ResourceNotFoundException("Thème introuvable");
        }
        return ResponseEntity.ok(topic);
    }
}
