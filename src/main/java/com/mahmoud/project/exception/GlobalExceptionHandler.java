package com.mahmoud.project.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableException() {
        return ResponseEntity.badRequest().body(
                ApiError.badRequest("Invalid request body")
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
        return ResponseEntity.badRequest().body(
                ApiError.badRequest("Invalid parameter value: " + exception.getName())
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.notFound("Product not found.")
        );
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError> handleCartNotFound() {
        return ResponseEntity.badRequest().body(
                ApiError.badRequest("Cart not found.")
        );
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<ApiError> handleCartIsEmpty() {
        return ResponseEntity.badRequest().body(
                ApiError.badRequest("Cart is empty.")
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.notFound("Category not found.")
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.notFound("User not found.")
        );
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiError> handleIncorrectPassword() {
        return ResponseEntity.badRequest().body(
                ApiError.badRequest("Incorrect current password.")
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.notFound("Order not found.")
        );
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ApiError> handleUserUnauthorized() {
        return ResponseEntity.status(401).body(
                ApiError.unauthorized("Unauthorized user.")
        );
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ApiError> handlePaymentException(PaymentException ex) {
        String message = "Error creating a checkout session.";

        message = ex.messageModified()? ex.getMessage() : message;

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiError.internalServerError(message)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiError.conflict("This email has already been registered.")
        );
    }
}
