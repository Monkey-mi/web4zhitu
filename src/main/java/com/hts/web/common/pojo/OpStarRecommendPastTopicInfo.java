package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

/**
 * 达人推荐主题信息
 * @author mishengliang
 *
 */
public class OpStarRecommendPastTopicInfo implements Serializable{

	/**
	 *  
	 */
	private static final long serialVersionUID = -9213079401185374183L;
	
	
	public OpStarRecommendPastTopicInfo() {
		super();
	}
	
	private Integer topicId;             //主题ID
	private Integer topicType;        //文章类型
	 private String title;                     //主题标题 
	private String headIntro;          //分享介绍
	private String  bannerPic;           //第一张展示的图banner
	private List<OpStarModuleInfo> starModuleInfos;//达人模块信息
	private String footIntro;          //结尾总结
	private String stickerButton;  //发图按钮文本
	private String shareButton;   //分享按钮文本
	private String foot;                    //来自***织图
	
	
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHeadIntro() {
		return headIntro;
	}
	public void setHeadIntro(String headIntro) {
		this.headIntro = headIntro;
	}
	public String getBannerPic() {
		return bannerPic;
	}
	public void setBannerPic(String bannerPic) {
		this.bannerPic = bannerPic;
	}
	public List<OpStarModuleInfo> getStarModuleInfos() {
		return starModuleInfos;
	}
	public void setStarModuleInfos(List<OpStarModuleInfo> starModuleInfos) {
		this.starModuleInfos = starModuleInfos;
	}
	public String getFootIntro() {
		return footIntro;
	}
	public void setFootIntro(String footIntro) {
		this.footIntro = footIntro;
	}
	
	public String getStickerButton() {
		return stickerButton;
	}

	public void setStickerButton(String stickerButton) {
		this.stickerButton = stickerButton;
	}

	public String getShareButton() {
		return shareButton;
	}

	public void setShareButton(String shareButton) {
		this.shareButton = shareButton;
	}

	public String getFoot() {
		return foot;
	}
	public void setFoot(String foot) {
		this.foot = foot;
	}
	
}
