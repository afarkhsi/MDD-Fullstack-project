package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.*;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.model.Topic;
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
            .map(topic -> ResponseEntity.ok(Map.of("message", "Abonnement réussi", "topic", topic)))
            .orElse(ResponseEntity.badRequest().body(Map.of("message", "Erreur d’abonnement")));
    }

    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable Long id, Principal principal) {
        return subscriptionService.unsubscribe(id, principal.getName())
            .map(topic -> ResponseEntity.ok(Map.of("message", "Désabonnement réussi", "topic", topic)))
            .orElse(ResponseEntity.badRequest().body(Map.of("message", "Erreur de désabonnement")));
    }
    
    @GetMapping("/user-subscriptions")
    public ResponseEntity<Set<Topic>> getMySubscriptions(Principal principal) {
        Set<Topic> topics = subscriptionService.getSubscriptions(principal.getName());
        return ResponseEntity.ok(topics);
    }
}
