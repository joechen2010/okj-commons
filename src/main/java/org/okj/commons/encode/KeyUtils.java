package org.okj.commons.encode;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class KeyUtils {

    public static String getSign(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] in = content.getBytes();
            byte[] digested = md.digest(in);

            return new String(encode(digested));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String encode(byte[] data) {
        return (new BASE64Encoder()).encode(data);
    }

    public static String encode(String input) {
        if (input == null) {
            return null;
        }
        return (new BASE64Encoder()).encode(input.getBytes());
    }

    public static byte[] decode(String input) {
        if (input == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(input);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    public static String encrypt(String content, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] bytes = content.getBytes();
        byte[] encodedByteArray = new byte[] {};
        for (int i = 0; i < bytes.length; i += 102) {
            byte[] subarray = ArrayUtils.subarray(bytes, i, i + 102);
            byte[] doFinal = cipher.doFinal(subarray);
            encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
        }
        return encode(encodedByteArray);
    }

    public static String decrypt(String base64EncodedContent, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] deBytes = decode(base64EncodedContent);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deBytes.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(deBytes, i, i + 128));
            sb.append(new String(doFinal));
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyString = getKeyString(publicKey);
        System.out.println("public:\n" + publicKeyString);

        String privateKeyString = getKeyString(privateKey);
        System.out.println("private:\n" + privateKeyString);
    }
}
