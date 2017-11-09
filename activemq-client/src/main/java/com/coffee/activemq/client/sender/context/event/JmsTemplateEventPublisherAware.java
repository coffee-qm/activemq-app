package com.coffee.activemq.client.sender.context.event;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.coffee.activemq.common.MqConstants;
import com.coffee.activemq.common.config.model.MqJmsTemplateInfo;
import com.coffee.activemq.common.db.jdbc.dao.JdbcDAO;

/**
 * JmsTemplate Event Publisher Aware
 * 
 * @author QM
 * */
public class JmsTemplateEventPublisherAware implements ApplicationEventPublisherAware {
	private static Logger LOGGER = LoggerFactory.getLogger(JmsTemplateEventPublisherAware.class);

	private ApplicationEventPublisher jmsTemplateEventPublisher;

	@Override
	public void setApplicationEventPublisher(
			final ApplicationEventPublisher applicationEventPublisher) {
		this.jmsTemplateEventPublisher = applicationEventPublisher;
	}

	@Resource(name = "jdbcDAO")
	private JdbcDAO jdbcDAO;

	private Map<String, MqJmsTemplateInfo> getMqMiddleWareConfig() {
		final String sql = "SELECT HOST_CODE, HOST_IP, HOST_PORT, USER_NAME, USER_PWD, MAX_CONNECTION FROM MQ_MIDDLEWARE_CONF T WHERE T.VALID_ID = 1";
		List<Map<String, String>> confList = null;
		try {
			confList = jdbcDAO.queryForList(sql);
		} catch (final SQLException e) {
			LOGGER.error("日志队列初始化时查询中间件主机配置异常！", e);
		}
		// 
		Map<String, MqJmsTemplateInfo> mqJmsTemplateInfoMap = null;
		if (CollectionUtils.isNotEmpty(confList)) {
			// 
			mqJmsTemplateInfoMap = new HashMap<String, MqJmsTemplateInfo>();
			final Iterator<Map<String, String>> iterator = confList.iterator();
			while (iterator.hasNext()) {
				final Map<String, String> map = iterator.next();
				if (MapUtils.isNotEmpty(map)
						&& StringUtils.isNotEmpty(map.get(MqConstants.MQ_HOST_CODE))
						&& StringUtils.isNotEmpty(map.get(MqConstants.MQ_HOST_IP))
						&& StringUtils.isNotEmpty(map.get(MqConstants.MQ_HOST_PORT))
						&& StringUtils.isNotEmpty(map.get(MqConstants.MQ_USER_NAME))
						&& StringUtils.isNotEmpty(map.get(MqConstants.MQ_USER_PWD))) {
					//					String key = "MIDDLEWARE_";
					//					String sid = map.get("HOST_CODE") + "";
					//					key = key.concat(sid);
					final String key = map.get(MqConstants.MQ_HOST_CODE) + "";
					// 
					final MqJmsTemplateInfo vo = new MqJmsTemplateInfo();
					vo.setHostCode(map.get(MqConstants.MQ_HOST_CODE));
					vo.setHostIp(map.get(MqConstants.MQ_HOST_IP));
					vo.setHostPort(map.get(MqConstants.MQ_HOST_PORT));
					vo.setUserName(map.get(MqConstants.MQ_USER_NAME));
					vo.setUserPwd(map.get(MqConstants.MQ_USER_PWD));
					vo.setMaxConnections(map.get(MqConstants.MQ_MAX_CONNECTION) == null ? 1
							: Integer.parseInt(map.get(MqConstants.MQ_MAX_CONNECTION)));
					mqJmsTemplateInfoMap.put(key, vo);
				}
			}
		}
		return mqJmsTemplateInfoMap;
	}

	public void publishEvent() {
		final Map<String, MqJmsTemplateInfo> mqJmsTemplateInfoMap = getMqMiddleWareConfig();
		final JmsTemplateEvent jmsTemplateEvent = new JmsTemplateEvent(mqJmsTemplateInfoMap);
		this.jmsTemplateEventPublisher.publishEvent(jmsTemplateEvent);
	}
}
