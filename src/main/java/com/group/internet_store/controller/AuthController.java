package com.group.internet_store.controller;

import com.group.internet_store.dto.UserLoginRequest;
import com.group.internet_store.dto.UserRegistrationRequest;
import com.group.internet_store.dto.UserResponse;
import com.group.internet_store.security.JwtUtils;
import com.group.internet_store.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        UserResponse userResponse = authService.createUser(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        String jwtToken = authService.loginUser(userLoginRequest);
        Map<String,String> map = new HashMap<>();
        map.put("token", jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
