package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByTopicIn(Set<Topic> topics);
}
