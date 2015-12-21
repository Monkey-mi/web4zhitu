package com.hts.web.trade.item.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.ItemShow;

public interface ItemShowDao extends BaseDao {
	/**
	 * 查询买家秀信息
	 * @return
	 * @throws Exception 
	 *	2015年12月21日
	 *	mishengliang
	 */
	public List<ItemShow> queryItemShow(Integer itemSetId) throws Exception;

}
