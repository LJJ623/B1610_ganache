package org.example;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class test3 {

    public static void main(String[] args) {
        try {
            // 调用 KeyValueStoreClient 的 setValueForKey 方法
            String txHash = test1.setValueForKey("4", "Test Value");
            System.out.println("Transaction receipt for (4, 'Test Value'): " + txHash);

            // 验证存储的值
            String value = test1.getValueForKey("4");

            System.out.println("Stored value for key '4': " + value);
            String username = "123";
            String originalMessage = "LJJ";

            // 加密
            String encryptedMessage = encrypt(username, originalMessage);
            System.out.println("加密后的消息: " + encryptedMessage);

            // 解密
            String decryptedMessage = decrypt(username, encryptedMessage);
            System.out.println("解密后的消息: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String username, String message) throws Exception {
        KeyPair keyPair = getKeyPair(username);
        PublicKey publicKey = keyPair.getPublic();

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = encryptCipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    public static String decrypt(String username, String encryptedMessage) throws Exception {
        KeyPair keyPair = getKeyPair(username);
        PrivateKey privateKey = keyPair.getPrivate();

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedMessage);
    }

    private static KeyPair getKeyPair(String username) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader("keys.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    String[] parts = line.split(", ");
                    String publicKeyString = parts[0].split("=")[1];
                    String privateKeyString = parts[1].split("=")[1];

                    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
                    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);

                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

                    return new KeyPair(publicKey, privateKey);
                }
            }
        }
        throw new FileNotFoundException("密钥文件中找不到用户名 " + username);
    }
}
