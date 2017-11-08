package com.coffee.activemq.common.config.model;

/**
 * @author QM
 */
public class MqJmsTemplateInfo {
	private Long id;
	/**
	 * 编码
	 */
	private String hostCode;
	/**
	 * IP
	 */
	private String hostIp;
	/**
	 * 端口号
	 */
	private String hostPort;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String userPwd;
	/**
	 * 最大链接数
	 */
	private Integer maxConnections;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

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

}
