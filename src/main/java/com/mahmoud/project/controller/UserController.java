package com.mahmoud.project.controller;

import com.mahmoud.project.dto.*;
import com.mahmoud.project.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(name = "sort", required = false, defaultValue = "") String sort
    ) {
        return userService.getAllUsers(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        UserDto userDto = userService.getUser(id);

        return ResponseEntity.status(200).body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody RegisterUserRequest registerUserRequest,
            UriComponentsBuilder uriBuilder
    ) {
        UserDto userDto = userService.registerUserWithProfile(registerUserRequest);
        var uri = uriBuilder.path("/users/{id}")
                .buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateUserRequest userRequest
    ) {
        UserDto userDto = userService.updateUser(id, userRequest);

        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest passwordRequest
    ) {
        userService.updateUserPassword(id, passwordRequest);

        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(
        @PathVariable(name = "id") Long id,
        @RequestBody UpdateUserRequest userRequest
    ) {
        UserDto userDto = userService.patchUser(id, userRequest);

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
