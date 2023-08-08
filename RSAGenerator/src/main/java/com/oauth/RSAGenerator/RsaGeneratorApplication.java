package com.oauth.RSAGenerator;

import com.oauth.RSAGenerator.service.PemFileWriter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
public class RsaGeneratorApplication implements CommandLineRunner {

	@Autowired
	private ResourceLoader resourceLoader;
	public static final int KEY_SIZE = 2048;

	public static void main(String[] args) {
		SpringApplication.run(RsaGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		KeyPair keyPair = generateRSAKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();


		Resource signKey = resourceLoader.getResource("classpath:sign_key_demo.pem");
		Resource verifyKey = resourceLoader.getResource("classpath:verify_key_demo.pem");
//		fileProcess(signKey.getFile(),verifyKey.getFile());

		writePemFile(privateKey, "RSA PRIVATE KEY", signKey.getFilename());
		writePemFile(publicKey, "RSA PUBLIC KEY", verifyKey.getFilename());
	}

	private static void fileProcess(File... files) throws IOException {
		for (File file : files) {
			if (!file.exists()) {
				file.createNewFile();
			}
		}
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
