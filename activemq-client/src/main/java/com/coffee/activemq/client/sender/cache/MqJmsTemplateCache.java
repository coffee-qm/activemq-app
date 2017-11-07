package com.coffee.activemq.client.sender.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jms.core.JmsTemplate;

public class MqJmsTemplateCache {

	private static MqJmsTemplateCache instance = null;
	private Map<String, JmsTemplate> jmsTemplateMap = new HashMap<String, JmsTemplate>();// 

	public synchronized static MqJmsTemplateCache getInstance() {
		if (instance == null) {
			instance = new MqJmsTemplateCache();
		}
		return instance;
	}

	public void setJmsTemplateMap(final Map<String, JmsTemplate> jmsTemplateMap) {
		this.jmsTemplateMap = jmsTemplateMap;
	}

	public Map<String, JmsTemplate> getJmsTemplateMap() {
		return jmsTemplateMap;
	}

}
