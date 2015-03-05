package com.hts.web.common.pojo;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 频道TopOne红人POJO
 * </p>
 * 
 * 创建时间:2014-10-31
 * @author lynch
 *
 */
public class OpChannelTopOne extends UserWorldBase implements ObjectWithIsMututal{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7753314633399298491L;
	private Integer topOneId;
	private Integer topId;
	private String topDesc;
	private Integer isMututal = Tag.UN_CONCERN;

	public Integer getTopId() {
		return topId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public String getTopDesc() {
		return topDesc;
	}

	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
	}
	
	public Integer getIsMututal() {
		return isMututal;
	}

	@Override
	public Integer getUserId() {
		return this.id;
	}

	@Override
	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	public Integer getTopOneId() {
		return topOneId;
	}

	public void setTopOneId(Integer topOneId) {
		this.topOneId = topOneId;
	}
	

}
