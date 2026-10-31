package com.group.internet_store.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force=true)
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
}
