package com.hts.web.operations.pojo;

import com.hts.web.common.pojo.OpMsgBulletin;

/**
 * 有奖活动公告
 * 
 * @author zhangbo	2015年12月7日
 *
 */
public class AwardActivityBulletin extends OpMsgBulletin {
	
	/**
	 * 序列号
	 * @author zhangbo	2015年12月7日
	 */
	private static final long serialVersionUID = -1651205580754416730L;
	
	/**
	 * 公告分类
	 * @author zhangbo	2015年12月7日
	 */
	private Integer category;

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
	
}
