package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldStickerDto;

/**
 * <p>
 * 贴纸数据访问接口
 * </p>
 * 
 * 创建时间:2014-12-26
 * 
 * @author lynch
 *
 */
public interface HTWorldStickerDao extends BaseDao {

	/**
	 * 查询贴纸列表
	 * 
	 * @param typeId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldStickerDto> querySticker(Integer typeId, 
			RowSelection rowSelection);
	
	/**
	 * 查询贴纸列表
	 * 
	 * @param maxId
	 * @param typeId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldStickerDto> querySticker(Integer maxId, Integer typeId, 
			RowSelection rowSelection);
	
	/**
	 * 根据id查询贴纸
	 * 
	 * @param id
	 * @return
	 */
	public HTWorldStickerDto queryStickerById(Integer id);
	
	/**
	 * 根据labelId查询贴纸
	 * 
	 * @param labelId
	 * @return
	 */
	public HTWorldStickerDto queryStickerByLabelId(Integer labelId);
	
	/**
	 * 查询标签
	 * 
	 * @param labelIds
	 * @param callback
	 */
	public void queryStickerByLabelIds(Integer[] labelIds, 
			RowCallback<HTWorldStickerDto> callback);
	
}
