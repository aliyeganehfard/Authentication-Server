package com.auth.client.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public static final String SECRET_KEY = "9A45Dn7WF9Twu1lp6V4pp1q0wSvsei4dmF7+RKm0pNULt0j7qpJP3wb7x93FrARMkK5iZlNa7Tgbw97vKZmRgOkXN0ZfsfJDIHu4Go1xsnrZj4nGSfIq9Hs0fVdnv/UrhuhZkLe8W1jCuWXOpFpW+mcQjfMRVenwDuYjWtB3SSo1d8ZLu4SSHqFbR49kENOmWkUOdXloOibf65mSiDAfQCgQaDd2disk8IjmmVKeGFsNI9uDnOVQ2Nsz8tlK3FBjBoS+X1e1/Kj9In4u9eFhtMRp9OkDhzdxPlxOsYY92WNte1CFaASzZooHLb/FiBaUBVNvQiqwm3xdCet/OByLTtruHmQi8pWNBE4E8q2C04y7a607JFAU4JlZKJRzka86yY29pJ4fYmXxvpkbl11o38qebmBt6ZFrpXfZz7PilWd/YEhCN2WBE8KcqfdC0ORXGAdg/Qspdv/GDcBepJMZ9972Zh7QLN4qI+gCCbg2PcqRW68QizAs3goovYvQGqX3Sf8ZUjdVSQmt0Be4duzZ4dILn5RRDfm5nYKFNCIoaz5ysxpURP63qHaH59seLwH2IgqF5qWnQ4KhqUGX07HwoUasqE2RoiNXjwjS0M83hsL9OFlLJ66cYucoXAsnRYKvAfq9fG9f/PF5nctrhVEO0NQgdcFsf/fbzvvTbRER1zI=";
    public static final String CLAIM_ROLES = "roles";
    public DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(getSigningAlgorithm()).build();
            return verifier.verify(token);
        }catch (TokenExpiredException tokenExpiredException ){
           tokenExpiredException.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC512(SECRET_KEY.getBytes());
    }

}