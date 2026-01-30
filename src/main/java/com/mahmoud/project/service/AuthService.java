package com.mahmoud.project.service;

import com.mahmoud.project.entity.User;
import com.mahmoud.project.exception.UserNotFoundException;
import com.mahmoud.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
