package com.hts.web.operations;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.MsgOperationsService;

public class MsgOperationsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2471678336959606578L;

	private Integer phoneCode = Tag.IOS;

	@Autowired
	private MsgOperationsService msgOperationsService;

	/**
	 * 查询公告
	 * 
	 * @return
	 */
	public String queryNotice() {
		try {
			msgOperationsService.buildNotice(getCurrentLoginUserId(), phoneCode, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询启动页
	 * 
	 * @return
	 */
	public String queryStartPage() {
		try {
			msgOperationsService.buildStartPage(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询布告
	 * 
	 * @return
	 */
	public String queryBulletin() {
		try {
			msgOperationsService.buildBulletin(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询专题
	 * 
	 * @return
	 */
	public String queryTheme() {
		try {
			msgOperationsService.buildTheme(start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户专题
	 * 
	 * @return
	 */
	public String queryUserTheme() {
		try {
			msgOperationsService.buildUserTheme(start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}


}
