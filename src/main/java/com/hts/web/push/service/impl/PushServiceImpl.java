package com.hts.web.push.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.PushIM;
import com.hts.web.common.pojo.PushMsg;
import com.hts.web.common.pojo.PushSysIM;
import com.hts.web.common.pojo.PushWorldIM;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.YunbaPushService;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserRemarkDao;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldKeepDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.exceptions.InvalidSSLConfig;
import com.notnoop.exceptions.NetworkIOException;
import com.notnoop.exceptions.RuntimeIOException;

public class PushServiceImpl implements PushService {
	
	private static final String API = "http://sdk.open.api.igexin.com/apiex.htm"; //OpenService接口地址
	
	private static final String TIP_CONCERN = "关注了你";
	private static final String TIP_LIKED = "赞了你的织图";
	private static final String TIP_MSG = "你收到1条私信";
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
	@Autowired
	private HTWorldKeepDao worldKeepDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserRemarkDao userRemarkDao;
	
	@Autowired
	private YunbaPushService yunbaPushService;
	
	@Value("${yunba.commonTopic}")
	private String commonTopic = "t1";
	
	@Value("${push.appId}")
	private String appId = "pAaKmHFNI18yYqlStWrBJ8";
	
	@Value("${push.appKey}")
	private String appKey = "2wD86REWflAkeaI8x52CY7";
	
	@Value("${push.masterSecret}")
	private String masterSecret = "NitfPMVs7L9Ft2Ct8zy6N9";
	
	@Value("${push.apnsPasswd}")
	private String apnsPasswd = "huanghuang8888";
	
	@Value("${push.isProduction}")
	private Boolean isProduction = false;
	
	@Value("${push.apnsMaxConnections}")
	private Integer apnsMaxConnections = 150; // 最大连接数
	
	@Value("${push.certificateResource}")
	private Resource certificateResource; // 证书文件资源
	
	private IIGtPush gtPush;
	
	private ApnsService apnsPoolService; // Apns连接池推送服务
	
	private TaskExecutor pushExecutor; // 推送多线程执行器
	
	public Resource getCertificateResource() {
		return certificateResource;
	}

	public void setCertificateResource(Resource certificateResource) {
		this.certificateResource = certificateResource;
	}

	@Override
	public TaskExecutor getPushExecutor() {
		return pushExecutor;
	}

	public void setPushExecutor(TaskExecutor pushExecutor) {
		this.pushExecutor = pushExecutor;
	}
	
	public Integer getApnsMaxConnections() {
		return apnsMaxConnections;
	}

	public void setApnsMaxConnections(Integer apnsMaxConnections) {
		this.apnsMaxConnections = apnsMaxConnections;
	}

	public ApnsService getApnsPoolService() {
		return apnsPoolService;
	}

	public void setApnsPoolService(ApnsService apnsPoolService) {
		this.apnsPoolService = apnsPoolService;
	}

	public Boolean getIsProduction() {
		return isProduction;
	}

	public void setIsProduction(Boolean isProduction) {
		this.isProduction = isProduction;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public IIGtPush getGtPush() {
		return gtPush;
	}

	public void setGtPush(IIGtPush gtPush) {
		this.gtPush = gtPush;
	}

	public String getApnsPasswd() {
		return apnsPasswd;
	}

	public void setApnsPasswd(String apnsPasswd) {
		this.apnsPasswd = apnsPasswd;
	}
	
	public String getCommonTopic() {
		return commonTopic;
	}

	public void setCommonTopic(String commonTopic) {
		this.commonTopic = commonTopic;
	}

	/**
	 * 初始化方法
	 * 
	 * @throws RuntimeIOException
	 * @throws InvalidSSLConfig
	 * @throws IOException
	 */
	public void init() throws RuntimeIOException, InvalidSSLConfig, IOException {
		gtPush = new IGtPush(API, appKey, masterSecret);
		apnsPoolService = APNS.newService()
		        .withCert(certificateResource.getFile().getAbsolutePath(), apnsPasswd)
		        .withAppleDestination(isProduction)
		        .asPool(apnsMaxConnections)
		        .build();
	}
	
	@Override
	public void pushComment(final Integer id, final Integer authorId, 
			final Integer worldId, final Integer wauthorId, final String content, 
			final UserPushInfo userPushInfo, final Integer shield) throws Exception{
		if(userPushInfo != null && authorId != userPushInfo.getId() && shield.equals(Tag.FALSE)) {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
						
						// 推送失败回调
						PushFailedCallback callback = new PushFailedCallback() {
							
							@Override
							public void onPushFailed(Exception e) {
								Log.warn(authorId, "comment push error (" + "id=" + id + ", authorId=" + authorId 
										+ ", wauthorId=" + userPushInfo.getId() + ")");
//								worldCommentDao.updatePushed(id, Tag.FALSE);
							}
						};
						
						
						String fullName = userRemarkDao.queryRemark(userPushInfo.getId(), authorId);
						if(fullName == null) {
							fullName = userInfoDao.queryUserNameById(authorId);
						}
						
						// 对方已经开通了IM
						if(UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
							String comment = content.trim();
							if(comment.startsWith(":")) {
								comment = comment.substring(1).trim();
							}
							pushIMMessage(userPushInfo.getId(), new PushWorldIM(Tag.PUSH_ACTION_COMMENT, fullName, 
									comment, authorId, userPushInfo.getId(), worldId, wauthorId, 0, id), 
									fullName + ": " + comment, userPushInfo.getPhoneCode(), userPushInfo.getAcceptCommentPush(), shield, callback);
						
						// 对方未开通IM使用个推
						} else if(userPushInfo.getOnline() == Tag.TRUE 
								&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
								&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())
								&& userPushInfo.getAcceptCommentPush() == Tag.TRUE) {  
				
							String authorName = PushUtil.getShortName(fullName);
							String title = authorName + PushUtil.getShortComment(content);
							pushInteractMessage(title, Tag.PUSH_ACTION_COMMENT, userPushInfo.getPhoneCode(), userPushInfo.getPushToken(), 
									userPushInfo.getVer(), new PushMsg(Tag.PUSH_ACTION_COMMENT, authorName, 
											PushUtil.getShortComment(content)), callback);
						}
					}
			});
		}
		
	}
	
	@Override
	public void pushReply(final Integer id, final Integer authorId, final Integer worldId,
			final Integer wauthorId, final Integer reId, final String content, 
			final UserPushInfo userPushInfo, final Integer shield) throws Exception{
		if(userPushInfo != null && authorId != userPushInfo.getId() && shield.equals(Tag.FALSE)) {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
						
						// 推送失败回调
						PushFailedCallback callback = new PushFailedCallback() {
							
							@Override
							public void onPushFailed(Exception e) {
								Log.warn(authorId, "reply push error (" + "id=" + id + ", authorId=" + authorId 
										+ ", wauthorId=" + userPushInfo.getId() + ")");
//								worldCommentDao.updatePushed(id, Tag.FALSE);
							}
						};
						
						String fullName = userRemarkDao.queryRemark(userPushInfo.getId(), authorId);
						if(fullName == null) {
							fullName = userInfoDao.queryUserNameById(authorId);
						}
						
						// 对方已经开通了IM
						if(UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
							String reply = content.trim();
							if(reply.startsWith("@")) {
								int colonIndex = reply.indexOf(":") + 1;
								reply = reply.substring(colonIndex).trim();
							}
							pushIMMessage(userPushInfo.getId(), new PushWorldIM(Tag.PUSH_ACTION_REPLY, 
									fullName, reply, authorId, userPushInfo.getId(), worldId, wauthorId, reId, id), 
									fullName + ": " + reply, userPushInfo.getPhoneCode(), userPushInfo.getAcceptReplyPush(), shield, callback);
							
						// 对方未开通IM使用个推
						} else if(userPushInfo.getOnline() == Tag.TRUE 
							&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
							&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())
							&& userPushInfo.getAcceptReplyPush() == Tag.TRUE) {
							
							String authorName = PushUtil.getShortName(fullName);
							String title = authorName + PushUtil.getShortReply(content);
							pushInteractMessage(title, Tag.PUSH_ACTION_REPLY, userPushInfo.getPhoneCode(), userPushInfo.getPushToken(), 
									userPushInfo.getVer(),new PushMsg(Tag.PUSH_ACTION_REPLY, authorName, PushUtil.getShortReply(content)), callback);
						}
					}
			});
		}
		
	}
	
	@Override
	public void pushLiked(final Integer id, final Integer userId, final Integer worldId,
			final Integer wauthorId, final UserPushInfo userPushInfo, final Integer shield) throws Exception {
		if(userPushInfo != null && userId != userPushInfo.getId() && shield.equals(Tag.FALSE)) {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
						
						// 推送失败回调
						PushFailedCallback callback = new PushFailedCallback() {
							
							@Override
							public void onPushFailed(Exception e) {
								Log.warn(userId, "liked push error (" + "id=" + id + ", userId=" + userId 
										+ ", wauthorId=" + userPushInfo.getId() + ", worldId=" + worldId + ")");
//								worldLikedDao.updatePushed(id, Tag.FALSE);
							}
						};
						
						String fullName = userRemarkDao.queryRemark(userPushInfo.getId(), userId);
						if(fullName == null) {
							fullName = userInfoDao.queryUserNameById(userId);
						}
						
						// 对方已经开通了IM, 默认屏蔽用户的提醒
						if(UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
							pushIMMessage(userPushInfo.getId(), new PushWorldIM(Tag.PUSH_ACTION_LIKED, fullName, TIP_LIKED,
									userId, userPushInfo.getId(), worldId, wauthorId, 0, id), 
									fullName + TIP_LIKED, userPushInfo.getPhoneCode(), userPushInfo.getAcceptLikedPush(), shield, callback);
							
						// 对方未开通IM使用个推
						} else if(userPushInfo.getOnline() == Tag.TRUE 
								&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
								&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())
								&& userPushInfo.getAcceptLikedPush() == Tag.TRUE) {
							
							String name = PushUtil.getShortName(fullName);
							String title = name + TIP_LIKED;
							pushInteractMessage(title, Tag.PUSH_ACTION_LIKED, userPushInfo.getPhoneCode(), userPushInfo.getPushToken(), userPushInfo.getVer(),
									new PushMsg(Tag.PUSH_ACTION_LIKED, name, ""), callback);
						}
					}
			});
		}
	}
	
	@Override
	public void pushConcern(final Integer id, final Integer userId, final Integer concernId, 
			final UserPushInfo userPushInfo, final Integer shield) {
		
		if(userPushInfo != null && userId != userPushInfo.getId() && shield.equals(Tag.FALSE)) {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
						// 推送失败回调
						PushFailedCallback callback = new PushFailedCallback() {
							
							@Override
							public void onPushFailed(Exception e) {
								Log.warn(userId, "concern push error (" + "id=" + id + ", userId=" + userId 
										+ ", concernId=" + userPushInfo.getId() + ")");
//								userConcernDao.updatePushed(id, Tag.FALSE);
							}
						};
						
						String fullName = userInfoDao.queryUserNameById(userId);
						
						// 对方已经开通了IM
						if(UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
							pushIMMessage(userPushInfo.getId(), new PushIM(Tag.PUSH_ACTION_CONCERN, fullName, TIP_CONCERN, 
									userId, userPushInfo.getId()), 
									fullName + TIP_CONCERN, userPushInfo.getPhoneCode(), userPushInfo.getAcceptConcernPush(), shield, callback);
							
						// 对方未开通IM使用个推
						} else if(userPushInfo.getOnline() == Tag.TRUE 
								&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
								&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())
								&& userPushInfo.getAcceptConcernPush() == Tag.TRUE) {
							
							String name = PushUtil.getShortName(fullName);
							String title = name + TIP_CONCERN;
							pushInteractMessage(title, Tag.PUSH_ACTION_CONCERN, userPushInfo.getPhoneCode(), userPushInfo.getPushToken(), userPushInfo.getVer(),
									new PushMsg(Tag.PUSH_ACTION_CONCERN, name, ""), callback);
						}
					}
			});
		}
	}
	
	@Override
	public void pushMiShuMessage(final Integer senderId, final String content, final UserPushInfo userPushInfo, final PushFailedCallback callback)
			throws Exception {
		if(userPushInfo != null && senderId != userPushInfo.getId()) {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
					
					String fullName = userInfoDao.queryUserNameById(senderId);
						
					// 对方已经开通了IM
					if(UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
						pushIMMessage(userPushInfo.getId(), new PushIM(Tag.PUSH_ACTION_XIAOMI_MSG,
								fullName, content, senderId, userPushInfo.getId()), 
								fullName + ": " + content, userPushInfo.getPhoneCode(), userPushInfo.getAcceptUmsgPush(), null, callback);
					
					// 对方未开通IM使用个推
					} else if(userPushInfo.getOnline() == Tag.TRUE 
							&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
							&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())) {
						
						String name = userInfoDao.queryUserNameById(senderId);
						JSONObject jsObj = JSONObject.fromObject(new PushMsg(Tag.PUSH_ACTION_XIAOMI_MSG, PushUtil.getShortName(name), content));
						String pushContent = jsObj.toString();
						pushTransmissionMessage(userPushInfo.getPhoneCode(), userPushInfo.getVer(),
								userPushInfo.getPushToken(), pushContent, TIP_MSG, callback);
					}
				}
			});
		}
		
	}
	
	@Override
	public void pushSysMessage(final String title, final Integer senderId, final String content, 
			final UserPushInfo userPushInfo, final Integer sysType, final PushFailedCallback callback)
			throws Exception {
		if(userPushInfo != null && senderId != userPushInfo.getId()) {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
						
						// 对方已经开通了IM
						if(UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
							pushIMMessage(userPushInfo.getId(), new PushSysIM(Tag.PUSH_ACTION_SYS_MSG, "", content, 
									senderId, userPushInfo.getId(), sysType), 
									content, userPushInfo.getPhoneCode(), userPushInfo.getAcceptMsgPush(), null, callback);
						
						// 对方未开通IM使用个推
						} else if(userPushInfo.getOnline() == Tag.TRUE 
								&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
								&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())
								&& userPushInfo.getAcceptMsgPush() == Tag.TRUE) {
							
							JSONObject jsObj = JSONObject.fromObject(new PushMsg(Tag.PUSH_ACTION_SYS_MSG, null, title));
							String pushContent = jsObj.toString();
							pushTransmissionMessage(userPushInfo.getPhoneCode(), userPushInfo.getVer(),
									userPushInfo.getPushToken(), pushContent, title, callback);
						}
					}
			});
		}
		
	}
	
	@Override
	public void pushShield(final Integer userId, final Integer shieldId, final Integer shield) throws Exception {
			pushExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
						UserPushInfo userPushInfo = userInfoDao.queryUserPushInfoById(shieldId);
						// 对方已经开通了IM
						if(userPushInfo != null && UserInfoUtil.checkIsImVersion(userPushInfo.getVer())) {
							pushIMMessage(userPushInfo.getId(), new PushIM(Tag.PUSH_ACTION_SHIELD_MSG, "",
									String.valueOf(shield), userId, userPushInfo.getId()), null,
									userPushInfo.getPhoneCode(), Tag.FALSE, Tag.FALSE, new PushFailedCallback() {
										
										@Override
										public void onPushFailed(Exception e) {
											Log.warn(userId, "An error occured while shielding " + shieldId + " by " + userId);
										}
									});
						}
					}
			});
	}
	
	
	@Override
	public void pushAppMessage(String content) throws Exception {
		yunbaPushService.pushAppMsg(commonTopic, content);
	}
	
	/**
	 * 推送互动消息
	 * 
	 * @param phoneCode
	 * @param pushToken
	 * @param serializable
	 */
	private void pushInteractMessage(String title, final Integer action, final Integer phoneCode, final String pushToken, Float ver,
			final Object serializable, final PushFailedCallback callback) {
		JSONObject jsObj = JSONObject.fromObject(serializable);
		String pushContent = jsObj.toString();
		pushTransmissionMessage(phoneCode, ver, pushToken, pushContent, title, callback);
	}
	
	
	/**
	 * 推送playload消息
	 * 
	 * @param phoneCode
	 * @param ver
	 * @param pushToken
	 * @param pushContent
	 * @param title
	 * @param callback
	 */
	private void pushTransmissionMessage(final Integer phoneCode, final Float ver, final String pushToken, 
			final String pushContent, final String title, final PushFailedCallback callback) {
		try {
			switch (phoneCode) {
			case Tag.ANDROID:
				gexinTransmissionPush(phoneCode, ver, pushToken, title, pushContent);
				break;
			case Tag.IOS:
				if(ver >= Tag.VERSION_2_9_5) //2.9.5版本以后使用个推
						gexinTransmissionPush(phoneCode, ver, pushToken, title, pushContent);
				else  // 使用apns推送
					apnsPush(title, pushToken, pushContent, apnsPoolService);
				break;
			default:
				break;
			}
		} catch (HTSException e) {
			callback.onPushFailed(e);
		}
	}
	
	@Override
	public void pushListMessage(final String content, final Integer pushAction, final String pushId, 
			List<UserPushInfo> pushInfoList, final PushFailedCallback callback) throws Exception {
		
		List<String> apnsTokenList = new ArrayList<String>();
		final List<Target> gexinTargetList = new ArrayList<Target>();
		
		for(UserPushInfo userPushInfo : pushInfoList) {
			if(userPushInfo != null && userPushInfo.getOnline() == Tag.TRUE 
					&& !StringUtil.checkIsNULL(userPushInfo.getPushToken()) 
					&& !Tag.INVALID_PUSH_TOKEN.equals(userPushInfo.getPushToken())
					&& userPushInfo.getAcceptMsgPush() == Tag.TRUE) {
				if(userPushInfo.getPhoneCode().equals(Tag.IOS) && userPushInfo.getVer() < Tag.VERSION_2_9_5) {
					apnsTokenList.add(userPushInfo.getPushToken());
				} else {
					Target target = new Target();
					target.setAppId(appId);
					target.setClientId(userPushInfo.getPushToken());
					gexinTargetList.add(target);
				}
			}
		}
		
		// 通过个推批量推送
		if(gexinTargetList.size() > 0) {
			try {
				gexinListPush(appId, appKey, gtPush, content, pushAction, pushId, gexinTargetList);
			} catch (HTSException e) {
				callback.onPushFailed(e);
			}
		}
		
		// 通过apns推送
		if(apnsTokenList.size() > 0) {
			PushMsg psMsg = new PushMsg(pushAction, null, content, pushId);
			String playloadMsg = JSONObject.fromObject(psMsg).toString();
			
			String payload = APNS.newPayload()
	                .badge(1)
	                .sound("")
	                .customField("playload", playloadMsg)
	                .localizedKey(content)
	                .build();
			try {
				apnsPoolService.push(apnsTokenList, payload);
			} catch(NetworkIOException e) {
				callback.onPushFailed(e);
			}
		}
	}
	
	/**
	 * 个信传透playload消息推送
	 * 
	 * @param pushToken
	 * @param content
	 */
	private void gexinTransmissionPush(Integer phoneCode, Float ver, 
			String pushToken, String title, String content) throws HTSException {
		
		// Android推送配置
		String appId = this.appId;
		String appKey = this.appKey;
		IIGtPush gtPush = this.gtPush;
		
		SingleMessage message = new SingleMessage();
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent(content);
		try {
			template.setPushInfo("open",1,"","",content, title,"","");
		} catch (Exception e) {
			throw new HTSException(e.getMessage());
		}
		template.setTransmissionType(2);
		message.setData(template);
		message.setOffline(true);
		message.setOfflineExpireTime(10 * 60 * 1000);
		
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(pushToken);
		IPushResult result = gtPush.pushMessageToSingle(message, target);
		Object res = result.getResponse().get("result");
		if(!res.equals("ok")) {
			throw new HTSException(res.toString());
		}
	}
	
	/**
	 * 个信多用户批量推送
	 * 
	 * @param appId
	 * @param appKey
	 * @param gtPush
	 * @param content
	 * @param pushAction
	 * @param pushId
	 * @param targets
	 * @throws HTSException
	 */
	private void gexinListPush(String appId, String appKey, IIGtPush gtPush, 
			String content, Integer pushAction, String pushId, List<Target> targets) throws HTSException {
		
		PushMsg psMsg = new PushMsg(pushAction, null, content, pushId);
		String playload = JSONObject.fromObject(psMsg).toString();
		
		ListMessage message = new ListMessage();
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(2);
		template.setTransmissionContent(playload);
		try {
			template.setPushInfo("open",1,"",null,playload,content,"","");
		} catch (Exception e) {
			throw new HTSException(e.getMessage());
		}
		
		message.setData(template);
		message.setOffline(true);
		message.setOfflineExpireTime(120 * 60 * 1000);
		
		String contentId = gtPush.getContentId(message);
		IPushResult result = gtPush.pushMessageToList(contentId, targets);
		Object res = result.getResponse().get("result");
		if(!res.equals("ok")) {
			throw new HTSException(res.toString());
		}
	}
	
	public static void main(String[] args) {
		String token = "581344d5 fa048084 e6acaefd 54e12e7f 014b0085 eced6c44 91a7d762 85ca4a4c";
		String payload = APNS.newPayload()
                .badge(1)
                .sound("")
                .customField("playload", "测试")
                .localizedKey("测试内容")
                .build();
	}
	
	/**
	 * APNS推送
	 * @param title
	 * @param pushToken
	 * @param content
	 * @param apnsService
	 * @throws HTSException
	 */
	private void apnsPush(String title, String pushToken, Object content, ApnsService apnsService) throws HTSException {
		String payload = APNS.newPayload()
                .badge(1)
                .sound("")
                .customField("playload", content)
                .localizedKey(title)
                .build();
		try {
			apnsService.push(pushToken, payload);
		} catch(NetworkIOException e) {
			throw new HTSException(e);
		}
	}
	
	/**
	 * 推送IM消息
	 * 
	 * @param toAlias
	 * @param msg
	 * @param phoneCode
	 * @param notified
	 * @param shield
	 * @param callback
	 */
	private void pushIMMessage(Integer toAlias, PushIM msg, String title, Integer phoneCode, 
			Integer notified, Integer shield, PushFailedCallback callback) {
		try {
			yunbaPushService.pushIMMsg(toAlias, msg, title, phoneCode, notified, shield);
		} catch (HTSException e) {
			callback.onPushFailed(e);
		}
	}
	
	/**
	 * 推送失败回调接口
	 * 
	 * @author ztj
	 */
	public interface PushFailedCallback {
		public void onPushFailed(Exception e);
	}

	@Override
	public void pushUserRecSysMsg(Integer recUid, String content) throws Exception {
		Map<String, Object> extra = new HashMap<String, Object>();
		extra.put("sid", recUid);
		yunbaPushService.pushTopicMsg(commonTopic, 
				new PushSysIM(Tag.PUSH_ACTION_USER_REC_MSG, "",content, 0, 0, recUid), extra);
	}

	@Override
	public void apnsPushTest(String token) throws HTSException {
		apnsPush("测试标题", token, "测试内容",apnsPoolService);
	}


}
