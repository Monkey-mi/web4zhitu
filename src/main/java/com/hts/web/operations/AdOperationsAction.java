package com.hts.web.operations;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.AdOperationsService;

/**
 * <p>
 * 广告运营子模块控制器
 * </p>
 * 
 * 创建时间：2013-11-30
 * 
 * @author ztj
 * 
 */
public class AdOperationsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3772110135554548900L;

	private Integer phoneCode = Tag.IOS;
	private String appLink;
	
	@Autowired
	private AdOperationsService adOperationsService;

	public String getAppLink() {
		return appLink;
	}

	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}
	
	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	/**
	 * 检测APP链接
	 * 
	 * @return
	 */
	public String checkAppLink() {
		String ip;
		String link = null;
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		
		try {
			link = adOperationsService.addAppLinkRecordFromURL(request.getServletPath(), ip);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		if(link != null) {
			setAppLink(link);
			return StrutsKey.REDIRECT;
		}
		
		return ERROR;
	}

	/**
	 * 查询App链接
	 * 
	 * @return
	 */
	public String queryAppLink() {
		try {
			adOperationsService.buildAppLink(phoneCode, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
}
