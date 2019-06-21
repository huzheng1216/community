package com.flyersoft.seekbooks.Temp;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import static android.util.Base64.decode;

public class RSAUtil {

	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ENCODE_ALGORITHM = "SHA-256";
    public static final String PLAIN_TEXT = "test string";
    
    
    public static byte[] sign(PrivateKey privateKey, String plain_text) {  
        MessageDigest messageDigest;  
        byte[] signed = null;  
        try {  
            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);  
            messageDigest.update(plain_text.getBytes());  
            byte[] outputDigest_sign = messageDigest.digest();  
            System.out.println("SHA-256加密后-----》" +bytesToHexString(outputDigest_sign));  
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);  
            Sign.initSign(privateKey);  
            Sign.update(outputDigest_sign);  
            signed = Sign.sign();  
            System.out.println("SHA256withRSA签名后-----》" + bytesToHexString(signed));  
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {  
            e.printStackTrace();  
        }  
        return signed;  
    }
    
    
    public static boolean verifySign(PublicKey publicKey, String plain_text, byte[] signed) {  
    	  
        MessageDigest messageDigest;  
        boolean SignedSuccess=false;  
        try {  
            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);  
            messageDigest.update(plain_text.getBytes());  
            byte[] outputDigest_verify = messageDigest.digest();  
            //System.out.println("SHA-256加密后-----》" +bytesToHexString(outputDigest_verify));  
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);  
            verifySign.initVerify(publicKey);  
            verifySign.update(outputDigest_verify);  
            SignedSuccess = verifySign.verify(signed);  
            System.out.println("验证成功？---" + SignedSuccess);  
              
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {  
            e.printStackTrace();  
        }  
        return SignedSuccess;  
    } 
    
    
    /**
     * 
     * @param key
     * @param sign_type SHA-256
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key,String sign_type) throws Exception {  
        byte[] keyBytes;  
        keyBytes = decode(key, Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(sign_type);  
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
        return privateKey;  
    } 
    
    /**
     * 
     * @param key
     * @param sign_type  RSA  SHA-256
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key,String sign_type) throws Exception {  
        byte[] keyBytes;  
        keyBytes = decode(key, Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(sign_type);  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    }
    
    
  //***************************签名和验证*******************************  
    public static byte[] sign(byte[] data,String str_priK) throws Exception{  
      PrivateKey priK = getPrivateKey(str_priK,"SHA-256");  
      Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);       
      sig.initSign(priK);  
      sig.update(data);  
      return sig.sign();  
  }  
    
  public static boolean verify(byte[] data,byte[] sign,String str_pubK) throws Exception{  
      PublicKey pubK = getPublicKey(str_pubK,"RSA");  
      Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);  
      sig.initVerify(pubK);  
      sig.update(data);  
      return sig.verify(sign);  
  }  
    
  //************************加密解密**************************  
  public static byte[] encrypt(byte[] bt_plaintext,String str_pubK)throws Exception{  
      PublicKey publicKey = getPublicKey(str_pubK,"RSA");  
      Cipher cipher = Cipher.getInstance("RSA");  
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
      byte[] bt_encrypted = cipher.doFinal(bt_plaintext);  
      return bt_encrypted;  
  }  
    
  public static byte[] decrypt(byte[] bt_encrypted,String str_priK)throws Exception{  
      PrivateKey privateKey = getPrivateKey(str_priK,"SHA256withRSA");  
      Cipher cipher = Cipher.getInstance("SHA256withRSA");  
      cipher.init(Cipher.DECRYPT_MODE, privateKey);  
      byte[] bt_original = cipher.doFinal(bt_encrypted);  
      return bt_original;  
  }  
    
    public static String bytesToHexString(byte[] src) {  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }
}
