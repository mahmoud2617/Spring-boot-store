package com.mahmoud.project.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordRequest {
    private String oldPassword;

    @Size(min = 6, max = 255, message = "Password cannot be less than 6 or greater than 255")
    private String newPassword;
}
