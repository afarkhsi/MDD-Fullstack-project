package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserComment;
import com.openclassrooms.mddapi.payload.request.CommentRequest;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.UserCommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class CommentService {

    private final UserCommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentService(UserCommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public void createComment(CommentRequest request, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Article article = articleRepository.findById(request.getArticleId())
            .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        UserComment comment = new UserComment();
        comment.setComment(request.getComment());
        comment.setUsername(user);
        comment.setArticle(article);

        commentRepository.save(comment);
    }

    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(c -> {
                    CommentResponse dto = new CommentResponse();
                    dto.setId(c.getId());
                    dto.setComment(c.getComment());
                    dto.setUsername(c.getUsername().getUsername());
                    return dto;
                })
                .toList();
    }
}
