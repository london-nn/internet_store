package com.group.internet_store.service;

import com.group.internet_store.dto.UserLoginRequest;
import com.group.internet_store.dto.UserRegistrationRequest;
import com.group.internet_store.dto.UserResponse;
import com.group.internet_store.entity.User;
import com.group.internet_store.exceptions.UsernameAlreadyExists;
import com.group.internet_store.mapper.ProductMapper;
import com.group.internet_store.mapper.UserMapper;
import com.group.internet_store.repository.UserRepository;
import com.group.internet_store.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserResponse createUser(UserRegistrationRequest user) {
        checkIfUsernameExists(user.getUsername());
        User newUser = userMapper.userRegistrationRequestToUser(user);
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(newUser);
        String jwtToken = jwtUtils.generateToken(savedUser.getUsername());
        UserResponse userResponse = userMapper.userToUserResponse(savedUser, jwtToken);
        return userResponse;
    }

    public String loginUser(UserLoginRequest user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    public void checkIfUsernameExists(String username) {
        if(userRepository.existsUserByUsername(username)) {
            throw new UsernameAlreadyExists("Username: " + username + " already exists");
        }
    }
}
