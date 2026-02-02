package com.mahmoud.project.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    private boolean modified = false;

    public PaymentException(String message) {
        super(message);
        modified = true;
    }

    public boolean messageModified() {
        return modified;
    }
}
