package com.openclassrooms.mddapi.service;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    
    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }


    public User getUserFromIdentifier(String emailOrUsername) {
        Optional<User> byEmail = getUserByEmail(emailOrUsername);
        Optional<User> byUsername = getUserByUsername(emailOrUsername);

        if (byEmail.isEmpty() && byUsername.isEmpty()) {
            throw new UsernameNotFoundException(
                "User Not Found with username or email: " + emailOrUsername);
        }

        return byEmail.orElseGet(byUsername::get);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean isEmailExist(String email) {
        Optional<User> existingUser = getUserByEmail(email);

        return existingUser.isPresent();
    }
    
    public boolean isPasswordValid(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }
    
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public User signUpUser(SignupRequest signUpRequest) {
        User user = new User();

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encodedPassword);
 
        return saveUser(user);
    }
    
    public User updateUser(User existingUser, String username, String email, String password) {
        if (username != null && !username.isBlank()) {
            existingUser.setUsername(username);
        }
        if (email != null && !email.isBlank()) {
            existingUser.setEmail(email);
        }
        if (password != null && !password.isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(existingUser);
    }
}
