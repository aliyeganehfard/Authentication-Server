package com.oauth.server.common.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String username;
    private String password;
}
