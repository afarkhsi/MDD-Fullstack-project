package com.openclassrooms.mddapi.payload.request;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class CommentRequest {
    @NotBlank
    private String comment;
    @NotNull
    private Long articleId;
}
