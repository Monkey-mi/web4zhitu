package com.hts.web.trade.item;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.trade.item.service.ItemShowService;

public class ItemShowAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6318105357493415624L;
	
	private Integer itemSetId; 
	
	@Autowired
	private ItemShowService itemShowService;

	public String queryItemShow(){
		try {
			itemShowService.queryItemShowInfo(itemSetId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public Integer getItemSetId() {
		return itemSetId;
	}

	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	} 
	
	
}
