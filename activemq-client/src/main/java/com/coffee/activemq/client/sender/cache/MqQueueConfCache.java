package com.coffee.activemq.client.sender.cache;

import java.util.HashMap;
import java.util.Map;

import com.coffee.activemq.common.config.model.MqQueueConf;
import com.coffee.activemq.common.exception.ErrorCode;
import com.coffee.activemq.common.exception.ErrorMsg;
import com.coffee.activemq.common.exception.QueueException;

/**
 * @author QM
 */
public class MqQueueConfCache {

	private static MqQueueConfCache instance = null;

	private Map<String, MqQueueConf> queueConfInfoVOMap = new HashMap<String, MqQueueConf>();

	public synchronized static MqQueueConfCache getInstance() {
		if (instance == null) {
			instance = new MqQueueConfCache();
		}
		return instance;
	}

	public Map<String, MqQueueConf> getQueueConfInfoVOMap() {
		return queueConfInfoVOMap;
	}

	public void setQueueConfInfoVOMap(final Map<String, MqQueueConf> queueConfInfoVOMap) {
		this.queueConfInfoVOMap = queueConfInfoVOMap;
	}

	public MqQueueConf getQueueConfInfoByCode(final String queueCode) throws QueueException {
		if (queueConfInfoVOMap.containsKey(queueCode)) {
			return queueConfInfoVOMap.get(queueCode);
		}
		throw new QueueException(ErrorCode.CONFIG, ErrorMsg.CONFIG);
	}
}
