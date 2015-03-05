package com.hts.web.common.pojo;

import java.io.Serializable;

public class HTWorldChildWorldDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6298516922596180478L;
	private Integer id; // 主键
	private String childWorldDesc; // 子世界描述
	private Integer worldId; // 所属世界节点id
	private String path; // 图片存放路径
	private Integer width; // 图片宽度
	private Integer height; // 图片高度
	private Integer isTitle; // 是否为封面
	private Integer angle; // 旋转角度
	private Integer type; // 子世界类型
	private String typePath; // 缩略图类型路径

	public HTWorldChildWorldDto() {
	}

	public HTWorldChildWorldDto(Integer id, String childWorldDesc,
			Integer worldId, String path, Integer width, Integer height,
			Integer isTitle, Integer angle, Integer type, String typePath) {
		super();
		this.id = id;
		this.childWorldDesc = childWorldDesc;
		this.worldId = worldId;
		this.path = path;
		this.width = width;
		this.height = height;
		this.isTitle = isTitle;
		this.angle = angle;
		this.type = type;
		this.typePath = typePath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChildWorldDesc() {
		return childWorldDesc;
	}

	public void setChildWorldDesc(String childWorldDesc) {
		this.childWorldDesc = childWorldDesc;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(Integer isTitle) {
		this.isTitle = isTitle;
	}

	public Integer getAngle() {
		return angle;
	}

	public void setAngle(Integer angle) {
		this.angle = angle;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypePath() {
		return typePath;
	}

	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}
	

}
