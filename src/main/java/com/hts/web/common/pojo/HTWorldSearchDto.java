package com.hts.web.common.pojo;

/**
 * <p>
 * 世界传输对象
 * </p>
 * 
 * 创建时间：2013-04-09
 * 
 * @author yql
 * 
 */
public class HTWorldSearchDto {
	private Integer id;
	private String titleThumbPath; // 首页缩略图路径
	private double latitude;// 纬度
	private double longitude;// 经度

	public HTWorldSearchDto(Integer id, String titleThumbPath, double latitude,
			double longitude) {
		super();
		this.id = id;
		this.titleThumbPath = titleThumbPath;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
