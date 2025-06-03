package com.market.exception;

public class CartException extends Exception {
    public CartException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CartException: " + getMessage();
    }
}

