package com.oauth.server.common.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
}
