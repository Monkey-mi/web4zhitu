package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 返回结果抽象<br />
 * 
 * @author ztj
 *
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036772766065724482L;
	private Integer result;
	private String msg;
	private Object resultSet;

	public Result() {
		super();
	}

	public Result(Integer result, String msg, Object resultSet) {
		super();
		this.result = result;
		this.msg = msg;
		this.resultSet = resultSet;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResultSet() {
		return resultSet;
	}

	public void setResultSet(Object resultSet) {
		this.resultSet = resultSet;
	}

}
