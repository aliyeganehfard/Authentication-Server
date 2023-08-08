import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        try {
            // Generate RSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024); // Specify key size (e.g., 2048 bits)
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Get the private and public keys
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            Map<String, List<String>> payload = new HashMap<>();
            payload.put("p1", Arrays.asList("p1","p2"));

            payload.put("p2",Arrays.asList("p1","p2"));

            JWTCreator.Builder builder = JWT.create()
                    .withSubject("user")
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 100));

            payload.forEach(builder::withClaim);

            String token = builder.sign(Algorithm.RSA512(publicKey,privateKey));


            System.out.println(token);

            System.out.println("__________________________________");


            System.out.println(JWT.require(Algorithm.RSA512(publicKey, null)).build().verify(token).getToken());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
