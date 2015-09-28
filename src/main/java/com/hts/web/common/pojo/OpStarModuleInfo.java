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
	
	

	public OpStarModuleInfo(Integer id, String title1, String title2, Integer userId, String facePic, String userName,
			String pic, String intro2, Integer topicId) {
		super();
		this.id = id;
		this.title1 = title1;
		this.title2 = title2;
		this.userId = userId;
		this.facePic = facePic;
		this.userName = userName;
		this.pic = pic;
		this.intro2 = intro2;
		this.topicId = topicId;
	}
	
	public OpStarModuleInfo() {
		super();
	}
	
	private Integer  id;   //ID
	private String  title1;//小标题
	private String title2;//小副标题
	private Integer userId;//用户ID
	private String facePic;//头像链接
	private String  userName;//用户昵称
	private String  pic;//推荐主图
	private String  intro2;//用户模块介绍
	private Integer topicId;//主题ID
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
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
		return "OpStarModuleInfo [id=" + id + ", title1=" + title1 + ", title2=" + title2 + ", userId=" + userId
				+ ", facePic=" + facePic + ", userName=" + userName + ", pic=" + pic + ", intro2=" + intro2
				+ ", topicId=" + topicId + "]";
	}
	
	
	
}
