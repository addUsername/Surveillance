package com.example.demo.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author SERGI
 *
 */
public class Crypt {

	private static final String ALGORITHM = "AES";

	public static void encrypt(byte[] magicKey, File rawFile, File outputFile) {
		doMagic(Cipher.ENCRYPT_MODE, magicKey, rawFile, outputFile);
	}
	
	public static void decrypt(byte[] magicKey, File encodedFile, File outputFile) {
		doMagic(Cipher.DECRYPT_MODE, magicKey, encodedFile, outputFile);
	}
	
	private static void doMagic(int cipherMode, byte[] magicKey, File file, File outputFile) {
	
		try {
			Key secretKey = new SecretKeySpec(magicKey, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(cipherMode, secretKey);
	
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes);
			fis.close();
	
			byte[] outputBytes = cipher.doFinal(bytes);
			FileOutputStream os = new FileOutputStream(outputFile);
			os.write(outputBytes);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}