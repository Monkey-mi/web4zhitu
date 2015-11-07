package com.hts.web.push.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSErrorCode;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.PushIM;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.YunbaPushService;
import com.hts.web.push.yunba.YunbaClient;
import com.hts.web.push.yunba.YunbaException;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserShieldDao;

@Service("HTSYunbaPushService")
public class YunbaPushServiceImpl implements YunbaPushService {

	@Autowired
	private UserShieldDao userShieldDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private YunbaClient yunbaClient;
	
	
	public void pushIMMsg(Integer toAlias, PushIM msg, String title,
			Integer phoneCode, Integer notified, Integer shield) throws HTSException {
		if(shield == null)
			shield = userShieldDao.queryShieldId(toAlias, msg.getUid()) == null ? Tag.FALSE : Tag.TRUE;
		
		if(shield.equals(Tag.FALSE)) {
			try {
				Integer isMutual = userConcernDao.queryIsMututal(toAlias, msg.getUid());
				if(isMutual == null) {
					isMutual = Tag.UN_CONCERN;
				}
				msg.setR(isMutual);
				net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(msg);
				if(phoneCode.equals(Tag.IOS) && notified.equals(Tag.TRUE) && !StringUtil.checkIsNULL(title)) {
					JSONObject apnJSON = new JSONObject();
					JSONObject aps = new JSONObject();
					aps.put("sound", "default");
					aps.put("badge", 1);
					aps.put("alert", title);
					apnJSON.put("aps", aps);
					apnJSON.put("a", msg.getA());
					apnJSON.put("uid", msg.getUid());
					yunbaClient.publishToAlias(String.valueOf(toAlias), json.toString(), apnJSON);
				} else {
					yunbaClient.publishToAlias(String.valueOf(toAlias), json.toString());
				}
			} catch (Exception e) {
				throw new HTSException(HTSErrorCode.REQUEST_FAIL, e.getMessage());
			}
		}
	}
	
	@Override
	public void pushBulletin(Integer pushAction, String content, String sid, List<Integer> recipientIds) throws HTSException {
		JSONObject apnJSON = new JSONObject();
		JSONObject aps = new JSONObject();
		try {
			aps.put("sound", "default");
			aps.put("badge", 1);
			aps.put("alert", content);
			apnJSON.put("aps", aps);
			apnJSON.put("a", pushAction);
			if(sid != null) {
				apnJSON.put("sid", sid);
			}
			yunbaClient.publishToAliasBatch(JSONArray.fromObject(recipientIds), 
					content, apnJSON);
		} catch (JSONException e) {
			throw new HTSException(HTSErrorCode.REQUEST_FAIL, e.getMessage());
		} catch (YunbaException e) {
			throw new HTSException(HTSErrorCode.REQUEST_FAIL, e.getMessage());
		}
	}

	@Override
	public void pushTopicMsg(String topic, PushIM msg) throws HTSException {
		try {
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(msg);
			JSONObject apnJSON = new JSONObject();
			JSONObject aps = new JSONObject();
			aps.put("sound", "default");
			aps.put("badge", 1);
			aps.put("alert", msg.getM());
			apnJSON.put("aps", aps);
			yunbaClient.publishToCommonTopic(topic, json.toString(), apnJSON);
		} catch (Exception e) {
			throw new HTSException(HTSErrorCode.REQUEST_FAIL, e.getMessage());
		}
			
		
	}

	@Override
	public void pushAppMsg(String topic, String msg) throws Exception {
		try {
			JSONObject apnJSON = new JSONObject();
			JSONObject aps = new JSONObject();
			aps.put("sound", "default");
			aps.put("badge", 1);
			aps.put("alert", msg);
			apnJSON.put("aps", aps);
			yunbaClient.publishToCommonTopic(topic, msg, apnJSON);
		} catch (Exception e) {
			throw new HTSException(HTSErrorCode.REQUEST_FAIL, e.getMessage());
		}
		
	}

	@Override
	public void pushTopicMsg(String topic, PushIM msg, Map<String, Object> extra)
			throws HTSException {
		try {
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(msg);
			JSONObject apnJSON = new JSONObject();
			JSONObject aps = new JSONObject();
			aps.put("sound", "default");
			aps.put("badge", 1);
			aps.put("alert", msg.getM());
			apnJSON.put("aps", aps);
			apnJSON.put("a", msg.getA());
			Set<String> keies = extra.keySet();
			for(String k : keies) {
				apnJSON.put(k, extra.get(k));
			}
			yunbaClient.publishToCommonTopic(topic, json.toString(), apnJSON);
		} catch (Exception e) {
			throw new HTSException(HTSErrorCode.REQUEST_FAIL, e.getMessage());
		}
	}
}
