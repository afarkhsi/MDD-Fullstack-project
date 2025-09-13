package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.exception.ApiExceptions;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    public UserController(UserService userService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(Principal principal, @RequestBody Map<String, String> updates) {
        User user = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String username = updates.get("username");
        String email = updates.get("email");
        String password = updates.get("password");

        User updatedUser = userService.updateUser(user, username, email, password);
        return ResponseEntity.ok(mapToUserResponse(updatedUser));
    }

    @GetMapping("/me/subscriptions")
    public ResponseEntity<Set<TopicResponse>> getMySubscriptions(Principal principal) {
        Set<TopicResponse> topics = subscriptionService.getSubscriptions(principal.getName()).stream().map(topic -> {
            TopicResponse tr = new TopicResponse();
            tr.setId(topic.getId());
            tr.setName(topic.getName());
            tr.setDescription(topic.getDescription());
            tr.setIsSubscribed(true);
            return tr;
        }).collect(Collectors.toSet());
        
        if (topics.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Aucun abonnement trouvé");
        }

        return ResponseEntity.ok(topics);
    }

    // Mapper interne
    private UserResponse mapToUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        Set<TopicResponse> topics = user.getSubscribedTopics().stream().map(topic -> {
            TopicResponse tr = new TopicResponse();
            tr.setId(topic.getId());
            tr.setName(topic.getName());
            tr.setDescription(topic.getDescription());
            tr.setIsSubscribed(true);
            return tr;
        }).collect(Collectors.toSet());

        dto.setSubscribedTopics(topics);
        return dto;
    }
}

