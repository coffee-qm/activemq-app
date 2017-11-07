package com.coffee.activemq.common;

public interface MqConstants {

	/* Bean Class */
	String ACTIVEMQ_CONNECTION_FACTORY_BEAN_CLASS = "org.apache.activemq.spring.ActiveMQConnectionFactory";

	String POOLED_CONNECTION_FACTORY_BEAN_CLASS = "org.apache.activemq.pool.PooledConnectionFactory";

	String JMS_TEMPLATE_BEAN_CLASS = "org.springframework.jms.core.JmsTemplate";

	String ACTIVEMQ_QUEUE_BEAN_CLASS = "org.apache.activemq.command.ActiveMQQueue";

	String DIC_MESSAGE_LISTENER_BEAN_CLASS = "com.tydic.mq.server.receiver.jms.DicMessageListener";

	String JMS_LISTENER_CONTAINER_BEAN_CLASS = "org.springframework.jms.listener.DefaultMessageListenerContainer";

	String CONSTANT_SYMBOL_BAR = "|";// 分号;

	String CONSTANT_SYMBOL_SLASH_RIGHT = "\\"; // 右斜杠"";

	String CONSTANT_NUMBER_SIGN = "#"; // 井号#

	String CONSTANT_TEXT_FORMAT = ".txt"; // 文本格式

	String CONSTANT_POINT = "."; //点 .

	String MQ_QUEUENAME = "QUEUENAME"; // 队列名称

	String MQ_QUEUECODE = "QUEUECODE"; // 队列编码

	String MQ_HOSTCODERECEIVER = "HOSTCODERECEIVER"; // 

	String MQ_HOST_CODE = "HOST_CODE"; // 主机编码

	String MQ_HOST_IP = "HOST_IP"; // 主机IP

	String MQ_HOST_PORT = "HOST_PORT"; // 主机端口

	String MQ_USER_NAME = "USER_NAME"; // 用户名

	String MQ_USER_PWD = "USER_PWD"; // 用户密码

	String MQ_MAX_CONNECTION = "MAX_CONNECTION"; // 最大连接数

	String MQ_CLASS_NAME = "CLASS_NAME"; // 类名

	String MQ_DATE_SOURCE_TYPE = "DATE_SOURCE_TYPE"; // 数据源

	String MQ_SQL_TYPE = "SQL_TYPE"; //SQL类型（新增、修改）

	String MQ_SQL_VALUE = "SQL_VALUE"; // SQL值

	String MQ_QUEUE_NAME = "QUEUE_NAME"; //

	String MQ_FOLDER_NAME = "FOLDER_NAME"; // 

	String MQ_FILE_NAME = "FILE_NAME"; // 

	String MQ_MAX_FILE_SIZE = "MAX_FILE_SIZE"; //

	String MQ_FILE_SUFFIX = "FILE_SUFFIX"; // 

	String MQ_FILE_BAK_SUFFIX = "FILE_BAK_SUFFIX"; //

	String MQ_CUR_CONSUMERS_NUM = "CUR_CONSUMERS_NUM"; // 
}
