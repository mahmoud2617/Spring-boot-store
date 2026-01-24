package com.mahmoud.project.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUserRequest {
    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 255, message = "Name mustn't be greater than 255 character.")
    private String name;

    @NotNull(message = "Email is required.")
    @NotBlank(message = "Email must not be empty.")
    @Email
    private String email;

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password must not be empty.")
    @Size(min = 6, max = 255, message = "Password cannot be less than 6 or greater than 255")
    private String password;
}
