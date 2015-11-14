package com.hts.web.wechat;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.wechat.service.WechatService;

import net.sf.json.JSONObject;

/**
 * 微信信息查询接口,详情见:http://mp.weixin.qq.com/wiki/7/1c97470084b73f8e224fe6d9bab1625b.html
 * 
 * @author lynch
 *
 */
public class WechatAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186905433339744500L;
	
	@Autowired
	private WechatService wechatService;
	
	/**
	 * 获取签名
	 * 
	 * @return
	 */
	public String signature() {
		String url = request.getHeader("Referer");
		try {
			JSONObject jsObj = wechatService.getSignature(url);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, jsObj, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
