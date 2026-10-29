package com.group.internet_store.exceptions;

import com.group.internet_store.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GenericConstructor {

    public <T> ErrorResponse<T> construct(String errorName , T details, HttpStatus status, HttpServletRequest request) {
        String methodAndPath = request.getMethod() + " " + request.getRequestURI();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy - H:mm:ss");
        return new ErrorResponse<>(
                errorName,
                LocalDateTime.now().format(formatter),
                methodAndPath,
                status.value(),
                details
        );
    }

}