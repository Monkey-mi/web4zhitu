package com.hts.web.base;

/**
 * <p>
 * 汇图说自定义异常
 * </p>
 * @author ztj
 *
 */
public class HTSException extends Exception {

	
	private int errorCode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6608825747512996755L;
	
	public HTSException(HTSErrorCode errorCode) {
		super(errorCode.toString());
		this.errorCode = errorCode.getErrCode();
	}
	
	public HTSException(HTSErrorCode errorCode, String customMsg) {
		super(customMsg);
		this.errorCode = errorCode.getErrCode();
	}
	
	public HTSException(HTSErrorCode errorCode, Throwable e) {
		super(e);
		this.errorCode = errorCode.getErrCode();
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	
}
