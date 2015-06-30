package com.hts.web.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.ztworld.service.ZTWorldStickerService;

/**
 * <p>
 * 织图贴纸子模块控制器
 * </p>
 * 
 * 创建时间：2014-12-26
 * @author lynch
 *
 */
public class ZTWorldStickerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3463998574426220992L;
	
	@Autowired
	private ZTWorldStickerService stickerService;
	
	private Integer stickerId;
	
	private Integer typeId;
	
	private Integer id;
	
	/**
	 * 查询置顶贴纸
	 * 
	 * @return
	 */
	public String queryTopSticker() {
		try {
			stickerService.buildTopSticker(getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询推荐贴纸
	 * 
	 * @return
	 */
	public String queryRecommendSticker() {
		try {
			stickerService.buildRecommendSticker(getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询贴纸列表
	 * 
	 * @return
	 */
	public String querySticker() {
		try {
			stickerService.buildSticker(getCurrentLoginUserId(), typeId, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
			
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id获取贴纸
	 * @return
	 */
	public String queryStickerById() {
		try {
			HTWorldStickerDto dto = stickerService.getStickerDtoById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
			
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最大贴纸id
	 * 
	 * @return
	 */
	public String queryMaxStickerId() {
		try {
			stickerService.buildMaxStickerId(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解锁贴纸
	 * 
	 * @return
	 */
	public String unlock() {
		try {
			stickerService.unlock(getCurrentLoginUserId(), stickerId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 使用贴纸
	 * 
	 * @return
	 */
	public String used() {
		try {
			stickerService.saveStickerUsed(getCurrentLoginUserId(), stickerId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 贴纸介绍页面
	 * 
	 * @return
	 */
	public String stickerIntro() {
		return SUCCESS;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getStickerId() {
		return stickerId;
	}

	public void setStickerId(Integer stickerId) {
		this.stickerId = stickerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
