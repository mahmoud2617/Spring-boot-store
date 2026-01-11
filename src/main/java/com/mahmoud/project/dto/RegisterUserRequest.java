package com.mahmoud.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUserRequest {
    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 255, message = "Name mustn't be greater than 255 character.")
    private String name;

    @Email
    private String email;

    @Size(min = 6, max = 255, message = "Password cannot be less than 6 or greater than 255")
    private String password;
}
