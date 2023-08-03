//package com.oauth.server.filter;
//
//import com.oauth.server.common.exception.AuthException;
//import com.oauth.server.database.model.User;
//import com.oauth.server.database.repository.UserRepository;
//import com.oauth.server.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
//
//    private UserRepository userService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        var username = authentication.getName();
//        var password = authentication.getCredentials().toString();
//        User user = userService.findByUsername(username).get();
//
////        User user = userService.findByUsername(username);
//        if (passwordEncoder.matches(user.getPassword(),password)){
//            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
//            return new UsernamePasswordAuthenticationToken(username, password, authorities);
//        }else {
//            throw new AuthException("incorrect password!");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//    }
//}
