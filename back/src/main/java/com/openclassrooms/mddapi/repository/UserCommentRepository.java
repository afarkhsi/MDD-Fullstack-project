package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.UserComment;

public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    List<UserComment> findByArticleId(Long articleId);
}
