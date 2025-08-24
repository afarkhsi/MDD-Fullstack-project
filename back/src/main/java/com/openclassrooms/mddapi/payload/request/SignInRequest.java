package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SignInRequest {
	
  @NotBlank(message = "Email or username cannot be blank or null")
  @Size(min = 3, max = 50)
  private String emailOrUsername;
  
  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
}
