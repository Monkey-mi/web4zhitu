package com.hts.web.userinfo.service;

import com.hts.web.common.service.BaseService;

/**
 * 重置密码，业务逻辑层
 * @time 2014年5月5日 17:34:10
 * @author zxx
 *
 */

public interface UserRetrievePWDService extends BaseService{
	public boolean resetPWD(String loginCode,String sid,String pwd)throws Exception;
	public void requestRPWD(String loginCode)throws Exception;
}
