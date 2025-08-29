package com.openclassrooms.mddapi.payload.response;

import lombok.Data;

@Data
public class TopicResponse {
    private Long id;
    private String name;
    private String description;
    private Boolean isSubscribed;
}
