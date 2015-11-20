package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * @author mishengliang
 * 11-16-2015
 */
public class OpActivitySharePageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160601005276465882L;
	
	private OpActivity opActivity;
	private List<HTWorld> htWorlds;
	private Integer  remianDay;
	private String labelName;
	private Integer activityCount;
	private List<OpActivityStar> opActivityStars;

	public OpActivitySharePageInfo() {
		super();
	}

	public OpActivity getOpActivity() {
		return opActivity;
	}

	public void setOpActivity(OpActivity opActivity) {
		this.opActivity = opActivity;
	}

	public List<HTWorld> getHtWorlds() {
		return htWorlds;
	}

	public void setHtWorlds(List<HTWorld> htWorlds) {
		this.htWorlds = htWorlds;
	}

	public Integer getRemianDay() {
		return remianDay;
	}

	public void setRemianDay(Integer remianDay) {
		this.remianDay = remianDay;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(Integer activityCount) {
		this.activityCount = activityCount;
	}

	public List<OpActivityStar> getOpActivityStars() {
		return opActivityStars;
	}

	public void setOpActivityStars(List<OpActivityStar> opActivityStars) {
		this.opActivityStars = opActivityStars;
	}

	
}
