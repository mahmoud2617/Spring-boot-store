package com.mahmoud.project.controller;

import com.mahmoud.project.dto.UserDto;
import com.mahmoud.project.entity.User;
import com.mahmoud.project.mapper.UserMapper;
import com.mahmoud.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    UserRepository userRepository;
    UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(name = "sort", required = false, defaultValue = "") String sort
    ) {
        if (!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userRepository.findByIdWithDetails(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(userMapper.toDto(user));
    }
}
