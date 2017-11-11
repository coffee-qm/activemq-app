package com.coffee.activemq.server.config.model;

/**
 * @author QM
 * */
public class MqJmsListenerContainerInfo {

	private String hostCode; // 编码
	private String hostIp; // IP
	private String hostPort;// 端口号
	private String userName; // 用户名
	private String userPwd; // 密码
	private Integer maxConnections; // 最大链接数
	private String queueNameStr; // 监听的队列名称串
	private Integer concurrentConsumers; // 消费者数

	public String getHostCode() {
		return hostCode;
	}

	public void setHostCode(final String hostCode) {
		this.hostCode = hostCode;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(final String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostPort() {
		return hostPort;
	}

	public void setHostPort(final String hostPort) {
		this.hostPort = hostPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(final String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(final Integer maxConnections) {
		this.maxConnections = maxConnections;
	}

	public String getQueueNameStr() {
		return queueNameStr;
	}

	public void setQueueNameStr(final String queueNameStr) {
		this.queueNameStr = queueNameStr;
	}

	public Integer getConcurrentConsumers() {
		return concurrentConsumers;
	}

	public void setConcurrentConsumers(final Integer concurrentConsumers) {
		this.concurrentConsumers = concurrentConsumers;
	}

}
