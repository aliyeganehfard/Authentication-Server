package com.oauth.server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oauth.server.common.dto.AuthenticationResponse;
import com.oauth.server.common.exception.AuthException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.net.PortUnreachableException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

//    public static final String SECRET_KEY = "5/3Wa3ItrK8rBJpZr2YKxvMgAajJXkIWNgh4prD5sQo=";
    public static final String SECRET_KEY = "9A45Dn7WF9Twu1lp6V4pp1q0wSvsei4dmF7+RKm0pNULt0j7qpJP3wb7x93FrARMkK5iZlNa7Tgbw97vKZmRgOkXN0ZfsfJDIHu4Go1xsnrZj4nGSfIq9Hs0fVdnv/UrhuhZkLe8W1jCuWXOpFpW+mcQjfMRVenwDuYjWtB3SSo1d8ZLu4SSHqFbR49kENOmWkUOdXloOibf65mSiDAfQCgQaDd2disk8IjmmVKeGFsNI9uDnOVQ2Nsz8tlK3FBjBoS+X1e1/Kj9In4u9eFhtMRp9OkDhzdxPlxOsYY92WNte1CFaASzZooHLb/FiBaUBVNvQiqwm3xdCet/OByLTtruHmQi8pWNBE4E8q2C04y7a607JFAU4JlZKJRzka86yY29pJ4fYmXxvpkbl11o38qebmBt6ZFrpXfZz7PilWd/YEhCN2WBE8KcqfdC0ORXGAdg/Qspdv/GDcBepJMZ9972Zh7QLN4qI+gCCbg2PcqRW68QizAs3goovYvQGqX3Sf8ZUjdVSQmt0Be4duzZ4dILn5RRDfm5nYKFNCIoaz5ysxpURP63qHaH59seLwH2IgqF5qWnQ4KhqUGX07HwoUasqE2RoiNXjwjS0M83hsL9OFlLJ66cYucoXAsnRYKvAfq9fG9f/PF5nctrhVEO0NQgdcFsf/fbzvvTbRER1zI=";
    public static final Long EXPIRATION_TOKEN_TIME = 30L * 60L * 1000L;
    public static final Long EXPIRATION_REFRESH_TOKEN_TIME = 30L * 60L * 1000L;
    public static final String CLAIM_ROLES = "roles";

//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

    public AuthenticationResponse getToken(String claimName,List<String> claimValue, UserDetails userDetails) {
        AuthenticationResponse jwt = new AuthenticationResponse();
        String accessToken = generateAccessToken(claimName,claimValue, userDetails);
        String refreshToken = generateRefreshToken(userDetails);
        jwt.setAccessToken(accessToken);
        jwt.setRefreshToken(refreshToken);
        return jwt;
    }

//    public Boolean isValidToken(String token){
//        try {
//            DecodedJWT decodedJWT = getDecodedJWT(token);
//            var username = decodedJWT.getSubject();
//            var roles = decodedJWT.getClaim("roles").asArray(String.class);
//            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            Arrays.stream(roles).forEach(role ->
//                    authorities.add(new SimpleGrantedAuthority(role))
//            );
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }

    public DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(getSigningAlgorithm()).build();
            return verifier.verify(token);
        }catch (TokenExpiredException tokenExpiredException ){
            throw new AuthException("token was expired!");
        }
        catch (Exception e){
            throw new AuthException("invalid token!");
        }

    }

    private String generateAccessToken(String claimName,List<String> claimValue, UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_TIME))
                .withClaim(claimName,claimValue)
                .sign(getSigningAlgorithm());
    }

    private String generateRefreshToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_TOKEN_TIME))
                .sign(getSigningAlgorithm());
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC512(SECRET_KEY.getBytes());
    }


//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(Map<String, Object> extraClaim, UserDetails userDetails) {
//        return Jwts.builder()
//                .setClaims(extraClaim)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        Date expiration = extractExpiration(token);
//        return expiration.before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSigningKey() {
//        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}