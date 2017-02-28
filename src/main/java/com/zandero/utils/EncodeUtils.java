package com.zandero.utils;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.StrongTextEncryptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Common purpose utils for encoding / decoding
 */
public final class EncodeUtils {

	private EncodeUtils() {
		// hiding constructor
	}

	public static final String UTF_8 = "UTF-8";

	static final int LOOKUPLENGTH = 16;

	static final char[] lookUpHexAlphabet = new char[LOOKUPLENGTH];

	static {
		for (int i = 0;
		     i < 10;
		     i++) {
			lookUpHexAlphabet[i] = (char) ('0' + i);
		}
		for (int i = 10;
		     i <= 15;
		     i++) {
			lookUpHexAlphabet[i] = (char) ('A' + i - 10);
		}
	}

	static public String bin2hex(byte[] binaryData) {

		if (binaryData == null) {
			return null;
		}
		int lengthData = binaryData.length;
		int lengthEncode = lengthData * 2;
		char[] encodedData = new char[lengthEncode];
		int temp;
		for (int i = 0;
		     i < lengthData;
		     i++) {
			temp = binaryData[i];
			if (temp < 0) {
				temp += 256;
			}
			encodedData[i * 2] = lookUpHexAlphabet[temp >> 4];
			encodedData[i * 2 + 1] = lookUpHexAlphabet[temp & 0xf];
		}
		return new String(encodedData);
	}

	public static String md5(String text) {

		try {
			return md5(text.getBytes(UTF_8));
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("The device lacks UTF-8 support!", e);
		}
	}

	public static String md5(byte[] data) {

		return hash(data, "md5");
	}

	public static String sha256(String text) {

		try {
			return hash(text.getBytes(UTF_8), "SHA-256");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("The device lacks UTF-8 support!", e);
		}
	}

	private static String hash(byte[] data, String algorithm) {

		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			digest.update(data);
			byte[] digestHash = digest.digest();
			return bin2hexAndroid(digestHash);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("This device has no SHA-256 algorithm available!");
		}
	}

	public static String sha256Hex(String in) {

		try {
			byte[] bytes = MessageDigest.getInstance("SHA-256").digest(in.getBytes(Charset.forName(UTF_8)));
			return bin2hexAndroid(bytes); // hex encoded 64 bytes
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unexpected error: SHA-256 digest algorithm not available.");
		}
	}

	public static String bin2hexAndroid(byte[] bytes) {

		BigInteger bi = new BigInteger(1, bytes);
		return String.format(Locale.US, "%0" + (bytes.length << 1) + "X", bi).toLowerCase(Locale.US);
	}

	public static byte sha256ReturnFirstByte(String in) {

		try {
			byte[] bytes = MessageDigest.getInstance("SHA-256").digest(in.getBytes(Charset.forName(UTF_8)));
			return bytes[0];
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unexpected error: SHA-256 digest algorithm not available.");
		}
	}

/*	public static String md5base64(String in) {

		try {
			return Base64Android.encodeToString(MessageDigest.getInstance("MD5").digest(in.getBytes(Charset.forName(EncodeUtils.UTF_8))), Base64Android.NO_WRAP | Base64Android.NO_PADDING);
		}
		catch (NoSuchAlgorithmException e) {
			log.severe("Unexpected error: MD5 digest algorithm not available.");
		}
		return null;
	}

	public static String md5base64(byte[] in) {

		try {
			return Base64Android.encodeToString(MessageDigest.getInstance("MD5").digest(in), Base64Android.NO_WRAP | Base64Android.NO_PADDING);
		}
		catch (NoSuchAlgorithmException e) {
			log.severe("Unexpected error: MD5 digest algorithm not available.");
		}
		return null;
	}*/


	public static String md5Hex(String in) {

		if (StringUtils.isNullOrEmptyTrimmed(in)) {
			throw new IllegalArgumentException("Missing crypt key!");
		}

		try {
			byte[] bytes = MessageDigest.getInstance("MD5").digest(in.getBytes(Charset.forName(UTF_8)));
			return bin2hex(bytes); // hex encoded 32bytes
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unexpected error: MD5 digest algorithm not available.");
		}
	}

	public static String md5Hex(byte[] bytes) {

		try {
			bytes = MessageDigest.getInstance("MD5").digest(bytes);
			return bin2hex(bytes); // hex encoded 32bytes
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unexpected error: MD5 digest algorithm not available.");
		}
	}

	public static byte[] getBytes(InputStream is) throws IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int len;
		byte[] data = new byte[100000];
		while ((len = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, len);
		}

		buffer.flush();
		return buffer.toByteArray();
	}

	public static String encrypt(String value, String key) {

		StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
		textEncryptor.setPassword(key);
		return textEncryptor.encrypt(value);
	}

	public static String decrypt(String value, String key) {

		StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
		textEncryptor.setPassword(key);
		try {
			return textEncryptor.decrypt(value);
		}
		catch (EncryptionOperationNotPossibleException e) {
			// invalid key given ... throw illegal argument exception
			throw new IllegalArgumentException("Invalid password given!");
		}
	}
}

