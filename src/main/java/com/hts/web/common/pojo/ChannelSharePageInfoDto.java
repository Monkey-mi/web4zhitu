package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 频道分享页面信息
 * 频道信息
 * 精选织图信息
 * 11-07-2015
 * @author mishengliang
 *
 */
public class ChannelSharePageInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1703834474706727159L;


	private OpChannelDetail opChannelDetail;
	private List<OpChannelWorldDto> opChannelWorldDtos;
	private  List<OpChannelStar> starList;
	
	public OpChannelDetail getOpChannelDetail() {
		return opChannelDetail;
	}
	public void setOpChannelDetail(OpChannelDetail opChannelDetail) {
		this.opChannelDetail = opChannelDetail;
	}
	public List<OpChannelWorldDto> getOpChannelWorldDto() {
		return opChannelWorldDtos;
	}
	public void setOpChannelWorldDto(List<OpChannelWorldDto> opChannelWorldDtos) {
		this.opChannelWorldDtos = opChannelWorldDtos;
	}
	public List<OpChannelStar> getStarList() {
		return starList;
	}
	public void setStarList(List<OpChannelStar> starList) {
		this.starList = starList;
	}
	
}
