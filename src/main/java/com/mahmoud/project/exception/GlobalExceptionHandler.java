package com.mahmoud.project.exception;

import com.mahmoud.project.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnreadableException() {
        return ResponseEntity.badRequest().body(
                new ErrorDto("Invalid request body")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgsErrors(
            MethodArgumentTypeMismatchException exception
    ) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid parameter value: " + exception.getName(),
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound() {
        return notFound("Product not found.");
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError> handleCartNotFound() {
        return badRequest("Cart not found.");
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<ApiError> handleCartIsEmpty() {
        return badRequest("Cart is empty.");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound() {
        return notFound("Category not found.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound() {
        return notFound("User not found.");
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiError> handleIncorrectPassword() {
        return badRequest("Incorrect current password.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFoundException() {
        return notFound("Order not found.");
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ApiError> handleUserUnauthorized() {
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized user.",
                LocalDateTime.now()
        );

        return ResponseEntity.status(401).body(apiError);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ApiError> handlePaymentException(PaymentException ex) {
        String message = "Error creating a checkout session.";

        if (ex.messageModified()) {
            message = ex.getMessage();
        }

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    private ResponseEntity<ApiError> notFound(String message) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private ResponseEntity<ApiError> badRequest(String message) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
