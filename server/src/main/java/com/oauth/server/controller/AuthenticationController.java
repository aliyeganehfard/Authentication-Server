package com.oauth.server.controller;

import com.oauth.server.common.exception.AuthException;
import com.oauth.server.database.model.User;
import com.oauth.server.common.dto.AuthenticationResponse;
import com.oauth.server.common.dto.SignInDto;
import com.oauth.server.common.dto.SignUpDto;
import com.oauth.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/")
public class AuthenticationController {

    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signUp")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpDto req) {
        User user = mapper.map(req, User.class);
        AuthenticationResponse response = userService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("signIn")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInDto req) {
        AuthenticationResponse response = userService.signIn(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("token/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException("missing refresh token");
        }
        var token = authHeader.substring("Bearer ".length());
        return new ResponseEntity<>(userService.refreshToken(token),HttpStatus.OK);
    }
}