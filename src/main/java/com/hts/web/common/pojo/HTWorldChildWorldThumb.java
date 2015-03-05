package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 子世界缩略图POJO
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
public class HTWorldChildWorldThumb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -590501499049112743L;

	private Integer toId;
	private Integer coordinatex; // x坐标
	private Integer coordinatey; // y坐标
	private Integer atId; // 所在子世界节点id
	private String thumbPath; // 缩略图路径
	private Integer angle = -1; // 旋转角度
	private Integer type = 1; // 子世界类型
	private String typePath; // 缩略图类型路径

	public HTWorldChildWorldThumb() {
		super();
	}
	
	public HTWorldChildWorldThumb(Integer toId, Integer coordinatex,
			Integer coordinatey, Integer atId, String thumbPath, Integer angle, Integer type, String typePath) {
		super();
		this.toId = toId;
		this.coordinatex = coordinatex;
		this.coordinatey = coordinatey;
		this.atId = atId;
		this.thumbPath = thumbPath;
		this.angle = angle;
		this.type = type;
		this.typePath = typePath;
	}
	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public Integer getCoordinatex() {
		return coordinatex;
	}

	public void setCoordinatex(Integer coordinatex) {
		this.coordinatex = coordinatex;
	}

	public Integer getCoordinatey() {
		return coordinatey;
	}

	public void setCoordinatey(Integer coordinatey) {
		this.coordinatey = coordinatey;
	}

	public Integer getAtId() {
		return atId;
	}

	public void setAtId(Integer atId) {
		this.atId = atId;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
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
