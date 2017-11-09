package com.coffee.activemq.client.sender.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jms.core.JmsTemplate;

import com.coffee.activemq.common.exception.ErrorCode;
import com.coffee.activemq.common.exception.ErrorMsg;
import com.coffee.activemq.common.exception.QueueException;

/**
 * @author QM
 */
public class MqJmsTemplateCache {

	private static MqJmsTemplateCache instance = null;

	private Map<String, JmsTemplate> jmsTemplateMap = new HashMap<String, JmsTemplate>();

	public synchronized static MqJmsTemplateCache getInstance() {
		if (instance == null) {
			instance = new MqJmsTemplateCache();
		}
		return instance;
	}

	public void setJmsTemplateMap(final Map<String, JmsTemplate> jmsTemplateMap) {
		this.jmsTemplateMap = jmsTemplateMap;
	}

	public JmsTemplate getJmsTemplate(final String key) throws QueueException {
		if (jmsTemplateMap.containsKey(key)) {
			return jmsTemplateMap.get(key);
		}
		throw new QueueException(ErrorCode.CONFIG, ErrorMsg.CONFIG);
	}
}
