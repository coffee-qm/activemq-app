package com.coffee.activemq.common.exception;

/**
 * @author QM
 * */
public class QueueException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 496707876766298630L;

	public QueueException(final int errorCode, final String message) {
		super(errorCode, message);
	}
}
