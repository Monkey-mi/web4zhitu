package com.hts.web.userinfo.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.ObjectWithConcerned;
import com.hts.web.common.pojo.ObjectWithIsMututal;
import com.hts.web.common.pojo.UserIsMututal;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.service.UserConcernService;

@Service("HTSUserConcernService")
public class UserConcernServiceImpl extends BaseServiceImpl implements
		UserConcernService {
	
	@Autowired
	private UserConcernDao userConcernDao;

	@Override
	public void extractConcernStatus(Integer joinId, List<? extends ObjectWithConcerned> objList) {
		int size = objList.size();
		if(size > 0) {
			Set<Integer> cidSet = new HashSet<Integer>();
			Integer[] concernIds = new Integer[size];
			for(int i = 0; i < size; i++) {
				ObjectWithConcerned dto = objList.get(i);
				Integer uid = dto.getAuthorId();
				if(!cidSet.contains(uid)) {
					concernIds[i] = dto.getAuthorId();
					cidSet.add(dto.getAuthorId());
				}
			}
			if(joinId != -1) {
				Set<Integer> cidSet2 = userConcernDao.queryConcernIds(joinId, concernIds);
				for(ObjectWithConcerned dto : objList) {
					if(cidSet2.contains(dto.getAuthorId())) {
						dto.setConcerned(Tag.TRUE);
					}
				}
			}
		}
	}

	@Override
	public void extractIsMututal(Integer joinId,
			final List<? extends ObjectWithIsMututal> objList) {
		int size = objList.size();
		if(size > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] cids = new Integer[size];
			for(int i = 0; i < size; i++) {
				Integer uid = objList.get(i).getUserId();
				cids[i] = uid;
				indexMap.put(uid, i);
			}
			userConcernDao.queryIsMututal(joinId, cids, new RowCallback<UserIsMututal>() {
	
				@Override
				public void callback(UserIsMututal t) {
					Integer index = indexMap.get(t.getConcernId());
					objList.get(index).setIsMututal(t.getIsMututal());
					
				}
				
			});
		}
	}
	
}
