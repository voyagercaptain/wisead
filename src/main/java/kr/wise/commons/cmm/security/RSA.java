package kr.wise.commons.cmm.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSA {
	/**

     * 1024비트 RSA 키쌍을 생성합니다.

     */

    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {

        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator gen;



        gen = KeyPairGenerator.getInstance("RSA");

        gen.initialize(1024, secureRandom);



        KeyPair keyPair = gen.genKeyPair();



        return keyPair;

    }



    /**

     * Public Key로 RSA 암호화를 수행합니다.

     * @param plainText 암호화할 평문입니다.

     * @param publicKey 공개키 입니다.

     * @return

     */

    public static String encryptRSA(String plainText, PublicKey publicKey)

            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,

                      BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("RSA");



        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());

        String encrypted = Base64.getEncoder().encodeToString(bytePlain);



    	return encrypted;

    }



    /**

     * Private Key로 RAS 복호화를 수행합니다.

     *

     * @param encrypted 암호화된 이진데이터를 base64 인코딩한 문자열 입니다.

     * @param privateKey 복호화를 위한 개인키 입니다.

     * @return

     * @throws Exception

     */

    public static String decryptRSA(String encrypted, PrivateKey privateKey)

    		throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,

    		         BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {



        Cipher cipher = Cipher.getInstance("RSA");



        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bytePlain = cipher.doFinal(byteEncrypted);

        String decrypted = new String(bytePlain, "utf-8");



        return decrypted;

    }
    
    
    public static String base64Encode(byte[] data) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(data);
        return encoded;
    }

    public static byte[] base64Decode(String encryptedData) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoded = decoder.decodeBuffer(encryptedData);
        return decoded;
    }

}
