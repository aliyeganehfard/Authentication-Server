import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Main {

    public static final int KEY_SIZE = 2048;

    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generateRSAKeyPair();
        RSAPrivateKey priv = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();

        writePemFile(priv, "RSA PRIVATE KEY", "sign_key.pem");
        writePemFile(pub, "RSA PUBLIC KEY", "verify_key.pem");

    }

    private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(KEY_SIZE);
        return generator.generateKeyPair();
    }

    private static void writePemFile(Key key, String description, String filename)
            throws FileNotFoundException, IOException {
        PemFileWriter pemFileWriter = new PemFileWriter(key, description);
        pemFileWriter.write(filename);

    }

}