package com.mahmoud.project.exception;

import java.time.LocalDateTime;

public record ApiError (int status, String message, LocalDateTime time) {
}
