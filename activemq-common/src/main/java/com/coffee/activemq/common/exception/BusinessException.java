package com.coffee.activemq.common.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -7245736834087547904L;

	private Long exeptionTypeId;

	private Long objId;

	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final int errorCode, final String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(final int errorCode, final Throwable throwable) {
		this.errorCode = errorCode;
	}

	public BusinessException(final Throwable throwable) {
		super(throwable);
	}

	public Long getExeptionTypeId() {
		return exeptionTypeId;
	}

	public void setExeptionTypeId(final Long exeptionTypeId) {
		this.exeptionTypeId = exeptionTypeId;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(final Long objId) {
		this.objId = objId;
	}
}
