package com.hts.web.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.ztworld.service.ZTWorldLabelService;

/**
 * <p>
 * 织图标签Action，用户操作标签及其关联的织图
 * </p>
 * z
 * 创建时间：2014-5-5
 * @author tianjie
 *
 */
public class ZTWorldLabelAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1621054694053282867L;
	
	private Integer labelId;
	private String labelName;
	private Boolean trimValid = false;
	
	@Autowired
	private ZTWorldLabelService worldLabelService;
	
	/**
	 * 保存标签
	 * 
	 * @return
	 */
	public String saveLabel() {
		try {
			HTWorldLabel label = worldLabelService.saveLabel(labelName);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, label, 
					OptResult.JSON_KEY_LABEL_INFO, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 模糊搜索标签
	 * 
	 * @return
	 */
	public String fuzzySearch() {
		try {
			worldLabelService.buildFuzzyLabel(labelName, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询热门标签
	 * 
	 * @return
	 */
	public String queryHotLabel() {
		try {
			worldLabelService.buildHotLabel(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询活动标签
	 * 
	 * @return
	 */
	public String queryActivityLabel() {
		try {
			worldLabelService.buildActivityLabel(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据名字查询标签信息
	 * 
	 * @return
	 */
	public String queryLabelByName() {
		try {
			worldLabelService.buildLabel(labelName, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (HTSException e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询标签织图列表
	 * 
	 * @return
	 */
	public String queryLabelWorld() {
		try {
			worldLabelService.buildLabelWorld(false, labelName, trimValid, 
					getCurrentLoginUserId(), maxId, start, limit, jsonMap, 
					trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (HTSException e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询标签织图，根据interactId字段排序
	 * 
	 * @return
	 */
	public String queryLabelWorldV2() {
		try {
			worldLabelService.buildLabelWorld(true, labelName, trimValid, 
					getCurrentLoginUserId(), maxId, start, limit, jsonMap, 
					trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (HTSException e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}


	/**
	 * 查询标签织图作者
	 * 
	 * @return
	 */
	public String queryLabelWorldAuthor() {
		try {
			worldLabelService.buildLabelWorldAuthor(labelId, getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ZTWorldLabelService getWorldLabelService() {
		return worldLabelService;
	}

	public void setWorldLabelService(ZTWorldLabelService worldLabelService) {
		this.worldLabelService = worldLabelService;
	}
	
	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Boolean getTrimValid() {
		return trimValid;
	}

	public void setTrimValid(Boolean trimValid) {
		this.trimValid = trimValid;
	}
	
}
