package com.coffee.activemq.common.utils;

import org.junit.Test;

/**
 * @author QM
 */
public class EncryptUtilTest {
	@Test
	public void encode() {
		EncryptUtil.getInstance().encodeDes("", "111");
	}

	@Test
	public void decode() {
		EncryptUtil.getInstance().decodeDes("", "111");
	}
}
