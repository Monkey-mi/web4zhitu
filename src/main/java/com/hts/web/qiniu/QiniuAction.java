package com.hts.web.qiniu;

import org.json.JSONException;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;

/**
 * <p>
 * 七牛控制器
 * </p>
 * 
 * 创建时间：2014-1-14
 * @author ztj
 *
 */
public class QiniuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609088920467411299L;

	/**
	 * 生成uptoken
	 * @return
	 */
	public String uptoken() {
		Config.ACCESS_KEY = (String) servletContext.getAttribute("qiniuAccessKey");
        Config.SECRET_KEY = (String) servletContext.getAttribute("qiniuSecretKey");
        String uptoken = null;
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        String bucketName = (String) servletContext.getAttribute("qiniuBucket");
        PutPolicy putPolicy = new PutPolicy(bucketName);
        try {
			uptoken = putPolicy.token(mac);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, uptoken, OptResult.JSON_KEY_TOKEN, jsonMap);
		} catch (AuthException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		} catch (JSONException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
        return StrutsKey.JSON;
	}
	
	
}
