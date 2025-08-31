package com.openclassrooms.mddapi.payload.response;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String comment;
    private String username;
}