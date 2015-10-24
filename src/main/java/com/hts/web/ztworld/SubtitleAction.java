package com.hts.web.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.ztworld.service.SubtitleService;

/**
 * <p>
 * 织图Action
 * </p>
 * 
 * 创建时间:2015-03-14
 * @author lynch
 *
 */
public class SubtitleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1462610933829579975L;

	@Autowired
	private SubtitleService subtitleService;
	
	/**
	 * 查询字幕
	 * 
	 * @return
	 */
	public String querySubtitle() {
		try {
			subtitleService.buildSubtitle(maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	

}
