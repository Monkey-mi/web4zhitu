package com.hts.web.trade.item.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.HTWorldChannelName;
import com.hts.web.common.pojo.HTWorldCommentInline;
import com.hts.web.common.pojo.HTWorldLikedInline;
import com.hts.web.common.pojo.HTWorldWithExtra;

public class ItemShowDTO   implements HTWorldWithExtra{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7485783919462735550L;
	/**
	 * 评论
	 */
	private List<HTWorldCommentInline> comments = new ArrayList<HTWorldCommentInline>();
	/**
	 * 频道名称列表
	 */
	private List<HTWorldChannelName> channelNames; 
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 织图ID
	 */
	private Integer worldId;
	/**
	 * 商品集合ID
	 */
	private Integer itemSetId;
	/**
	 * 排序号
	 */
	private Integer serial;
	/**
	 * 首页缩略图路径
	 */
	private String title_thumb_path;
	/**
	 * 织图描述
	 */
	private String worldDes;
	/**
	 * 地址描述
	 */
	private String addr;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户头像
	 */
	private String userAvatar;
	/**
	 * 编辑日期
	 */
	private Date dateModified;
	/**
	 * 图片数量
	 */
	private Integer  childCount;
	/**
	 * 点击数
	 */
	private Integer clickCount;
	/**
	 * 点赞数
	 */
	private Integer likeCount;
	/**
	 * 短链
	 */
	private String shortLink;
	/**
	 * 明星标识
	 */
	private String verifyIcon;
	/**
	 * 世界标签
	 */
	private String worldLabel; 
	/**
	 * 被评论次数
	 */
	private Integer commentCount;
	
	
	
	public List<HTWorldCommentInline> getComments() {
		return comments;
	}
	public void setComments(List<HTWorldCommentInline> comments) {
		this.comments = comments;
	}
	public List<HTWorldChannelName> getChannelNames() {
		return channelNames;
	}
	public void setChannelNames(List<HTWorldChannelName> channelNames) {
		this.channelNames = channelNames;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getItemSetId() {
		return itemSetId;
	}
	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public String getTitle_thumb_path() {
		return title_thumb_path;
	}
	public void setTitle_thumb_path(String title_thumb_path) {
		this.title_thumb_path = title_thumb_path;
	}
	public String getWorldDes() {
		return worldDes;
	}
	public void setWorldDes(String worldDes) {
		this.worldDes = worldDes;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public String getVerifyIcon() {
		return verifyIcon;
	}
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}
	public String getWorldLabel() {
		return worldLabel;
	}
	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	@Override
	public List<HTWorldLikedInline> getLikes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLiked(Object liked) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setKeep(Object keep) {
		// TODO Auto-generated method stub
		
	}
	
}
