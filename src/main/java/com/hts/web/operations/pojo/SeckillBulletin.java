package com.hts.web.operations.pojo;

import com.hts.web.common.pojo.OpMsgBulletin;

/**
 * 秒杀商品公告
 * 
 * @author zhangbo	2015年12月7日
 *
 */
public class SeckillBulletin extends OpMsgBulletin {

	/**
	 * 序列号
	 * @author zhangbo	2015年12月7日
	 */
	private static final long serialVersionUID = 8823671312896267885L;
	
	/**
	 * 公告分类
	 * @author zhangbo	2015年12月7日
	 */
	private Integer category;
	
	/**
	 * 秒杀商品截止日期，存储的为截止日期的时间戳
	 * @author zhangbo	2015年12月7日
	 */
	private long deadline;
	
	/**
	 * 序号
	 * @author zhangbo	2015年12月12日
	 */
	private Integer serial;

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return the deadline
	 */
	public long getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the serial
	 */
	public Integer getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
