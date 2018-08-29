package com.cxz.baselibs.utils;

import android.util.Base64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil {

    public static String encryptRSA(JSONObject json, String publicKey) {
        byte[] content = rsa(json, publicKey);
        String result2 = Base64.encodeToString(content, Base64.DEFAULT);
        result2 = result2.replaceAll("[+]", ".");
        return result2;
    }

    public static byte[] rsa(JSONObject json, String publicKey) {
        String text = json.toString();
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            RSAPublicKey publicKey1 = loadPublicKeyByStr(publicKey);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
            return cipher.doFinal(text.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String loadPublicKeyByFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/publicKey.keystore"));
            String readLine = null;

            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

}
