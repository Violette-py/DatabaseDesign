package com.pcs.be.service.ex;

public class LimitException extends RuntimeException {
    public LimitException(String message) {
        super(message);
    }
}
