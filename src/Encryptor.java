import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encryptor {

    public static byte[] encrypt(String transform, PublicKey publicKey, byte[] input) {
        try {
            Cipher cipher = Cipher.getInstance(transform);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(String transform, PrivateKey privateKey, byte[] input) {
        try {
            Cipher cipher = Cipher.getInstance(transform);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair generatePrivateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey generatePublicKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair().getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    public static String convertToString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    public static PublicKey convertToPublicKey(String base64PublicKey){
        if (base64PublicKey == null) return null;
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
            // Convert the byte array back to a PublicKey object
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey convertToPrivateKey(String base64PrivateKey){
        if (base64PrivateKey == null) return null;
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
            // Convert the byte array back to a PrivateKey object
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}


