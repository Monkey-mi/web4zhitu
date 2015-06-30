package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.List;

import com.hts.web.base.constant.Tag;

public class HTWorldStickerTop implements Serializable, StickerWithLock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 510725241630367713L;
	
	private Integer recommendId;
	private Integer id;
	private Integer typeId;
	private Integer setId;
	private String stickerPath;
	private String stickerThumbPath;
	private String stickerDemoPath;
	private String stickerName;
	private String stickerDesc;
	private String sharePath;
	private Integer hasLock;
	private Integer unlock = Tag.FALSE;
	private Integer labelId;
	private Integer fill = 0;
	
	private List<HTWorldStickerDto> sets;
	
	public HTWorldStickerTop(Integer recommendId, Integer id, Integer typeId,
			Integer setId, String stickerPath, String stickerThumbPath,
			String stickerDemoPath, String stickerName, String stickerDesc,
			String sharePath, Integer hasLock, Integer unlock, Integer labelId,
			Integer fill) {
		super();
		this.recommendId = recommendId;
		this.id = id;
		this.typeId = typeId;
		this.setId = setId;
		this.stickerPath = stickerPath;
		this.stickerThumbPath = stickerThumbPath;
		this.stickerDemoPath = stickerDemoPath;
		this.stickerName = stickerName;
		this.stickerDesc = stickerDesc;
		this.sharePath = sharePath;
		this.hasLock = hasLock;
		this.unlock = unlock;
		this.labelId = labelId;
		this.fill = fill;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public String getStickerPath() {
		return stickerPath;
	}

	public void setStickerPath(String stickerPath) {
		this.stickerPath = stickerPath;
	}

	public String getStickerThumbPath() {
		return stickerThumbPath;
	}

	public void setStickerThumbPath(String stickerThumbPath) {
		this.stickerThumbPath = stickerThumbPath;
	}

	public String getStickerDemoPath() {
		return stickerDemoPath;
	}

	public void setStickerDemoPath(String stickerDemoPath) {
		this.stickerDemoPath = stickerDemoPath;
	}

	public String getStickerName() {
		return stickerName;
	}

	public void setStickerName(String stickerName) {
		this.stickerName = stickerName;
	}

	public String getStickerDesc() {
		return stickerDesc;
	}

	public void setStickerDesc(String stickerDesc) {
		this.stickerDesc = stickerDesc;
	}

	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}

	public Integer getHasLock() {
		return hasLock;
	}

	public void setHasLock(Integer hasLock) {
		this.hasLock = hasLock;
	}

	public Integer getUnlock() {
		return unlock;
	}

	public void setUnlock(Integer unlock) {
		this.unlock = unlock;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getFill() {
		return fill;
	}

	public void setFill(Integer fill) {
		this.fill = fill;
	}

	public List<HTWorldStickerDto> getSets() {
		return sets;
	}

	public void setSets(List<HTWorldStickerDto> sets) {
		this.sets = sets;
	}

	@Override
	public Integer getStickerId() {
		return id;
	}
	
}
