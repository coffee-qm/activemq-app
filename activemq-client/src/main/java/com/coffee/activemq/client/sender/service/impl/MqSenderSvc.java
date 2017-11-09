package com.coffee.activemq.client.sender.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.coffee.activemq.client.sender.cache.MqJmsTemplateCache;
import com.coffee.activemq.client.sender.cache.MqQueueConfCache;
import com.coffee.activemq.client.sender.jms.core.MessageCreator;
import com.coffee.activemq.common.config.model.MqQueueConf;
import com.coffee.activemq.common.exception.ErrorCode;
import com.coffee.activemq.common.exception.ErrorMsg;
import com.coffee.activemq.common.exception.QueueException;

/**
 * 队列发送客户端
 * 
 * @author QM
 * */
public class MqSenderSvc {

	private static final Logger LOGGER = LoggerFactory.getLogger(MqSenderSvc.class);

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
	public void send(final String queueCode, final Object message) throws QueueException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Try to send message to:{}", queueCode);
		}
		if (StringUtils.isEmpty(queueCode) || message == null) {
			throw new QueueException(ErrorCode.ILLEGAL_DATA, "队列编码或消息对象为空");
		}
		// 1. 获取配置信息
		final MqQueueConf vo = MqQueueConfCache.getInstance().getQueueConfInfoByCode(queueCode);
		// 2. 获取传输对象
		final JmsTemplate jmsTemplate = MqJmsTemplateCache.getInstance().getJmsTemplate(
				vo.getHostCodeReceiver());
		// 3. 发送
		try {
			jmsTemplate.send(vo.getQueueName(), new MessageCreator(message));
		} catch (final Exception e) {
			LOGGER.error("Failed to send message to queue:{}", queueCode);
			throw new QueueException(ErrorCode.SEND, ErrorMsg.SEND);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End of send message to:{}", queueCode);
		}
	}
}
