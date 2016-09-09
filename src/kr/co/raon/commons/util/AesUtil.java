package kr.co.raon.commons.util;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kr.co.raon.commons.CommonProperty;


/**
  * @FileName : AesUtil.java
  * @Project : VAS
  * @Date : 2016. 3. 11. 
  * @작성자 : redEye
  * @변경이력 :
  * @프로그램 설명 :
  */
public class AesUtil {	
	
	/**
	 * AES 암복호화 키설정
	  * @Method Name : main
	  * @작성일 : 2016. 3. 11.
	  * @작성자 : redEye
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param args
	  * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		/*
		 * key는 프로퍼티에 정의 
		 */
		CommonProperty cf = new CommonProperty();
		String AesKey = StringUtil.null2Str(cf.getProp("AesKey"));
		try { 
			String originalText = "plain text";
			String key = AesKey;
			String en = Encrypt(originalText, key);
			String de = Decrypt(en, key);
			/* 
			System.out.println( "Original Text is " + originalText);
			System.out.println( "Encrypted Text is " + en );
			System.out.println( "Decrypted Text is " + de );
			*/
		}
		catch (Exception ex)
		{
			//System.out.println("오류");
		}
          
	}
	/**
	 * AEC 복호화 
	  * @Method Name : Decrypt
	  * @작성일 : 2016. 3. 11.
	  * @작성자 : redEye
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param text
	  * @param key
	  * @return
	  * @throws Exception
	 */
	public static String Decrypt(String text, String key) throws Exception {
		   Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		   byte[] keyBytes= new byte[16];
		   byte[] b= key.getBytes("UTF-8");
		   int len= b.length;
		   if (len > keyBytes.length) len = keyBytes.length;
		   System.arraycopy(b, 0, keyBytes, 0, len);
		   SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		   IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		   cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
		
		  //  BASE64Decoder decoder = new BASE64Decoder();
		   // byte [] results = cipher.doFinal(decoder.decodeBuffer(text));
		   // byte [] results = cipher.doFinal(text);
		   // return new String(results,"UTF-8");
		   return text ;
     }
	/**
	 * AES 암호화
	  * @Method Name : Encrypt
	  * @작성일 : 2016. 3. 11.
	  * @작성자 : redEye
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param text
	  * @param key
	  * @return
	  * @throws Exception
	 */
    public static String Encrypt(String text, String key) throws Exception	{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes= new byte[16];
        byte[] b= key.getBytes("UTF-8");
        int len= b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        // BASE64Encoder encoder = new BASE64Encoder();
        // return encoder.encode(results);
        return text;
     }
	
}
