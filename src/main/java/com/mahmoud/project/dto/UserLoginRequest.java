package com.mahmoud.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {
    @NotNull(message = "Email is required.")
    @NotBlank(message = "Email must not be empty.")
    @Email
    private String email;

    @Size(min = 6, max = 255, message = "Password cannot be less than 6 or greater than 255")
    private String password;
}
