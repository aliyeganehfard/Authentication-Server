package com.oauth.server.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oauth.server.common.exception.AuthException;
import com.oauth.server.config.JwtService;
import com.oauth.server.database.model.User;
import com.oauth.server.database.repository.UserRepository;
import com.oauth.server.common.dto.AuthenticationResponse;
import com.oauth.server.common.dto.SignInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.oauth.server.config.JwtService.CLAIM_ROLES;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService,AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse signUp(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse signIn(SignInDto signInDto) {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword())
           );
       }catch (BadCredentialsException badCredentialsException){
           throw new AuthException("incorrect password!");
       }

        User user = findByUsername(signInDto.getUsername());
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse refreshToken(String token) {
        DecodedJWT jwt = jwtService.getDecodedJWT(token);
        User user = findByUsername(jwt.getSubject());
        return getAuthenticationResponse(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new AuthException("username " + username + " not found!")
        );
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        List<String> collect = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return jwtService.getToken(CLAIM_ROLES, collect, user);
    }
}
