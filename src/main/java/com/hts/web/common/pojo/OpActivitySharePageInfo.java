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

	
}
