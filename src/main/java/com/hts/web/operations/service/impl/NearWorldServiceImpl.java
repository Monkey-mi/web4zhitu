package com.hts.web.operations.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.addr.service.CityService;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.mongo.NearWorldMongoDao;
import com.hts.web.operations.dao.mongo.NearWorldStarMongoDao;
import com.hts.web.operations.service.NearWorldService;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.ztworld.service.ZTWorldService;

@Service("HTSNearWorldService")
public class NearWorldServiceImpl extends BaseServiceImpl implements NearWorldService {
	
	private static final double STAR_WORLD_MAX_INSTANCE = 0.01;
	
	private static final int STAR_WORLD_LIMIT = 6;
	
	@Autowired
	private NearWorldMongoDao worldMongoDao;
	
	@Autowired
	private NearWorldStarMongoDao worldStarMongoDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public List<HTWorldInteractDto> queryNearWorld(double longitude, double latitude, 
			int start, int limit) {
		
		if(longitude == 0 || latitude == 0) {
			throw new IllegalArgumentException("longitude or latitude can not be null");
		}
		
		List<HTWorldInteractDto> starList = worldStarMongoDao.queryNear(longitude, latitude, 
				STAR_WORLD_MAX_INSTANCE, start, STAR_WORLD_LIMIT);
		final List<HTWorldInteractDto> list = worldMongoDao.queryNear(longitude, latitude, start, limit);

		// 合并达人和普通织图列表
		if(starList.size() > 0) {
			Set<Integer> starWids = new HashSet<Integer>();
			for(int i = 0; i < starList.size(); i++) {
				starWids.add(starList.get(i).getId());
			}
			
			for(int i = 0; i < starList.size(); i++) {
				if(starWids.contains(list.get(i).getId())) {
					list.remove(i);
					--i;
					continue;
				}
			}
			list.addAll(0, starList);
		}
		
		// 查询用户信息
		if (!list.isEmpty()) {
			final Map<Integer,List<Integer>> indexMap = new HashMap<Integer, List<Integer>>();
			for (int i = 0; i < list.size(); i++) {
				Integer auid = list.get(i).getAuthorId();
				if(indexMap.containsKey(auid)) {
					indexMap.get(auid).add(i);
				} else {
					List<Integer> l = new ArrayList<Integer>();
					l.add(i);
					indexMap.put(auid, l);
				}
			}
			Integer[] uids = new Integer[indexMap.size()];
			indexMap.keySet().toArray(uids);
			
			userInfoDao.queryUserInfoDtos(uids, new RowCallback<UserInfoDto>() {
				
				@Override
				public void callback(UserInfoDto t) {
					Integer uid = t.getId();
					for(Integer i : indexMap.get(uid)) {
						list.get(i).setUserInfo(t);
					}
				}
			});
		}
		
		return list;
	}
	

	@Override
	public List<HTWorldInteractDto> queryNearWorld(String city, 
			int start, int limit) {
		
		if(city == null || city.equals("")) {
			throw new IllegalArgumentException("longitude or latitude can not be null");
		}
		
		double[] loc = cityService.getLocByCityName(city);
		if(loc == null)
			throw new NullPointerException(city + " not exists");
		return queryNearWorld(loc[0], loc[1], start, limit);
	}

	@Override
	public void saveNearWorld(HTWorld world) {
		if(world.getLongitude() == null || world.getLongitude() == 0 
				|| world.getLongitude() > 180 || world.getLongitude() < -180
				|| world.getLatitude() == null || world.getLatitude() == 0 
				|| world.getLatitude() > 90 || world.getLatitude() < -90)
			return;
		
		HTWorldInteractDto near = new HTWorldInteractDto();
		near.setLoc(new Double[]{world.getLongitude(), world.getLatitude()});
		BeanUtils.copyProperties(world, near);
		worldMongoDao.saveWorld(near);
		if(userInfoDao.queryStar(world.getAuthorId()) > 0) {
			worldStarMongoDao.saveWorld(near);
		}
	}

	@Override
	public void deleteNearWorld(Integer id) {
		worldMongoDao.deleteWorld(id);
		worldStarMongoDao.deleteWorld(id);
	}


	@Override
	public void buildNearWorld(String address, Double longitude,
			Double latitude, int start, int limit, Map<String, Object> jsonMap,
			Integer commentLimit, Integer likedLimit) throws Exception {
		
		List<HTWorldInteractDto> list = null;
		
		if(longitude == null || latitude == null ){
			if(address != null && !"".equals(address.trim())){
				list = queryNearWorld(address,start,limit);
			}else{
				throw new IllegalArgumentException("either the address or the longitude and the latitude can not be null ");
			}
		}else{
			list = queryNearWorld(longitude,latitude,start,limit);
		}
		
		worldService.extractLikeComment(commentLimit, likedLimit, list);
		userInfoService.extractVerify(list);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}


	@Override
	public void buildNearLabel(String address, Double longitude,
			Double latitude, int start, int limit, Map<String, Object> jsonMap)
			throws Exception {
		
		List<OpNearLabelDto> list = null;
		
		if(longitude == null || latitude == null ){
			if(address != null && !"".equals(address.trim())){
				double[] loc = cityService.getLocByCityName(address);
				if(loc == null){
					throw new NullPointerException(address + " not exists");
				}else{
					
				}
			}else{
				throw new IllegalArgumentException("either the address or the longitude and the latitude can not be null ");
			}
		}else{
			
		}
	}


	@Override
	public void buildNearBanner(String address, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void buildNearLabelWorld(Integer labelId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
