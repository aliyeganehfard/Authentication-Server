//import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.util.Scanner;
//
//public class PemFileReader {
//
//    //    public RSAPrivateKey readPrivateKey(File file) throws IOException {
////        try (FileReader keyReader = new FileReader(file)) {
////
////            PEMParser pemParser = new PEMParser(keyReader);
////            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
////            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
////
////            return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
////        }
////    }
//
//    public RSAPrivateKey readPrivateKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        try (PEMParser pemParser = new PEMParser(new FileReader(file))) {
//
//            PrivateKeyInfo privkeyInfo = (PrivateKeyInfo) pemParser.readObject();
//            PKCS8EncodedKeySpec keyspec = new PKCS8EncodedKeySpec(privkeyInfo.getEncoded());
//            return  (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keyspec);
//        }
//    }
//
//    private static String getFileContent(String filePath) throws IOException {
//        final StringBuilder sb = new StringBuilder();
//        File file = new File(filePath);
//        if (file.exists()) {
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNextLine()) {
//                sb.append(scanner.nextLine()).append("LINE_SEPARATOR");
//            }
//            scanner.close();
//            return sb.toString();
//        } else {
//            throw new IllegalArgumentException("File does not exist");
//        }
//    }
//
//    public RSAPrivateKey loadPrivateKeyFromFile(String privateKeyPath) throws Exception {
//        String privateKeyPem = getFileContent(privateKeyPath);
//        return loadPrivateKeyFromPem(privateKeyPem);
//    }
//
//    public static RSAPrivateKey loadPrivateKeyFromPem(String privateKeyPem) throws GeneralSecurityException {
//        String privateKey = privateKeyPem.replace(BEGIN_PRIVATE_KEY, "")
//                .replaceAll(LINE_SEPARATOR, "")
//                .replace(END_PRIVATE_KEY, "");
//        byte[] encodedPrivateKey = DatatypeConverter.parseBase64Binary(privateKey);
//        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKey));
//    }
//
//}
