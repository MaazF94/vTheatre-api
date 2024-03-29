package com.vtheatre.util;
/*
 * Description :
 * 
 *   This file contains a class which converts a UTF-8 string into a cipher string, and vice versa.
 *   The class uses 128-bit AES Algorithm in Cipher Block Chaining (CBC) mode with a UTF-8 key
 *   string and a UTF-8 initial vector string which are hashed by MD5. PKCS5Padding is used
 *   as a padding mode and binary output is encoded by Base64. 
 * 
 * Since :
 * 
 *   2007.10.20
 * 
 * Author :
 * 
 *   JO Hyeong-ryeol (http://www.hyeongryeol.com/6)
 * 
 * Copyright :
 * 
 *   Permission to copy, use, modify, sell and distribute this software is granted provided this
 *   copyright notice appears in all copies. This software is provided "as is" without express
 *   or implied warranty, and with no claim as to its suitability for any purpose.
 *   
 *   Copyright (C) 2007 by JO Hyeong-ryeol.
 * 
 * $Id: StringEncrypter.java 65 2007-12-14 15:29:49Z JO Hyeong-ryeol $
 * 
 */

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Random;

/**
 * This class converts a UTF-8 string into a cipher string, and vice versa. It
 * uses 128-bit AES Algorithm in Cipher Block Chaining (CBC) mode with a UTF-8
 * key string and a UTF-8 initial vector string which are hashed by MD5.
 * PKCS5Padding is used as a padding mode and binary output is encoded by
 * Base64.
 * 
 * @author JO Hyeong-ryeol
 */
public class StringEncrypter {
	private Cipher rijndael;
	private SecretKeySpec key;
	private IvParameterSpec initalVector;
	private static final String CIPHER_PROVIDER = "BC";

	// Security.addProvider(new
	// org.bouncycastle.jce.provider.BouncyCastleProvider());

	/**
	 * Creates a StringEncrypter instance.
	 * 
	 * @param key           A key string which is converted into UTF-8 and hashed by
	 *                      MD5. Null or an empty string is not allowed.
	 * @param initialVector An initial vector string which is converted into UTF-8
	 *                      and hashed by MD5. Null or an empty string is not
	 *                      allowed.
	 * @throws Exception
	 */
	public StringEncrypter(String key, String initialVector) throws Exception {
		if (key == null || key.equals(""))
			throw new Exception("The key can not be null or an empty string.");

		if (initialVector == null || initialVector.equals(""))
			throw new Exception("The initial vector can not be null or an empty string.");

		if (Security.getProvider(CIPHER_PROVIDER) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}

		// Create a AES algorithm.
		this.rijndael = Cipher.getInstance("AES/CBC/PKCS7Padding", CIPHER_PROVIDER);

		// Initialize an encryption key and an initial vector.
		this.key = new SecretKeySpec(key.getBytes("UTF8"), "AES");
		this.initalVector = new IvParameterSpec(initialVector.getBytes("UTF8"));
		/*
		 * MessageDigest md5 = MessageDigest.getInstance("MD5"); this.key = new
		 * SecretKeySpec(md5.digest(key.getBytes("UTF8")), "AES"); this.initalVector =
		 * new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));
		 */
	}

	public StringEncrypter(byte[] key, byte[] initialVector) throws Exception {
		if (key == null || key.equals(""))
			throw new Exception("The key can not be null or an empty string.");

		if (initialVector == null || initialVector.equals(""))
			throw new Exception("The initial vector can not be null or an empty string.");

		if (Security.getProvider(CIPHER_PROVIDER) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}

		// Create a AES algorithm.
		this.rijndael = Cipher.getInstance("AES/CBC/NOPadding", CIPHER_PROVIDER);

		// Initialize an encryption key and an initial vector.
		this.key = new SecretKeySpec(key, "AES");
		this.initalVector = new IvParameterSpec(initialVector);
		/*
		 * MessageDigest md5 = MessageDigest.getInstance("MD5"); this.key = new
		 * SecretKeySpec(md5.digest(key.getBytes("UTF8")), "AES"); this.initalVector =
		 * new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));
		 */
	}

	/**
	 * Encrypts a string.
	 * 
	 * @param value A string to encrypt. It is converted into UTF-8 before being
	 *              encrypted. Null is regarded as an empty string.
	 * @return An encrypted string.
	 * @throws Exception
	 */
	public String encrypt(String value) throws Exception {
		if (value == null)
			value = "";

		// Initialize the cryptography algorithm.
		this.rijndael.init(Cipher.ENCRYPT_MODE, this.key, this.initalVector);

		// Get a UTF-8 byte array from a unicode string.
		byte[] utf8Value = value.getBytes("UTF8");

		// SHAUtil the UTF-8 byte array.
		byte[] encryptedValue = this.rijndael.doFinal(utf8Value);

		// Return a base64 encoded string of the encrypted byte array.
		return Base64Encoder.encode(encryptedValue);
	}

	public byte[] encryptToByte(byte[] value) throws Exception {

		// Initialize the cryptography algorithm.
		this.rijndael.init(Cipher.ENCRYPT_MODE, this.key, this.initalVector);

		// SHAUtil the UTF-8 byte array.
		byte[] encryptedValue = this.rijndael.doFinal(value);

		// encrypted byte array.
		return encryptedValue;
	}

	public String encryptBase64Url(byte[] value) throws Exception {
		// Initialize the cryptography algorithm.
		this.rijndael.init(Cipher.ENCRYPT_MODE, this.key, this.initalVector);

		// SHAUtil the UTF-8 byte array.
		byte[] encryptedValue = this.rijndael.doFinal(value);

		return Base64Encoder.encodeUrl(encryptedValue);
	}

	/**
	 * Decrypts a string which is encrypted with the same key and initial vector.
	 * 
	 * @param value A string to decrypt. It must be a string encrypted with the same
	 *              key and initial vector. Null or an empty string is not allowed.
	 * @return A decrypted string
	 * @throws Exception
	 */
	public String decrypt(String value) throws Exception {
		if (value == null || value.equals(""))
			throw new Exception("The cipher string can not be null or an empty string.");

		// Initialize the cryptography algorithm.
		this.rijndael.init(Cipher.DECRYPT_MODE, this.key, this.initalVector);

		// Get an encrypted byte array from a base64 encoded string.
		byte[] encryptedValue = Base64Encoder.decode(value);

		// Decrypt the byte array.
		byte[] decryptedValue = this.rijndael.doFinal(encryptedValue);

		// Return a string converted from the UTF-8 byte array.
		return new String(decryptedValue, "UTF8");
	}

	public byte[] decryptToByte(byte[] value) throws Exception {
		if (value == null || value.equals(""))
			throw new Exception("The cipher string can not be null or an empty string.");

		// Initialize the cryptography algorithm.
		this.rijndael.init(Cipher.DECRYPT_MODE, this.key, this.initalVector);

		// Decrypt the byte array.
		byte[] decryptedValue = this.rijndael.doFinal(value);

		// Return a string converted from the UTF-8 byte array.
		return decryptedValue;
	}

	public byte[] decryptStringToByte(String value) throws Exception {
		if (value == null || value.equals(""))
			throw new Exception("The cipher string can not be null or an empty string.");

		// Initialize the cryptography algorithm.
		this.rijndael.init(Cipher.DECRYPT_MODE, this.key, this.initalVector);

		// Get an encrypted byte array from a base64 encoded string.
		byte[] encryptedValue = Base64Encoder.decode(value);

		// Decrypt the byte array.
		byte[] decryptedValue = this.rijndael.doFinal(encryptedValue);

		// Return a string converted from the UTF-8 byte array.
		return decryptedValue;
	}

	public String getNonce(int length) {
		Random RAND = new Random();
		String nonce_chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
		String result = "";
		for (int i = 0; i < length; i++) {
			int r = RAND.nextInt(nonce_chars.length());
			result += nonce_chars.substring(r, r + 1);
		}
		return result;
	}
}
