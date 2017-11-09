package com.coffee.activemq.common.utils;

import org.junit.Test;

/**
 * @author QM
 */
public class EncryptUtilTest {
	private static String KEY = "111111";

	@Test
	public void encode() {
		final String decode = "admin";
		final String encode = EncryptUtil.getInstance().encode(decode, KEY);
		System.out.println("encode:" + encode);
	}

	@Test
	public void decode() {
		final String encode = "D820BC4E6689C4E5";
		final String decode = EncryptUtil.getInstance().decode(encode, KEY);
		System.out.println("decode:" + decode);
	}
}
