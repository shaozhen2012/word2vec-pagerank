package com.rongpingkeji.common.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * Created by Daniel on 2018/11/12.
 */
public class AES {


    /**
     * 加密
     *
     * @param src
     * @param key
     * @return
     */
    public static String encrypt(String src, String key) {
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String encrypt(String src, String key, byte[] data) {
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec _iv = new IvParameterSpec(data);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, _iv);
            byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String decrypt(String src, String key) {
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encryptdata = new Base64().decode(src);
            byte[] value = cipher.doFinal(encryptdata);
            return new String(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String decrypt(byte[] encryptdata, byte[] raw, byte[] inv) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(inv));
            byte[] value = cipher.doFinal(encryptdata);
            return new String(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
