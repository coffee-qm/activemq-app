package com.coffee.activemq.server.context.event;

import org.springframework.context.ApplicationEvent;

/**
 * JmsListenerContainer Event
 * */
public class JmsListenerContainerEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2433092961385228404L;

	/**
	 * 
	 * */
	public JmsListenerContainerEvent(final Object source) {
		super(source);
	}
}
