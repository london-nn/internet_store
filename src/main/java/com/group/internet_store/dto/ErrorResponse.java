package com.group.internet_store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse<T> {
    private String errorName;
    private String timestamp;
    private String methodAndPath;
    private int status;
    private T details;
}