package com.oauth.commonutils.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oauth.commonutils.common.aop.CommonUtilsException;
import com.oauth.commonutils.common.utils.PublicKeyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.interfaces.RSAPublicKey;

@Service
public class JWTVerificationProvider {


    public static final String CLAIM_ROLES = "roles";
    private RSAPublicKey publicKey;


    @Autowired
    public void init() {
        try {
            publicKey = PublicKeyReader.getPublicKey("verify_key.pub");
        } catch (Exception e) {
           throw new CommonUtilsException("problem with read RSA file");
        }
    }

    public RSAPublicKey getPublicKey(){
        return this.publicKey;
    }

    public DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(getVerificationAlgorithm()).build();
            return verifier.verify(token);
        } catch (TokenExpiredException tokenExpiredException) {
            throw new CommonUtilsException("token was expired!");
        } catch (Exception e) {
            throw new CommonUtilsException("invalid token!");
        }

    }

    private Algorithm getVerificationAlgorithm(){
       return Algorithm.RSA256(publicKey, null);
    }
}