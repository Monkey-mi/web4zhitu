package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 私信POJO对象
 * </p>
 * 
 * 
 * @author ztj 2013-1-29 2015-10-28
 *
 */
public class UserMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2978348943668722430L;

	private Integer id;
	private String content;

	public UserMsg() {
		super();
	}

	public UserMsg(Integer id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
