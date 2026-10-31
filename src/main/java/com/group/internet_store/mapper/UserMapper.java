package com.group.internet_store.mapper;

import com.group.internet_store.dto.UserRegistrationRequest;
import com.group.internet_store.dto.UserResponse;
import com.group.internet_store.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRegistrationRequestToUser(UserRegistrationRequest userRegistrationRequest);

    UserResponse userToUserResponse(User user,String token);
}
