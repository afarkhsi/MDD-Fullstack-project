package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long id, Principal principal) {
        return subscriptionService.subscribe(id, principal.getName())
            .map(dto -> ResponseEntity.ok(Map.of("message", "Abonnement réussi", "topic", dto)))
            .orElse(ResponseEntity.badRequest().body(Map.of("message", "Erreur d’abonnement")));
    }

    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable Long id, Principal principal) {
        return subscriptionService.unsubscribe(id, principal.getName())
            .map(dto -> ResponseEntity.ok(Map.of("message", "Désabonnement réussi", "topic", dto)))
            .orElse(ResponseEntity.badRequest().body(Map.of("message", "Erreur de désabonnement")));
    }
    
    @GetMapping("/user-subscriptions")
    public ResponseEntity<Set<TopicResponse>> getMySubscriptions(Principal principal) {
        Set<TopicResponse> topics = subscriptionService.getSubscriptions(principal.getName())
            .stream()
            .map(topic -> {
                TopicResponse dto = new TopicResponse();
                dto.setId(topic.getId());
                dto.setName(topic.getName());
                dto.setDescription(topic.getDescription());
                dto.setIsSubscribed(true);
                return dto;
            })
            .collect(Collectors.toSet());

        return ResponseEntity.ok(topics);
    }
}
