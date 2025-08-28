package com.openclassrooms.mddapi.payload.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleResponse {
    private Long id;
    private String title;
    private String description;
    private SimpleUser authorUsername;
    private SimpleTopic topic;        
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class SimpleUser {
        private Long id;
        private String name;
        private String email;
    }

    @Data
    public static class SimpleTopic {
        private Long id;
        private String name;
    }
}
