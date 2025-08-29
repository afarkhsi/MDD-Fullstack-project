package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TopicResponse>> getAllTopics(Principal principal) {
    	String username = principal.getName();
        return ResponseEntity.ok(topicService.getAllTopics(username));
    }
}
