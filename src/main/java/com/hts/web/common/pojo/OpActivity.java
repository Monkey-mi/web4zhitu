package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 广场活动POJO
 * </p>
 * 
 * 创建时间：2013-11-8
 * 
 * @author ztj
 * 
 */
public class OpActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889053555278132844L;
	private Integer id;
	private String titlePath;
	private String titleThumbPath;
	private String channelPath;
	private String activityName;
	private String activityTitle;
	private String activityDesc;
	private String activityLink;
	private String activityLogo;
	private Date activityDate;
	private Date deadline;
	private Integer objType;
	private Integer objId;
	private Integer commercial;
	private String shareTitle;
	private String shareDesc;
	private Integer valid;
	private Integer serial;

	private Integer worldCount = 0;
	private Integer winnerId = 0;

	private List<OpActivitySponsor> sponsors = new ArrayList<OpActivitySponsor>();
	private List<OpActivityAward> awards = new ArrayList<OpActivityAward>();
	private List<HTWorldLabelWorldAuthor> authors = new ArrayList<HTWorldLabelWorldAuthor>();
	
	private HTWorldStickerDto sticker;

	public OpActivity() {
		super();
	}

	public OpActivity(Integer id, String titlePath, String titleThumbPath,
			String channelPath, String activityName, String activityTitle,
			String activityDesc, String activityLink, String activityLogo,
			Date activityDate, Date deadline, Integer objType, Integer objId,
			Integer commercial, String shareTitle, String shareDesc,
			Integer valid, Integer serial) {
		this.id = id;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.channelPath = channelPath;
		this.activityName = activityName;
		this.activityTitle = activityTitle;
		this.activityDesc = activityDesc;
		this.activityLink = activityLink;
		this.activityLogo = activityLogo;
		this.activityDate = activityDate;
		this.deadline = deadline;
		this.objType = objType;
		this.objId = objId;
		this.commercial = commercial;
		this.shareTitle = shareTitle;
		this.shareDesc = shareDesc;
		this.valid = valid;
		this.serial = serial;
	}

	public OpActivity(Integer id, String titlePath, String titleThumbPath,
			String channelPath, String activityName, String activityTitle,
			String activityDesc, String activityLink, String activityLogo,
			Date activityDate, Date deadline, Integer objType, Integer objId,
			Integer commercial, String shareTitle, String shareDesc,
			Integer valid) {
		this.id = id;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.channelPath = channelPath;
		this.activityName = activityName;
		this.activityTitle = activityTitle;
		this.activityDesc = activityDesc;
		this.activityLink = activityLink;
		this.activityLogo = activityLogo;
		this.activityDate = activityDate;
		this.deadline = deadline;
		this.objType = objType;
		this.objId = objId;
		this.commercial = commercial;
		this.shareTitle = shareTitle;
		this.shareDesc = shareDesc;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}

	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}

	public String getActivityLogo() {
		return activityLogo;
	}

	public void setActivityLogo(String activityLogo) {
		this.activityLogo = activityLogo;
	}

	public List<OpActivitySponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<OpActivitySponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public List<OpActivityAward> getAwards() {
		return awards;
	}

	public void setAwards(List<OpActivityAward> awards) {
		this.awards = awards;
	}

	public Integer getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
	}

	public List<HTWorldLabelWorldAuthor> getAuthors() {
		return authors;
	}

	public void setAuthors(List<HTWorldLabelWorldAuthor> authors) {
		this.authors = authors;
	}

	public String getChannelPath() {
		return channelPath;
	}

	public void setChannelPath(String channelPath) {
		this.channelPath = channelPath;
	}

	public HTWorldStickerDto getSticker() {
		return sticker;
	}

	public void setSticker(HTWorldStickerDto sticker) {
		this.sticker = sticker;
	}
	
}
