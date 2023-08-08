package com.oauth.server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oauth.server.common.dto.AuthenticationResponse;
import com.oauth.server.common.exception.AuthException;
import com.oauth.server.common.utils.PrivateKeyReader;
import com.oauth.server.common.utils.PublicKeyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

@Service
public class JwtService {

    //    public static final String SECRET_KEY = "5/3Wa3ItrK8rBJpZr2YKxvMgAajJXkIWNgh4prD5sQo=";
    public static final String SECRET_KEY = "9A45Dn7WF9Twu1lp6V4pp1q0wSvsei4dmF7+RKm0pNULt0j7qpJP3wb7x93FrARMkK5iZlNa7Tgbw97vKZmRgOkXN0ZfsfJDIHu4Go1xsnrZj4nGSfIq9Hs0fVdnv/UrhuhZkLe8W1jCuWXOpFpW+mcQjfMRVenwDuYjWtB3SSo1d8ZLu4SSHqFbR49kENOmWkUOdXloOibf65mSiDAfQCgQaDd2disk8IjmmVKeGFsNI9uDnOVQ2Nsz8tlK3FBjBoS+X1e1/Kj9In4u9eFhtMRp9OkDhzdxPlxOsYY92WNte1CFaASzZooHLb/FiBaUBVNvQiqwm3xdCet/OByLTtruHmQi8pWNBE4E8q2C04y7a607JFAU4JlZKJRzka86yY29pJ4fYmXxvpkbl11o38qebmBt6ZFrpXfZz7PilWd/YEhCN2WBE8KcqfdC0ORXGAdg/Qspdv/GDcBepJMZ9972Zh7QLN4qI+gCCbg2PcqRW68QizAs3goovYvQGqX3Sf8ZUjdVSQmt0Be4duzZ4dILn5RRDfm5nYKFNCIoaz5ysxpURP63qHaH59seLwH2IgqF5qWnQ4KhqUGX07HwoUasqE2RoiNXjwjS0M83hsL9OFlLJ66cYucoXAsnRYKvAfq9fG9f/PF5nctrhVEO0NQgdcFsf/fbzvvTbRER1zI=";
    public static final Long EXPIRATION_TOKEN_TIME = 30L * 60L * 1000L;
    public static final Long EXPIRATION_REFRESH_TOKEN_TIME = 30L * 60L * 1000L;
    public static final String CLAIM_ROLES = "roles";


    public RSAPrivateKey privateKey;
    public RSAPublicKey publicKey;


    @Autowired
    public void init() {
        try {
            privateKey = PrivateKeyReader.getPrivateKey("sign_key.pem");
            publicKey = PublicKeyReader.getPublicKey("verify_key.pem");
        } catch (Exception e) {
           throw new AuthException("problem with read RSA file");
        }
    }

    public AuthenticationResponse getToken(Map<String,List<String>> payload, UserDetails userDetails) {
        AuthenticationResponse jwt = new AuthenticationResponse();
        String accessToken = generateAccessToken(payload, userDetails);
        String refreshToken = generateRefreshToken(userDetails);
        jwt.setAccessToken(accessToken);
        jwt.setRefreshToken(refreshToken);
        return jwt;
    }

    public DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(getVerificationAlgorithm()).build();
            return verifier.verify(token);
        } catch (TokenExpiredException tokenExpiredException) {
            throw new AuthException("token was expired!");
        } catch (Exception e) {
            throw new AuthException("invalid token!");
        }

    }

    private String generateAccessToken(Map<String,List<String>> payload, UserDetails userDetails) {
        JWTCreator.Builder builder=  JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_TIME));
        payload.forEach(builder::withClaim);
        return builder.sign(getSigningAlgorithm());
    }

    private String generateRefreshToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_TOKEN_TIME))
                .sign(getSigningAlgorithm());
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.RSA256(publicKey,privateKey);
    }

    private Algorithm getVerificationAlgorithm(){
       return Algorithm.RSA256(publicKey, null);
    }
}