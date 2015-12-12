package com.hts.web.operations.pojo;

import com.hts.web.common.pojo.OpMsgBulletin;

/**
 * 推荐商品公告
 * 
 * @author zhangbo	2015年12月7日
 *
 */
public class RecommendItemBulletin extends OpMsgBulletin {

	/**
	 * 序列号
	 * @author zhangbo	2015年12月7日
	 */
	private static final long serialVersionUID = 3773384112040246484L;
	
	/**
	 * 公告分类
	 * @author zhangbo	2015年12月7日
	 */
	private Integer category;
	
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
