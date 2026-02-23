package com.anthony.devsu.backend.backend_banca.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    
    public InsufficientBalanceException(String message) {
        super(message);
    }
}