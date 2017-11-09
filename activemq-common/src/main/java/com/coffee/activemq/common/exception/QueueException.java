package com.coffee.activemq.common.exception;

public class QueueException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3228118549975878672L;

	public QueueException(final String message) {
		super(message);
	}

	public QueueException(final int errorCode, final String message) {
		super(errorCode, message);
	}

	public QueueException(final int errorCode, final Throwable throwable) {
		super(errorCode, throwable);
	}

	public QueueException(final Throwable throwable) {
		super(throwable);
	}
}
