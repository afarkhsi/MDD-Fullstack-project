package com.openclassrooms.mddapi.payload.response;

import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<TopicResponse> subscribedTopics;
}
