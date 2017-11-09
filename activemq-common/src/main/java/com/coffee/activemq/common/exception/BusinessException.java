package com.coffee.activemq.common.exception;

/**
 * @author QM
 * */
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4194033122908893859L;

	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final int errorCode, final String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(final Throwable t) {
		super(t);
	}

	public BusinessException(final int errorCode, final Throwable t) {
		this.errorCode = errorCode;
	}
}
