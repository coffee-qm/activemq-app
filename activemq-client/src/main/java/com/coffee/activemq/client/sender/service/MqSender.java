package com.coffee.activemq.client.sender.service;

import com.coffee.activemq.common.exception.QueueException;

public interface MqSender {

	/**
	 * Send message to queue
	 * 
	 * @param queueCode
	 *            queueCode
	 * @param message
	 *            message
	 * @throws QueueException
	 *             QueueException
	 */
	public void send(final String queueCode, final Object message) throws QueueException;
}
