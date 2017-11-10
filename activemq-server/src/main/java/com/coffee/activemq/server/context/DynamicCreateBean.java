package com.coffee.activemq.server.context;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import com.coffee.activemq.common.consts.MqConstants;
import com.coffee.activemq.server.config.model.MqJmsListenerContainerInfo;
import com.coffee.activemq.server.context.event.JmsListenerContainerEvent;

/**
 * DynamicCreateBean
 * 
 * @author QM
 * */
public class DynamicCreateBean implements ApplicationContextAware, ApplicationListener {

	private static Logger LOGGER = LoggerFactory.getLogger(DynamicCreateBean.class);

	private ConfigurableApplicationContext app;

	@Override
	public void setApplicationContext(final ApplicationContext ac) throws BeansException {
		this.app = (ConfigurableApplicationContext) ac;
	}

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		// 如果是容器刷新事件OR Start Event
		if (event instanceof JmsListenerContainerEvent) {
			LOGGER.info("监听到JmsListenerContainerEvent");
			try {
				regDynamicJmsListenerContainerBean(event.getSource());
			} catch (final Exception e) {
				LOGGER.error("Failed to register bean.", e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void regDynamicJmsListenerContainerBean(final Object o) {
		// 把JmsTemplate bean注册到容器中
		if (o instanceof Map) {
			final Map<String, MqJmsListenerContainerInfo> mqJmsListenerContainerInfo = (Map<String, MqJmsListenerContainerInfo>) o;
			this.addJmsListenerContainerBeanToApp(mqJmsListenerContainerInfo);
		}
	}

	private void addJmsListenerContainerBeanToApp(
			final Map<String, MqJmsListenerContainerInfo> mqJmsListenerContainerInfo) {
		// 获取MQ中间件配置信息Map
		if (MapUtils.isEmpty(mqJmsListenerContainerInfo)) {
			return;
		}
		// 
		final DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app
				.getAutowireCapableBeanFactory();
		// 
		BeanDefinitionBuilder bdb;
		BeanDefinitionBuilder bdbConn; // 链接
		BeanDefinitionBuilder bdbConnPool; // 链接池
		BeanDefinitionBuilder bdbQueueDest; // 目标队列
		BeanDefinitionBuilder bdbMsgListener; // 消息监听器
		// 
		// 根据数据源得到数据，动态创建bean 并将bean注册到applicationContext中去
		for (final Entry<String, MqJmsListenerContainerInfo> entry : mqJmsListenerContainerInfo
				.entrySet()) {
			// 
			final String key = entry.getKey();
			final MqJmsListenerContainerInfo vo = entry.getValue();
			// bean ID
			// 
			// 1. 链接
			// 创建bean
			final String beanKeyConn = "ACTIVEMQ_CONNECTION_FACTORY_" + key;
			bdbConn = BeanDefinitionBuilder
					.rootBeanDefinition(MqConstants.ACTIVEMQ_CONNECTION_FACTORY_BEAN_CLASS);
			bdbConn.getBeanDefinition().setAttribute("id", beanKeyConn);
			bdbConn.addPropertyValue("brokerURL",
					"failover:(tcp://" + vo.getHostIp() + ":" + vo.getHostPort() + ")");
			bdbConn.addPropertyValue("userName", vo.getUserName());
			bdbConn.addPropertyValue("password", vo.getUserPwd());
			// 注册bean
			acf.registerBeanDefinition(beanKeyConn, bdbConn.getBeanDefinition());

			// 2. 链接池
			// 创建bean
			final String beanKeyConnPool = "POOLED_CONNECTION_FACTORY_" + key;
			bdbConnPool = BeanDefinitionBuilder
					.rootBeanDefinition(MqConstants.POOLED_CONNECTION_FACTORY_BEAN_CLASS);
			bdbConnPool.getBeanDefinition().setAttribute("id", beanKeyConnPool);
			bdbConnPool.addPropertyReference("connectionFactory", beanKeyConn);
			bdbConnPool.addPropertyValue("maxConnections", vo.getMaxConnections());
			bdbConnPool.setDestroyMethodName("stop");
			// 注册bean
			acf.registerBeanDefinition(beanKeyConnPool, bdbConnPool.getBeanDefinition());

			// 3. Queue Destination
			// 创建bean
			final String beanKeyQueueDest = "queueDestination_" + key;
			bdbQueueDest = BeanDefinitionBuilder
					.rootBeanDefinition(MqConstants.ACTIVEMQ_QUEUE_BEAN_CLASS);
			bdbQueueDest.getBeanDefinition().setAttribute("id", beanKeyQueueDest);
			bdbQueueDest.addConstructorArgValue(vo.getQueueNameStr());
			// 注册bean
			acf.registerBeanDefinition(beanKeyQueueDest, bdbQueueDest.getBeanDefinition());

			// 4. MessageListener
			// 创建bean
			final String beanKeyMsgListener = "messageListener_" + key;
			bdbMsgListener = BeanDefinitionBuilder
					.rootBeanDefinition(MqConstants.MESSAGE_LISTENER_BEAN_CLASS);
			bdbMsgListener.getBeanDefinition().setAttribute("id", beanKeyMsgListener);
			// 注册bean
			acf.registerBeanDefinition(beanKeyMsgListener, bdbMsgListener.getBeanDefinition());

			// 5. JmsListenerContainer
			// 创建bean
			final String beanKey = "jmsListenerContainer_" + key;
			bdb = BeanDefinitionBuilder
					.rootBeanDefinition(MqConstants.JMS_LISTENER_CONTAINER_BEAN_CLASS);
			bdb.getBeanDefinition().setAttribute("id", beanKey);
			bdb.addPropertyReference("connectionFactory", beanKeyConnPool);
			bdb.addPropertyReference("destination", beanKeyQueueDest);
			bdb.addPropertyReference("messageListener", beanKeyMsgListener);
			bdb.addPropertyValue("concurrentConsumers", vo.getConcurrentConsumers());
			// 注册bean
			acf.registerBeanDefinition(beanKey, bdb.getBeanDefinition());
			// 
			acf.getBean(beanKey);
		}
	}
}
