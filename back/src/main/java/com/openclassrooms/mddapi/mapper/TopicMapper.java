package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.payload.response.TopicResponse;

public class TopicMapper {

    public static TopicResponse toDto(Topic topic, boolean isSubscribed) {
        TopicResponse dto = new TopicResponse();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setDescription(topic.getDescription());
        dto.setIsSubscribed(isSubscribed);
        return dto;
    }
}