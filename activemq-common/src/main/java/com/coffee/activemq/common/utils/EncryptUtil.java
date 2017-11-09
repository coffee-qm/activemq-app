package com.coffee.activemq.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author QM
 */
public class EncryptUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);

	private static final String DES = "DES";

	/**
	 * 编码格式：默认null为GBK
	 */
	private static final String CHARSET_UTF8 = "UTF8";

	private static final String ALGORITHM_SHA1PRNG = "SHA1PRNG";

	private static final String CIPHER = "DES/ECB/PKCS5Padding";

	private static final int KEY_SIZE_DES = 56;

	public static EncryptUtil instance;

	public static EncryptUtil getInstance() {
		if (instance == null) {
			instance = new EncryptUtil();
		}
		return instance;
	}

	public String encode(final String res, final String key) {
		return keyGeneratorES(res, DES, key, KEY_SIZE_DES, Boolean.TRUE);
	}

	public String decode(final String res, final String key) {
		return keyGeneratorES(res, DES, key, KEY_SIZE_DES, Boolean.FALSE);
	}

	private String keyGeneratorES(final String res, final String algorithm, final String key,
			final int keysize, final boolean isEncode) {
		try {
			//
			final KeyGenerator kg = KeyGenerator.getInstance(algorithm);
			// 
			final SecureRandom secureRandom = SecureRandom.getInstance(ALGORITHM_SHA1PRNG);
			if (keysize == 0) {
				final byte[] keyBytes = CHARSET_UTF8 == null ? key.getBytes() : key
						.getBytes(CHARSET_UTF8);
				secureRandom.setSeed(keyBytes);
				kg.init(secureRandom);
			} else if (key == null) {
				kg.init(keysize);
			} else {
				final byte[] keyBytes = CHARSET_UTF8 == null ? key.getBytes() : key
						.getBytes(CHARSET_UTF8);
				secureRandom.setSeed(keyBytes);
				kg.init(keysize, secureRandom);
			}
			final SecretKey sk = kg.generateKey();
			final SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
			final Cipher cipher = Cipher.getInstance(CIPHER);
			if (isEncode) {
				cipher.init(Cipher.ENCRYPT_MODE, sks);
				final byte[] resBytes = CHARSET_UTF8 == null ? res.getBytes() : res
						.getBytes(CHARSET_UTF8);
				return this.parseByte2HexStr(cipher.doFinal(resBytes));
			} else {
				cipher.init(Cipher.DECRYPT_MODE, sks);
				return new String(cipher.doFinal(this.parseHexStr2Byte(res)));
			}
		} catch (final Exception e) {
			LOGGER.error("", e);
		}
		return null;
	}

	private String parseByte2HexStr(final byte buf[]) {
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

	private byte[] parseHexStr2Byte(final String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		final byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			final int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			final int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
