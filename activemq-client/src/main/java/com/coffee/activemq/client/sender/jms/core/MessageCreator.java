package com.coffee.activemq.client.sender.jms.core;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class MessageCreator implements org.springframework.jms.core.MessageCreator {
	private final Object message;

	public MessageCreator(final Object message) {
		this.message = message;
	}

	@Override
	public Message createMessage(final Session session) throws JMSException {
		return session.createObjectMessage((Serializable) message);
	}
}
