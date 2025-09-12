package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.exception.ApiExceptions;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.request.CommentRequest;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final TopicService topicService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, UserService userService, TopicService topicService, CommentService commentService) {
        this.articleService = articleService;
        this.userService = userService;
        this.topicService = topicService;
        this.commentService = commentService;
    }

    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleRequest request, Principal principal) {
        User author = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicService.getByIdEntity(request.getTopicId())
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Thème non trouvé"));

        articleService.createArticle(request, author, topic);

        return ResponseEntity.ok(new MessageResponse("Article créé avec succès"));
    }
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id) {
        ArticleResponse response = articleService.getById(id);
        if (response == null) {
            throw new ApiExceptions.ResourceNotFoundException("Article non trouvé");
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentRequest request, Principal principal) {
        commentService.createComment(request, principal.getName());
        return ResponseEntity.ok(new MessageResponse("Commentaire publié"));
    }

    @GetMapping("/{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long id) {
        List<CommentResponse> comments = commentService.getCommentsByArticleId(id);
        if (comments.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Aucun commentaire trouvé");
        }
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/subscribed")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ArticleResponse>> getArticlesFromSubscribedTopics(Principal principal) {
        List<ArticleResponse> articles = articleService.getArticlesFromSubscribedTopics(principal.getName());
        if (articles.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Aucun article trouvé pour vos abonnements");
        }
        return ResponseEntity.ok(articles);
    }
}
