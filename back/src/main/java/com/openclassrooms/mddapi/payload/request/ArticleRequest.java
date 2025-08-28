package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.*;
import lombok.Data;

@Data
public class ArticleRequest {
	  @NotBlank
	  private String title;

	  @NotBlank
	  private String description;

	  @NotNull
	  private Long topicId;
}