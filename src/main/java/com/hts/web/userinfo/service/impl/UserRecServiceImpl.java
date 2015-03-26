package com.hts.web.userinfo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.common.pojo.UserConcernName;
import com.hts.web.common.pojo.UserDynamicRec;
import com.hts.web.common.pojo.UserRecInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.userinfo.dao.LabelDao;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserPlatConcernDao;
import com.hts.web.userinfo.dao.UserRecDao;
import com.hts.web.userinfo.service.UserRecService;

@Service("HTSUserRecService")
public class UserRecServiceImpl extends BaseServiceImpl implements
		UserRecService {

	private static Logger log = Logger.getLogger(UserRecServiceImpl.class);
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserPlatConcernDao platConcernDao;
	
	@Autowired
	private UserRecDao userRecDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private LabelDao labelDao;
	
	private static final int REC_TYPE_NONE = -1;
	private static final int REC_TYPE_CONCERN = 0;
	private static final int REC_TYPE_PLAT = 1;
	private static final int REC_TYPE_CITY = 2;
	private static final int REC_TYPE_LABEL = 3;
	private static final int REC_TYPE_OP = 4;
	
	private static final int REC_OP_RANGE = 100;
	
	private static final int OP_SUGGEST_LIMIT = 6;
	
	
	@Override
	public void savePlatConcerns(Integer userId, String loginCodes) {
		if(loginCodes != null) {
			String[] codes = loginCodes.split(",");
			if(codes.length > 0) {
				for(String c : codes) {
					try {
						Integer cid = userInfoDao.queryUIDByLoginCode(c, PlatFormCode.SINA);
						if(cid != null && cid != 0) {
							Integer cidExist = platConcernDao.queryCid(userId, cid);
							if(cidExist == null || cidExist == 0) {
								platConcernDao.savePlatConcern(userId, cid);
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
						log.warn("save platform concern error:" 
								+ "uid=" + userId + ",logincode=" + c + "plat=" + PlatFormCode.SINA);
					}
				}
			}
		}
	}

	@Override
	public void buildRecUser(Integer userId, Map<String, Object> jsonMap)
			throws Exception {
		UserDynamicRec rec = null;
		String recMsg = null;
		
		UserRecInfo recInfo = userInfoDao.queryRecInfo(userId);
		int recType = getRecType(recInfo);
		
		switch(recType) {
		case REC_TYPE_PLAT:
			Long pcount = userRecDao.queryPlatRecCount(userId);
			if(pcount > 0) {
				int start = NumberUtil.getRandomNum(0, pcount.intValue()-1);
				rec = userRecDao.queryPlatRec(userId, start);
				recMsg = "微博好友";
			}
			break;
			
		case REC_TYPE_CITY:
			Long ctcount = userRecDao.queryCityUserCount(userId, recInfo.getCity());
			if(ctcount > 0) {
				int start = NumberUtil.getRandomNum(0, ctcount.intValue() - 1);
				rec = userRecDao.queryCityRecUser(userId, recInfo.getCity(), start);
				recMsg = "同在" + recInfo.getProvince() +  " " + recInfo.getCity();
			}
			break;

		case REC_TYPE_LABEL:
			String[] labels = recInfo.getUserLabel().split(",");
			if(labels.length > 0) {
				int lidx = NumberUtil.getRandomNum(0, labels.length-1);
				String label = labels[lidx];
				Integer labelId = labelDao.queryIdByName(label);
				Long lcount = userRecDao.queryLabelRecCount(userId, labelId);
				if(lcount > 0) {
					rec = userRecDao.queryLabelRec(userId, labelId);
					recMsg = "你们都有" + label + "标签";
				}
			}
			break;
			
		case REC_TYPE_OP:
			Long opcount = userRecDao.queryOpRecCount(userId);
			if(opcount > 0) {
				int start = NumberUtil.getRandomNum(0, opcount.intValue() - 1);
				rec = userRecDao.queryOpRec(userId, start);
				recMsg = "小编推荐";
			}
			break;
			
		case REC_TYPE_NONE:
			break;
			
		default:
			Long conCount = userConcernDao.queryConcernCount(userId);
			if(conCount > 0) {
				int cstart = NumberUtil.getRandomNum(0, conCount.intValue()-1);
				UserConcernName name = userConcernDao.queryConcernName(userId, cstart);
				if(name != null) {
					Long crcount = userRecDao.queryConcernRecCount(userId, name.getId());
					if(crcount > 0) {
						int start = NumberUtil.getRandomNum(0, crcount.intValue() - 1);
						rec = userRecDao.queryConcernRec(userId, name.getId(), start);
						recMsg = "你的好友" + name.getUserName() + "也关注了Ta";
					}
				}
			}
			break;
		}
		
		if(rec == null) {
			Long opcount = userRecDao.queryOpRecCount(userId);
			if(opcount > 0) {
				int start = NumberUtil.getRandomNum(0, opcount.intValue() - 1);
				rec = userRecDao.queryOpRec(userId, start);
				recMsg = "小编推荐";
			}
		}
		jsonMap.put(OptResult.JSON_KEY_USER_REC, rec);
		jsonMap.put(OptResult.JSON_KEY_USER_REC_MSG, recMsg);
	}
	
	
	
	/**
	 * 获取推荐类型
	 * 
	 * @param userId
	 * @return
	 */
	private Integer getRecType(UserRecInfo recInfo) {
		Integer recType = REC_TYPE_NONE;
		List<Integer> reclist = new ArrayList<Integer>();
		if(recInfo != null) {
			if(recInfo.getConcernCount() > 0) {
				reclist.add(REC_TYPE_CONCERN);
			}
			
//			if(!StringUtil.checkIsNULL(recInfo.getUserLabel())) {
//				reclist.add(REC_TYPE_LABEL);
//			}
			
			if(!StringUtil.checkIsNULL(recInfo.getCity())) {
				reclist.add(REC_TYPE_CITY);
			}
			
			if(recInfo.getPlatformCode().equals(PlatFormCode.SINA)) {
				int i = NumberUtil.getRandomNum(0, 3);
				if(i == 0) {
					reclist.add(REC_TYPE_PLAT);
				}
			}
			
			if(reclist.size() > 0) {
				int idx = NumberUtil.getRandomNum(0, reclist.size() - 1);
				return reclist.get(idx);
				
			} else { // 没有任何推荐属性的时候，系统推荐明星
				return REC_TYPE_OP;
			}
		}
		return recType;
	}

	@Override
	public void buildSuggestUser(Integer userId, Map<String, Object> jsonMap)
			throws Exception {
		int opstart = NumberUtil.getRandomNum(0, REC_OP_RANGE);
		List<UserDynamicRec> recList = userRecDao.queryOpRecList(userId, opstart, OP_SUGGEST_LIMIT);
		jsonMap.put(OptResult.JSON_KEY_USER_REC, recList);
	}

}
