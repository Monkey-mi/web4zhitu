package com.hts.web.trade.item;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.trade.item.service.ItemService;

/**
 * 商品控制类
 * 注：用于查询商品
 * 
 * @author zhangbo	2015年12月10日
 *
 */
@SuppressWarnings("serial")
public class ItemAction extends BaseAction {
	
	/**
	 * 商品主键id
	 * @author zhangbo	2015年12月10日
	 */
	private Integer itemId;
	
	/**
	 * 商品所在集合id
	 * @author zhangbo	2015年12月10日
	 */
	private Integer itemSetId;
	
	/**
	 * 用户id
	 * @author zhangbo	2015年12月10日
	 */
	private Integer uid;
	
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 查询商品相关信息，包括商品所在集合信息，与商品列表信息
	 * 
	 * @return	商品列表
	 * @author zhangbo	2015年12月10日
	 */
	public String queryItemInfo() {
		try {
			itemService.queryItemInfo(itemSetId, uid, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 为商品点赞
	 * 
	 * @return
	 * @author zhangbo	2015年12月10日
	 */
	public String likeItem() {
		try {
			itemService.likeItem(itemId, uid);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询商品集合最大id
	 * 
	 * @return
	 * @author zhangbo	2015年12月11日
	 */
	public String queryItemSetMaxId() {
		try {
			Integer maxId = itemService.getItemSetMaxId();
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getItemSetId() {
		return itemSetId;
	}

	public Integer getUid() {
		return uid;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	
	
}
