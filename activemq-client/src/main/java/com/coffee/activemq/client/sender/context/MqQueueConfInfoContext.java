package com.coffee.activemq.client.sender.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.coffee.activemq.client.sender.cache.MqQueueConfCache;
import com.coffee.activemq.common.MqConstants;
import com.coffee.activemq.common.config.model.MqQueueConf;
import com.coffee.activemq.common.db.jdbc.dao.JdbcDAO;

/**
 * @author QM
 */
public class MqQueueConfInfoContext {

	@Resource(name = "jdbcDAO")
	private JdbcDAO jdbcDAO;

	@SuppressWarnings("unchecked")
	private Map<String, MqQueueConf> qryQueueConfInfoMap() {
		final String sql = "SELECT QUEUE_NAME queueName, QUEUE_CODE queueCode, HOST_CODE_RECEIVER hostCodeReceiver FROM MQ_QUEUE_CONF T WHERE T.VALID_ID=1";
		Map<String, MqQueueConf> res = null;
		try {
			final List<Map<String, String>> l = jdbcDAO.queryForList(sql);
			if (l != null && l.size() > 0) {
				res = new HashMap<String, MqQueueConf>();
				for (int i = 0; i < l.size(); i++) {
					final Map<String, String> m = l.get(i);
					if (m == null || m.get(MqConstants.MQ_QUEUENAME) == null
							|| m.get(MqConstants.MQ_QUEUECODE) == null
							|| m.get(MqConstants.MQ_HOSTCODERECEIVER) == null) {
						continue;
					}
					System.out.println("[" + m.get(MqConstants.MQ_QUEUECODE).toString() + "] ["
							+ m.get(MqConstants.MQ_QUEUENAME).toString() + "] ["
							+ m.get(MqConstants.MQ_HOSTCODERECEIVER).toString() + "]");
					final MqQueueConf vo = new MqQueueConf();
					vo.setQueueName(m.get(MqConstants.MQ_QUEUENAME).toString());
					vo.setHostCodeReceiver(m.get(MqConstants.MQ_HOSTCODERECEIVER).toString());
					res.put(m.get(MqConstants.MQ_QUEUECODE).toString(), vo);
				}
			}
		} catch (final Exception e) {
			System.err.println("初始化队列配置信息异常！");
		}
		return res;
	}

	public void init() {
		System.out.println("初始化队列配置信息！");
		MqQueueConfCache.getInstance().setQueueConfInfoVOMap(this.qryQueueConfInfoMap());
	}
}
