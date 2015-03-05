package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 广场话题POJO
 * </p>
 * 
 * 创建时间：2013-9-9
 * 
 * @author ztj
 * 
 */
public class OpSquareTopic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7622432884677605751L;
	private Integer id;
	private String topic;
	private String topicPath;
	private String topicPathHd;
	private Date dateAdded;

	public OpSquareTopic() {
		super();
	}
	
	public OpSquareTopic(Integer id, String topic,
			String topicPath, String topicPathHd, Date dateAdded) {
		this.id = id;
		this.topic = topic;
		this.topicPath = topicPath;
		this.topicPathHd = topicPathHd;
		this.dateAdded = dateAdded;
	}
	
	public OpSquareTopic(String topic, String topicPath,
			String topicPathHd, Date dateAdded) {
		this.topic = topic;
		this.topicPath = topicPath;
		this.topicPathHd = topicPathHd;
		this.dateAdded = dateAdded;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopicPath() {
		return topicPath;
	}

	public void setTopicPath(String topicPath) {
		this.topicPath = topicPath;
	}

	public String getTopicPathHd() {
		return topicPathHd;
	}

	public void setTopicPathHd(String topicPathHd) {
		this.topicPathHd = topicPathHd;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
