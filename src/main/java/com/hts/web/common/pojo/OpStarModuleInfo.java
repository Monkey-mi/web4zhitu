package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 达人推荐页面中的达人模块信息
 * @author mishengliang
 *
 */
public class OpStarModuleInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322151356585831661L;
	
	

	public OpStarModuleInfo(Integer id, String title, String subtitle, Integer userId, String facePic, String userName,
			String pic, String pic02,String pic03,String pic04,String intro2, Integer topicId) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.userId = userId;
		this.facePic = facePic;
		this.userName = userName;
		this.pic = pic;
		this.pic02 = pic02;
		this.pic03 = pic03;
		this.pic04 = pic04;
		this.intro2 = intro2;
		this.topicId = topicId;
	}
	
	public OpStarModuleInfo() {
		super();
	}
	
	private Integer  id;   //ID
	private String  title;//小标题
	private String subtitle;//小副标题
	private Integer userId;//用户ID
	private String facePic;//头像链接
	private String  userName;//用户昵称
	private String  pic;//推荐主图
	private String pic02;
	private String pic03;
	private String pic04;
	private String  intro2;//用户模块介绍
	private Integer topicId;//主题ID
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getFacePic() {
		return facePic;
	}
	public void setFacePic(String facePic) {
		this.facePic = facePic;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPic02() {
		return pic02;
	}

	public void setPic02(String pic02) {
		this.pic02 = pic02;
	}

	public String getPic03() {
		return pic03;
	}

	public void setPic03(String pic03) {
		this.pic03 = pic03;
	}

	public String getPic04() {
		return pic04;
	}

	public void setPic04(String pic04) {
		this.pic04 = pic04;
	}

	public String getIntro2() {
		return intro2;
	}
	public void setIntro2(String intro2) {
		this.intro2 = intro2;
	}
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	@Override
	public String toString() {
		return "OpStarModuleInfo [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", userId=" + userId
				+ ", facePic=" + facePic + ", userName=" + userName + ", pic=" + pic + ", pic02=" + pic02 + ", pic03="
				+ pic03 + ", pic04=" + pic04 + ", intro2=" + intro2 + ", topicId=" + topicId + "]";
	}
	
	
}
