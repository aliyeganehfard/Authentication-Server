//package com.oauth.server.filter;
//
//import com.oauth.server.common.dto.AuthenticationResponse;
//import com.oauth.server.config.JwtService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
////@RequiredArgsConstructor
//public class UserPassAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
////    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtService jwtService;
//
//    public UserPassAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        var username = request.getParameter("username");
//        var password = request.getParameter("password");
//        return authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        User user = (User)authentication.getPrincipal();
//        Map<String, List<String>> userClaims = new HashMap<>();
////        userClaims.put("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
////        AuthenticationResponse token = jwtService.getToken(userClaims, user);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter writer = response.getWriter();
////        writer.write(token.toString());
//        writer.flush();
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
//    }
//}
