package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 达人推荐主题信息
 * @author mishengliang
 *
 */
public class OpStarRecommendTopicInfo implements Serializable{

	/**
	 *  
	 */
	private static final long serialVersionUID = -9213079401185374183L;
	
	
	public OpStarRecommendTopicInfo() {
		super();
	}
	
	private Integer topicId;             //主题ID
	 private String title;                     //主题标题 
	private String headIntro;          //分享介绍
	private String  fileName;           //主题资源所放文件名
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
