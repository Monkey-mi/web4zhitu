package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道计数POJO
 * </p>
 * 
 * 创建时间: 2015-05-05
 * @author lynch
 *
 */
public class OpChannelCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8161800547282901391L;
	private Integer id;
	private Integer worldCount;
	private Integer childCount;
	private Integer memberCount;
	private Integer superbCount;
	
	public OpChannelCount() {
		super();
	}

	public OpChannelCount(Integer id, Integer worldCount, Integer childCount,
			Integer memberCount, Integer superbCount) {
		super();
		this.id = id;
		this.worldCount = worldCount;
		this.childCount = childCount;
		this.memberCount = memberCount;
		this.superbCount = superbCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public Integer getSuperbCount() {
		return superbCount;
	}

	public void setSuperbCount(Integer superbCount) {
		this.superbCount = superbCount;
	}

}
