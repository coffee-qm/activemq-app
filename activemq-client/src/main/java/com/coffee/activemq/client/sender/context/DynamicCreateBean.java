package com.coffee.activemq.client.sender.context;

import java.util.Iterator;
import java.util.Map;

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
import org.springframework.jms.core.JmsTemplate;

import com.coffee.activemq.client.sender.cache.MqJmsTemplateCache;
import com.coffee.activemq.client.sender.context.event.JmsTemplateEvent;
import com.coffee.activemq.common.MqConstants;
import com.coffee.activemq.common.config.model.MqJmsTemplateInfo;
import com.coffee.activemq.common.utils.EncryptUtil;

/**
 * 动态注册JmsTemplate Bean
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
		if (event instanceof JmsTemplateEvent) {
			LOGGER.info("监听到JmsTemplateEvent");
			try {
				this.regDynamicJmsTemplatBean(event.getSource());
			} catch (final Exception e) {
				LOGGER.error("Failed to register bean.", e);
			}
		}
	}

	/**
	 * 
	 * */
	private void regDynamicJmsTemplatBean(final Object o) {
		// 把JmsTemplate bean注册到容器中
		if(o instanceof Map){
			final Map<String, MqJmsTemplateInfo> mqJmsTemplateInfoMap = (Map<String, MqJmsTemplateInfo>) o;
			this.addJmsTemplateBeanToApp(mqJmsTemplateInfoMap);
		}
	}

	@SuppressWarnings("unchecked")
	private void addJmsTemplateBeanToApp(final Map<String, MqJmsTemplateInfo> mqJmsTemplateInfoMap) {
		// 获取MQ中间件配置信息Map
		if (MapUtils.isNotEmpty(mqJmsTemplateInfoMap)) {
			// 
			final DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app
					.getAutowireCapableBeanFactory();
			// 
			BeanDefinitionBuilder bdb;
			BeanDefinitionBuilder bdb_cf; // 链接
			BeanDefinitionBuilder bdb_cf_pool; // 链接池
			final Iterator<String> iter = mqJmsTemplateInfoMap.keySet().iterator();
			// 根据数据源得到数据，动态创建bean 并将bean注册到applicationContext中去
			while (iter.hasNext()) {
				// bean ID
				final String key = iter.next();
				// 
				final MqJmsTemplateInfo vo = mqJmsTemplateInfoMap.get(key);
				// 1. 链接
				// 创建bean
				final String beanKey_cf = "ACTIVEMQ_CONNECTION_FACTORY_" + key;
				bdb_cf = BeanDefinitionBuilder
						.rootBeanDefinition(MqConstants.ACTIVEMQ_CONNECTION_FACTORY_BEAN_CLASS);
				bdb_cf.getBeanDefinition().setAttribute("id", beanKey_cf);
				bdb_cf.addPropertyValue("brokerURL",
						"tcp://" + vo.getHostIp() + ":" + vo.getHostPort());
				bdb_cf.addPropertyValue("userName", vo.getUserName());
				bdb_cf.addPropertyValue("password",
						EncryptUtil.getInstance().DESdecode(vo.getUserPwd(), "activemq"));
				// 注册bean
				acf.registerBeanDefinition(beanKey_cf, bdb_cf.getBeanDefinition());

				// 2. 链接池
				// 创建bean
				final String beanKey_cf_pool = "POOLED_CONNECTION_FACTORY_" + key;
				bdb_cf_pool = BeanDefinitionBuilder
						.rootBeanDefinition(MqConstants.POOLED_CONNECTION_FACTORY_BEAN_CLASS);
				bdb_cf_pool.getBeanDefinition().setAttribute("id", beanKey_cf_pool);
				bdb_cf_pool.addPropertyReference("connectionFactory", beanKey_cf);
				bdb_cf_pool.addPropertyValue("maxConnections", vo.getMaxConnections());
				bdb_cf_pool.setDestroyMethodName("stop");
				// 注册bean
				acf.registerBeanDefinition(beanKey_cf_pool, bdb_cf_pool.getBeanDefinition());

				// 3. JmsTemplate
				// 创建bean
				final String beanKey = "jmsQueueTemplate_" + key;
				bdb = BeanDefinitionBuilder.rootBeanDefinition(MqConstants.JMS_TEMPLATE_BEAN_CLASS);
				bdb.getBeanDefinition().setAttribute("id", beanKey);
				bdb.addConstructorArgReference(beanKey_cf_pool);
				bdb.addPropertyValue("pubSubDomain", "false");
				// 注册bean
				acf.registerBeanDefinition(beanKey, bdb.getBeanDefinition());

				// 置于缓存
				MqJmsTemplateCache.getInstance().getJmsTemplateMap()
						.put(key, (JmsTemplate) acf.getBean(beanKey));
			}
		}
	}
}
