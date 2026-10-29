package com.group.internet_store.exceptions;

public class ProductsListIsEmpty extends RuntimeException {
    public ProductsListIsEmpty(String message) {
        super(message);
    }
}
