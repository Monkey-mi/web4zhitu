package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 汇图子世界POJO
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
public class HTWorldChildWorld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8731608565913696279L;

	private Integer id; // 主键
	private String childWorldDesc; // 子世界描述
	private Integer worldId; // 所属世界节点id
	private String path; // 图片存放路径
	private Integer width; // 图片宽度
	private Integer height; // 图片高度
	private Integer isTitle; // 是否为封面

	private Integer coordinatex; // x坐标
	private Integer coordinatey; // y坐标
	private Integer atId; // 所在子世界节点id
	private String thumbPath; // 缩略图路径
	private Integer angle = -1; // 旋转角度
	private Integer type = 1; // 子世界类型,默认为圆圈
	private String typePath; // 缩略图类型路径

	private Map<Integer, HTWorldChildWorldThumb> thumbs = new HashMap<Integer, HTWorldChildWorldThumb>();
	
	public HTWorldChildWorld(Integer id, String childWorldDesc,
			Integer worldId, String path, Integer width, Integer height, Integer isTitle,
			Integer coordinatex, Integer coordinatey, Integer atId,
			String thumbPath, Integer angle, Integer type, String typePath) {
		super();
		this.id = id;
		this.childWorldDesc = childWorldDesc;
		this.worldId = worldId;
		this.path = path;
		this.width = width;
		this.height = height;
		this.isTitle = isTitle;
		this.coordinatex = coordinatex;
		this.coordinatey = coordinatey;
		this.atId = atId;
		this.thumbPath = thumbPath;
		this.angle = angle;
		this.type = type;
		this.typePath = typePath;
	}

	public HTWorldChildWorld() {
		super();
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

	public Map<Integer, HTWorldChildWorldThumb> getThumbs() {
		return thumbs;
	}

	public void setThumbs(Map<Integer, HTWorldChildWorldThumb> thumbs) {
		this.thumbs = thumbs;
	}

	/**
	 * 添加缩略图片
	 * 
	 * @param thumbnail
	 */
	public void addThumb(HTWorldChildWorldThumb thumb) {
		synchronized (thumbs) {
			this.thumbs.put(thumb.getToId(), thumb);
		}

	}

	/**
	 * 移除世界缩略图
	 * 
	 * @param thumbnail
	 */
	public void removeThumb(HTWorldChildWorldThumb thumb) {
		synchronized (thumbs) {
			this.thumbs.remove(thumb.getToId());
		}

	}

}
