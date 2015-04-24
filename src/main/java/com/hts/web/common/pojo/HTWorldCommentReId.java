package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 评论id POJO
 * </p>
 * 
 * 创建时间: 2015-04-24
 * @author lynch
 *
 */
public class HTWorldCommentReId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3224830356870325933L;

	private Integer id;
	private Integer reId;

	public HTWorldCommentReId(Integer id, Integer reId) {
		super();
		this.id = id;
		this.reId = reId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

}
