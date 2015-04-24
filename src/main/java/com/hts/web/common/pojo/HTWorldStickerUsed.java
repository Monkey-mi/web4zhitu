package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 贴纸使用记录POJO
 * </p>
 * 
 * 创建时间:2015-04-14
 * @author lynch
 *
 */
public class HTWorldStickerUsed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6311291348051781731L;

	private Integer id;
	private Integer userId;
	private Integer stickerId;
	private Long usedTime;

	public HTWorldStickerUsed(Integer userId, Integer stickerId,
			Long usedTime) {
		super();
		this.userId = userId;
		this.stickerId = stickerId;
		this.usedTime = usedTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStickerId() {
		return stickerId;
	}

	public void setStickerId(Integer stickerId) {
		this.stickerId = stickerId;
	}

	public Long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}

}
