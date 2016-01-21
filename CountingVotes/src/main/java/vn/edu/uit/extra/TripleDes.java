package vn.edu.uit.extra;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TripleDes {

	private String key;
	private String initializationVector;

	
	public TripleDes(String key, String initializationVector) {
		this.key = key;
		this.initializationVector = initializationVector;
	}

	public String encryptText(String plainText) throws Exception {
		// ---- Use specified 3DES key and IV from other source --------------
		byte[] plaintext = plainText.getBytes();
		byte[] tdesKeyData = key.getBytes();
		// byte[] myIV = initializationVector.getBytes();
		Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding ");
		SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
		IvParameterSpec ivspec = new IvParameterSpec(
				initializationVector.getBytes());
		c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
		byte[] cipherText = c3des.doFinal(plaintext);
		return new BASE64Encoder().encode(cipherText);
	}

	public String decryptText(String cipherText) throws Exception {
		byte[] encData = new BASE64Decoder().decodeBuffer(cipherText);
		Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding ");
		byte[] tdesKeyData = key.getBytes();
		SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
		IvParameterSpec ivspec = new IvParameterSpec(
				initializationVector.getBytes());
		decipher.init(Cipher.DECRYPT_MODE, myKey, ivspec);
		byte[] plainText = decipher.doFinal(encData);
		return new String(plainText);
	}

	
}
