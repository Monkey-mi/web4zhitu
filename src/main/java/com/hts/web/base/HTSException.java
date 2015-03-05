package com.hts.web.base;

import com.hts.web.base.constant.OptResult;

/**
 * <p>
 * 汇图说自定义异常
 * </p>
 * @author ztj
 *
 */
public class HTSException extends Exception {
	
	private Integer errorCode = OptResult.OPT_FAILED;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6608825747512996755L;

	public HTSException() {
		super();
	}
	
	public HTSException(String message, Throwable cause) {
		super(message, cause);
	}

	public HTSException(String message) {
		super(message);
	}

	public HTSException(Throwable cause) {
		super(cause);
	}

	public HTSException(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	
	

}
