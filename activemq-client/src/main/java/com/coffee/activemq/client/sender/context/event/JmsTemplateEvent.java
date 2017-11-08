package com.coffee.activemq.client.sender.context.event;

import org.springframework.context.ApplicationEvent;

/**
 * JmsTemplate Event
 * 
 * @author QM
 * */
public class JmsTemplateEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3663044721307707119L;

	/**
	 * 
	 * */
	public JmsTemplateEvent(final Object source) {
		super(source);
	}
}
