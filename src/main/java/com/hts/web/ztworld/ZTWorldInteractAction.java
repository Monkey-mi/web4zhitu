package com.hts.web.ztworld;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.UserOperationsService;
import com.hts.web.ztworld.service.ZTWorldInteractService;
import com.hts.web.ztworld.service.ZTWorldService;

/**
 * <p>
 * 织图互动子模块Action控制器
 * </p>
 * 
 * 创建时间：2013-7-4
 * @author ztj
 *
 */
public class ZTWorldInteractAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1053808493384255769L;
	
	private Integer id;
	private String authorName;
	private String authorAvatar;
	private String content;
	private Integer worldId;
	private Integer clickCount = 1;
	private Integer reId;
	
	private Integer commentId;

	private Integer sinceId = 0;
	private Integer maxId = 0;
	
	private String reportContent; // 举报织图的内容
	private Boolean isAddClick = false; // 是否添加播放次数
	private Integer worldAuthorId;
	private Integer reAuthorId;
	private Boolean im = false;
	private String ids;
	private String atIds;
	private String atNames;
	private String shortLink;
	
	@Autowired
	private ZTWorldInteractService worldInteractService;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private UserOperationsService userOptService;
	
	/*
	 * 评论子模块
	 */
	
	/**
	 * 分页查询织图评论
	 * 
	 * @return
	 */
	public String queryComments() {
		try {
			worldInteractService.buildComments(getCurrentLoginUserId(), 
					worldId, maxId, sinceId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加评论
	 * @return
	 */
	public String addComment() {
		try {
			worldInteractService.saveComment(im, worldId, worldAuthorId, 
					getCurrentLoginUserId(), content, atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 马甲发评论
	 * 
	 * @return
	 * 
	 * @version 3.0.5
	 * @author lynch
	 */
	public String addComment4Zombie() {
		try {
			Integer zombieId = userOptService.getRandomZombieId();
			worldInteractService.saveComment(false, worldId, worldAuthorId, 
					zombieId, content, atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 回复评论
	 * 
	 * @return
	 * @version 3.0.5
	 * @author lynch 2015-10-21
	 */
	public String replyComment() {
		try {
			worldInteractService.saveReply(im, worldId, worldAuthorId,
					getCurrentLoginUserId(), content, reId, reAuthorId, 
					atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 用户删除评论
	 * @return
	 */
	public String deleteComment() {
		try {
			worldInteractService.deleteComment(commentId, worldId, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 举报评论
	 * 
	 * @return
	 */
	public String reportComment() {
		try {
			worldInteractService.saveCommentReport(getCurrentLoginUserId(), commentId, content);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 喜欢子模块
	 */
	
	/**
	 * 喜欢织图
	 * @return
	 */
	public String liked() {
		try {
			PushStatus status = worldInteractService.saveLiked(im, getCurrentLoginUserId(), worldId, worldAuthorId);
			if(!im) 
				JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
			else 
				jsonMap.put(OptResult.JSON_KEY_PHONE, status.getPhone());
				jsonMap.put(OptResult.JSON_KEY_ACCEPT, status.getAccept());
				jsonMap.put(OptResult.JSON_KEY_SHIELD, status.getShield());
				jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, status.getIsMututal());
				jsonMap.put(OptResult.JSON_KEY_USER_ID, status.getUserId());
				jsonMap.put(OptResult.JSON_KEY_REMARK_ME, status.getRemarkMe());
				JSONUtil.optSuccess(jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 无须登陆直接喜欢织图
	 * 
	 * @return
	 */
	public String likeDirect() {
		/*
		 * 防止绕开前端的Cookie判断直接请求后台
		 */
		String likeTag = "ht_like_" + worldId;
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies) {
			String cookieName = c.getName();
			if(likeTag.equals(cookieName)) {
				JSONUtil.optFailed(getCurrentLoginUserId(), "非法请求", jsonMap);
				return StrutsKey.JSON;
			}
		}
		try {
			worldInteractService.saveLikedWithoutLogin(worldId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 客户端无须的登陆直接喜欢织图
	 * 
	 * @return
	 */
	public String likeDirect4Phone() {
		try {
			worldInteractService.saveLikedWithoutLogin(worldId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 取消喜欢织图
	 * @return
	 */
	public String cancelLiked() {
		try {
			worldInteractService.cancelLiked(getCurrentLoginUserId(), worldId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询喜欢指定织图用户
	 * 
	 * @return
	 */
	public String queryLikedUser() {
		try {
			worldInteractService.buildLikedUser(maxId, worldId, getCurrentLoginUserId(), start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 收藏子模块
	 */
	
	/**
	 * 收藏织图
	 * @return
	 */
	public String keep() {
		try {
			worldInteractService.saveKeep(getCurrentLoginUserId(), worldId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 取消收藏织图
	 * @return
	 */
	public String cancelKeep() {
		try {
			worldInteractService.cancelKeep(getCurrentLoginUserId(), worldId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 举报织图
	 * 
	 * @return
	 */
	public String report() {
		try {
			worldInteractService.saveReport(getCurrentLoginUserId(), worldId, reportContent);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 取消举报
	 * @return
	 */
	public String cancelReport() {
		try {
			worldInteractService.cancelReport(getCurrentLoginUserId(), worldId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询世界和互动信息
	 * @return
	 */
	public String queryWorldInteract() {
		try {
			worldInteractService.getWorldInteract(worldId, getCurrentLoginUserId(), 
					commentLimit, likedLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询织图信息
	 * 
	 * @return
	 */
	public String queryInteractByLink() {
		try {
			worldInteractService.getWorldInteractByLink(shortLink, likedLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 分页查询织图评论
	 * 
	 * @return
	 */
	public String queryOpenComment() {
		try {
			worldInteractService.buildOpenComment(worldId, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 查询织图位置信息
	 * 
	 * @return
	 */
	public String queryWorldGeo() {
		try {
			List<HTWorldGeo> list = worldInteractService.getWorldGeo(userId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, list, OptResult.JSON_KEY_HTWORLD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加播放次数
	 * 
	 * @return
	 */
	public String addClickCount() {
		try {
			worldService.addClickCount(worldId, 1);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询某个用户
	 * 
	 * @return
	 */
	public String queryLikeOthersWorldThumbnail() {
		try {
			worldInteractService.buildLikeOthersWorldThumbnail(limit, userId, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询喜欢我的消息
	 * 
	 * @return
	 */
	public String queryLikeMeCount() {
		try {
			worldInteractService.buildLikeMeCount(minId, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询评论reid列表
	 * 
	 * @return
	 */
	public String queryCommentReId() {
		try {
			worldInteractService.buildCommentReId(ids, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ZTWorldInteractService getWorldInteractService() {
		return worldInteractService;
	}

	public void setWorldInteractService(ZTWorldInteractService worldInteractService) {
		this.worldInteractService = worldInteractService;
	}
	

	public ZTWorldService getWorldService() {
		return worldService;
	}

	public void setWorldService(ZTWorldService worldService) {
		this.worldService = worldService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public Integer getSinceId() {
		return sinceId;
	}

	public void setSinceId(Integer sinceId) {
		this.sinceId = sinceId;
	}

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Boolean getIsAddClick() {
		return isAddClick;
	}

	public void setIsAddClick(Boolean isAddClick) {
		this.isAddClick = isAddClick;
	}

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}

	public Boolean getIm() {
		return im;
	}

	public void setIm(Boolean im) {
		this.im = im;
	}

	public Integer getReAuthorId() {
		return reAuthorId;
	}

	public void setReAuthorId(Integer reAuthorId) {
		this.reAuthorId = reAuthorId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getAtIds() {
		return atIds;
	}

	public void setAtIds(String atIds) {
		this.atIds = atIds;
	}

	public String getAtNames() {
		return atNames;
	}

	public void setAtNames(String atNames) {
		this.atNames = atNames;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	
	
}
