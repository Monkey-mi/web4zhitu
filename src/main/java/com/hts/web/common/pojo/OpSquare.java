package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 广场织图POJO
 * </p>
 * 
 * 创建时间：2012-12-13
 * 
 * @author ztj
 * 
 */
public class OpSquare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3416484952307922683L;

	private Integer id;
	private Integer targetId; // 推荐id
	private Integer squareLabel; // 广场推送标签
	private String recommender; // 推荐人
	private Date recommendDate; // 推荐日期
	private String recommendDesc; // 推荐描述
	private Integer shield = Tag.FALSE; // 屏蔽标志

	public OpSquare() {
		super();
	}

	public OpSquare(Integer id, Integer targetId,
			String recommender, Date recommendDate, String recommendDesc) {
		super();
		this.id = id;
		this.targetId = targetId;
		this.recommender = recommender;
		this.recommendDate = recommendDate;
		this.recommendDesc = recommendDesc;
	}

	public OpSquare(Integer targetId, String recommender,
			Date recommendDate, String recommendDesc) {
		super();
		this.targetId = targetId;
		this.recommender = recommender;
		this.recommendDate = recommendDate;
		this.recommendDesc = recommendDesc;
	}
	
	public OpSquare(Integer id, Integer targetId,
			Integer squareLabel, String recommender, Date recommendDate, String recommendDesc) {
		this.id = id;
		this.targetId = targetId;
		this.squareLabel = squareLabel;
		this.recommender = recommender;
		this.recommendDate = recommendDate;
		this.recommendDesc = recommendDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getRecommendDate() {
		return recommendDate;
	}

	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}
	
	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getSquareLabel() {
		return squareLabel;
	}

	public void setSquareLabel(Integer squareLabel) {
		this.squareLabel = squareLabel;
	}
	
	

}
