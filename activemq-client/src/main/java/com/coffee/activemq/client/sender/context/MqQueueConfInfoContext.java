package com.coffee.activemq.client.sender.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.activemq.client.sender.cache.MqQueueConfCache;
import com.coffee.activemq.common.MqConstants;
import com.coffee.activemq.common.config.model.MqQueueConf;
import com.coffee.activemq.common.db.jdbc.dao.JdbcDAO;

/**
 * @author QM
 */
public class MqQueueConfInfoContext {
	private static Logger LOGGER = LoggerFactory.getLogger(MqQueueConfInfoContext.class);

	@Resource(name = "jdbcDAO")
	private JdbcDAO jdbcDAO;

	@SuppressWarnings("unchecked")
	private Map<String, MqQueueConf> qryQueueConfInfoMap() {
		// 
		final String sql = "SELECT QUEUE_NAME queueName, QUEUE_CODE queueCode, HOST_CODE_RECEIVER hostCodeReceiver FROM MQ_QUEUE_CONF T WHERE T.VALID_ID=1";
		Map<String, MqQueueConf> res = null;
		try {
			final List<Map<String, String>> l = jdbcDAO.queryForList(sql);
			if (l != null && l.size() > 0) {
				res = new HashMap<String, MqQueueConf>();
				for (int i = 0; i < l.size(); i++) {
					final Map<String, String> m = l.get(i);
					if (m == null || StringUtils.isEmpty(m.get(MqConstants.MQ_QUEUENAME))
							|| StringUtils.isEmpty(m.get(MqConstants.MQ_QUEUECODE))
							|| StringUtils.isEmpty(m.get(MqConstants.MQ_HOSTCODERECEIVER))) {
						continue;
					}
					final MqQueueConf vo = new MqQueueConf();
					vo.setQueueName(m.get(MqConstants.MQ_QUEUENAME));
					vo.setHostCodeReceiver(m.get(MqConstants.MQ_HOSTCODERECEIVER));
					// 
					res.put(m.get(MqConstants.MQ_QUEUECODE), vo);
				}
			}
		} catch (final Exception e) {
			LOGGER.error("Failed to init queue.");
		}
		return res;
	}

	public void init() {
		LOGGER.info("Init queue configuration.");
		MqQueueConfCache.getInstance().setQueueConfInfoVOMap(this.qryQueueConfInfoMap());
	}
}
