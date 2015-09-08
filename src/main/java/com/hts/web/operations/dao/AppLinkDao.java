package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpAdAppLink;
import com.hts.web.common.pojo.OpAdAppLinkDto;

/**
 * <p>
 * APP链接数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-30
 * @author ztj
 *
 */
public interface AppLinkDao extends BaseDao {

	/**
	 * 查询App链接列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpAdAppLinkDto> queryAppLinkDto(Integer open, Integer phoneCode, RowSelection rowSelection);
	
	/**
	 * 查询App链接列表
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<OpAdAppLinkDto> queryAppLinkDto(Integer maxSerial, Integer open, Integer phoneCode, RowSelection rowSelection);
	
	/**
	 * 添加点击次数
	 * 
	 * @param id
	 */
	public void addClickCount(Integer id);
	
	/**
	 * 根据短链查询链接
	 * 
	 * @param shortLink
	 * @return
	 */
	public OpAdAppLink queryIdByShortLink(String shortLink);
}
