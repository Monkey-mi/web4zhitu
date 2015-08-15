package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 频道详情POJO
 * </p>
 * 
 * 创建时间: 2015-05-05
 * 
 * @author lynch
 *
 */
public class OpChannelDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8714941941476670606L;
	private Integer id;
	private Integer ownerId;
	private String channelName;
	private String channelTitle;
	private String subtitle;
	private String channelDesc;
	private String channelIcon;
	private String subIcon;
	private String banner;
	private Integer channelType;
	private String channelLabel;
	private String labelIds;
	private Integer worldCount;
	private Integer childCount;
	private Integer memberCount;
	private Integer superbCount;
	private Date createTime;
	private Date lastModified;
	private Integer superb;
	private Integer themeId;
	private Integer danmu;
	private Integer mood;
	private Integer world;
	private String review;

	private UserInfoDto owner;

	private List<OpChannelMemberThumb> members;
	
	private Integer subscribed = Tag.FALSE;
	private Integer role = -1;

	public OpChannelDetail() {
		super();
	}

	public OpChannelDetail(Integer id, Integer ownerId, String channelName,
			String channelTitle, String subtitle, String channelDesc,
			String channelIcon, String subIcon, String banner, Integer channelType,
			String channelLabel, String labelIds, Integer worldCount,
			Integer childCount, Integer memberCount, Integer superbCount,
			Date createTime, Date lastModified, Integer superb, Integer themeId,
			Integer danmu, Integer mood, Integer world, String review) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.channelName = channelName;
		this.channelTitle = channelTitle;
		this.subtitle = subtitle;
		this.channelDesc = channelDesc;
		this.channelIcon = channelIcon;
		this.subIcon = subIcon;
		this.banner = banner;
		this.channelType = channelType;
		this.channelLabel = channelLabel;
		this.labelIds = labelIds;
		this.worldCount = worldCount;
		this.childCount = childCount;
		this.memberCount = memberCount;
		this.superbCount = superbCount;
		this.createTime = createTime;
		this.lastModified = lastModified;
		this.superb = superb;
		this.themeId = themeId;
		this.danmu = danmu;
		this.mood = mood;
		this.world = world;
		this.review = review;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public String getChannelIcon() {
		return channelIcon;
	}

	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}

	public String getSubIcon() {
		return subIcon;
	}

	public void setSubIcon(String subIcon) {
		this.subIcon = subIcon;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public Integer getSuperbCount() {
		return superbCount;
	}

	public void setSuperbCount(Integer superbCount) {
		this.superbCount = superbCount;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public UserInfoDto getOwner() {
		return owner;
	}

	public void setOwner(UserInfoDto owner) {
		this.owner = owner;
	}

	public List<OpChannelMemberThumb> getMembers() {
		return members;
	}

	public void setMembers(List<OpChannelMemberThumb> members) {
		this.members = members;
	}

	public Integer getDanmu() {
		return danmu;
	}

	public void setDanmu(Integer danmu) {
		this.danmu = danmu;
	}

	public Integer getMood() {
		return mood;
	}

	public void setMood(Integer mood) {
		this.mood = mood;
	}

	public Integer getWorld() {
		return world;
	}

	public void setWorld(Integer world) {
		this.world = world;
	}

	public String getChannelLabel() {
		return channelLabel;
	}

	public void setChannelLabel(String channelLabel) {
		this.channelLabel = channelLabel;
	}

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Integer subscribed) {
		this.subscribed = subscribed;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
}
