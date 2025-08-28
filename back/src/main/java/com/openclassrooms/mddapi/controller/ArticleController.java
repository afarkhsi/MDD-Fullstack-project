package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final TopicService topicService;

    public ArticleController(ArticleService articleService, UserService userService, TopicService topicService) {
        this.articleService = articleService;
        this.userService = userService;
        this.topicService = topicService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleRequest request, Principal principal) {
        User author = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Topic topic = topicService.getById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        articleService.createArticle(request, author, topic);

        return ResponseEntity.ok(new MessageResponse("Article créé avec succès"));
    }
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }

}
