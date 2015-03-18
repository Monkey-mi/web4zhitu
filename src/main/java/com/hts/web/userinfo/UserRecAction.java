package com.hts.web.userinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.userinfo.service.UserRecService;

public class UserRecAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6483611053776487825L;
	
	@Autowired
	private UserRecService recService;
	
	private String platConcerns;
	
	/**
	 * 保存社交平台关注的用户账号
	 * 
	 * @return
	 */
	public String savePlatConcerns() {
		try {
			recService.savePlatConcerns(userId, platConcerns);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询动态推荐用户
	 * 
	 * @return
	 */
	public String queryDynamicRec() {
		try {
			recService.buildRecUser(getCurrentLoginUserId(), jsonMap);	
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询建议关注用户
	 * 
	 * @return
	 */
	public String querySuggest() {
		try {
			recService.buildSuggestUser(userId, jsonMap);	
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String getPlatConcerns() {
		return platConcerns;
	}

	public void setPlatConcerns(String platConcerns) {
		this.platConcerns = platConcerns;
	}
	
}
