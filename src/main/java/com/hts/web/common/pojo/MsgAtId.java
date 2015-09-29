package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * AT消息atidPOJO
 * 
 * @version 3.0.5
 * @author lynch 2015-09-22
 *
 */
public class MsgAtId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1722135118765825211L;
	private Integer atId;
	private String atName;

	public Integer getAtId() {
		return atId;
	}

	public void setAtId(Integer atId) {
		this.atId = atId;
	}

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

}
