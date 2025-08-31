package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Article createArticle(ArticleRequest request, User author, Topic topic) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setAuthor(author);
        article.setTopic(topic);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public List<ArticleResponse> getAll() {
        return articleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'id : " + id));
        return toResponse(article);
    }

    private ArticleResponse toResponse(Article article) {
        ArticleResponse dto = new ArticleResponse();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setCreatedAt(article.getCreatedAt());
        dto.setUpdatedAt(article.getUpdatedAt());

        if (article.getAuthor() != null) {
            ArticleResponse.SimpleUser user = new ArticleResponse.SimpleUser();
            user.setId(article.getAuthor().getId());
            user.setName(article.getAuthor().getUsername());
            user.setEmail(article.getAuthor().getEmail());
            dto.setAuthorUsername(user);
        }

        if (article.getTopic() != null) {
            ArticleResponse.SimpleTopic topic = new ArticleResponse.SimpleTopic();
            topic.setId(article.getTopic().getId());
            topic.setName(article.getTopic().getName());
            dto.setTopic(topic);
        }

        return dto;
    }
    
    public List<ArticleResponse> getArticlesFromSubscribedTopics(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Set<Topic> subscribedTopics = user.getSubscribedTopics();

        return articleRepository.findByTopicIn(subscribedTopics)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
