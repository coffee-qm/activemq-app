package com.coffee.activemq.common.config.model;

import java.io.Serializable;

/**
 * @author QM
 */
public class MqQueueConf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3741291246353192155L;
	private Long id;
	private String queueCode;
	private String queueName;
	private String queueDesc;
	private String queueMemo;
	private String hostCodeReceiver;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getQueueCode() {
		return queueCode;
	}

	public void setQueueCode(final String queueCode) {
		this.queueCode = queueCode;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(final String queueName) {
		this.queueName = queueName;
	}

	public String getQueueDesc() {
		return queueDesc;
	}

	public void setQueueDesc(final String queueDesc) {
		this.queueDesc = queueDesc;
	}

	public String getQueueMemo() {
		return queueMemo;
	}

	public void setQueueMemo(final String queueMemo) {
		this.queueMemo = queueMemo;
	}

	public String getHostCodeReceiver() {
		return hostCodeReceiver;
	}

	public void setHostCodeReceiver(final String hostCodeReceiver) {
		this.hostCodeReceiver = hostCodeReceiver;
	}

}
