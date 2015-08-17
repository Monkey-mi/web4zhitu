package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 系统公告
 * </p>
 * 
 * 创建时间: 2015-06-11
 * @author lynch
 *
 */
public class OpMsgBulletin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344569366649332761L;
	private Integer id;
	private String bulletinPath;
	private String bulletinThumb;
	private String bulletinName;
	private Integer bulletinType;
	private String link;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBulletinPath() {
		return bulletinPath;
	}

	public void setBulletinPath(String bulletinPath) {
		this.bulletinPath = bulletinPath;
	}

	public Integer getBulletinType() {
		return bulletinType;
	}

	public void setBulletinType(Integer bulletinType) {
		this.bulletinType = bulletinType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getBulletinThumb() {
		return bulletinThumb;
	}

	public void setBulletinThumb(String bulletinThumb) {
		this.bulletinThumb = bulletinThumb;
	}

	public String getBulletinName() {
		return bulletinName;
	}

	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}

}
