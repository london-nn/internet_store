package com.group.internet_store.exceptions;

public class MustBeGreaterThanZero extends RuntimeException {
    public MustBeGreaterThanZero(String message) {
        super(message);
    }
}
