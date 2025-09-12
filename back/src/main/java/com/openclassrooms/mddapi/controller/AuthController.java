package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.exception.ApiExceptions;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.SignInRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
	private final UserService userService;

	public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest) {
        // 1. Authentification
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmailOrUsername(),
                loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Génération du JWT
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // 3. Réponse
        JwtResponse jwtResponse = new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            userDetails.getAuthorities()
        );
        return ResponseEntity.ok(jwtResponse);
    }
	  
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

	    if (userService.isUsernameExist(signUpRequest.getUsername())) {
	       throw new ApiExceptions.BadRequestException("Error: Username is already taken!");
	    }

	    if (userService.isEmailExist(signUpRequest.getEmail())) {
	    	throw new ApiExceptions.BadRequestException("Email déjà utilisé");
	    }

	    // 1. Création et persistance
	    userService.signUpUser(signUpRequest);

	    // 2. Authentification automatique
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            signUpRequest.getUsername(),
	            signUpRequest.getPassword()
	        )
	    );
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    // 3. Génération du JWT
	    String jwt = jwtUtils.generateJwtToken(authentication);
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    // 4. Construction de la réponse
	    JwtResponse jwtResponse = new JwtResponse(
	        jwt,
	        userDetails.getId(),
	        userDetails.getUsername(),
	        userDetails.getEmail(),
	        userDetails.getAuthorities()
	    );

	    return ResponseEntity.ok(jwtResponse);
	}
}