package com.coffee.activemq.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	public static final String DES = "DES";
	/** 编码格式；默认null为GBK */
	public String charset = "UTF8"; // 
	public int keysizeDES = 56;
	public static EncryptUtil instance;

	public static EncryptUtil getInstance() {
		if (instance == null) {
			instance = new EncryptUtil();
		}
		return instance;
	}

	private String keyGeneratorES(final String res, final String algorithm,
			final String key, final int keysize, final boolean isEncode) {
		try {
			final KeyGenerator kg = KeyGenerator.getInstance(algorithm);
			final SecureRandom secureRandom = SecureRandom
					.getInstance("SHA1PRNG");
			if (keysize == 0) {
				final byte[] keyBytes = charset == null ? key.getBytes() : key
						.getBytes(charset);
				secureRandom.setSeed(keyBytes);
				kg.init(secureRandom);
			} else if (key == null) {
				kg.init(keysize);
			} else {
				final byte[] keyBytes = charset == null ? key.getBytes() : key
						.getBytes(charset);
				secureRandom.setSeed(keyBytes);
				kg.init(keysize, secureRandom);
			}
			final SecretKey sk = kg.generateKey();
			final SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(),
					algorithm);
			final Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			if (isEncode) {
				cipher.init(Cipher.ENCRYPT_MODE, sks);
				final byte[] resBytes = charset == null ? res.getBytes() : res
						.getBytes(charset);
				return parseByte2HexStr(cipher.doFinal(resBytes));
			} else {
				cipher.init(Cipher.DECRYPT_MODE, sks);
				return new String(cipher.doFinal(parseHexStr2Byte(res)));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String parseByte2HexStr(final byte buf[]) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] parseHexStr2Byte(final String hexStr) {
		if (hexStr.length() < 1)
			return null;
		final byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			final int high = Integer.parseInt(
					hexStr.substring(i * 2, i * 2 + 1), 16);
			final int low = Integer.parseInt(
					hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public String DESencode(final String res, final String key) {
		return keyGeneratorES(res, DES, key, keysizeDES, true);
	}

	public String DESdecode(final String res, final String key) {
		return keyGeneratorES(res, DES, key, keysizeDES, false);
	}

	public static void main(final String[] args) {
		final String encryptStr = EncryptUtil.getInstance().DESencode(args[0],
				"activemq");
		System.out.println("[" + args[0] + "][" + encryptStr + "]["
				+ encryptStr.length() + "]["
				+ EncryptUtil.getInstance().DESdecode(encryptStr, "activemq")
				+ "]");
	}
}
