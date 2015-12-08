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
import com.hts.web.common.pojo.AddrCity;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.common.pojo.OpNearCityGroupDto;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.hts.web.common.pojo.OpNearLabelWorldDto;
import com.hts.web.common.pojo.OpNearWorldDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.operations.dao.BulletinCacheDao;
import com.hts.web.operations.dao.NearLabelWorldDao;
import com.hts.web.operations.dao.NearRecommendCityCacheDao;
import com.hts.web.operations.dao.NearRecommendCityDao;
import com.hts.web.operations.dao.mongo.NearLabelMongoDao;
import com.hts.web.operations.dao.mongo.NearWorldMongoDao;
import com.hts.web.operations.dao.mongo.NearWorldStarMongoDao;
import com.hts.web.operations.service.NearService;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.service.UserConcernService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.ztworld.service.ZTWorldService;

@Service("HTSNearService")
public class NearServiceImpl extends BaseServiceImpl implements NearService {
	
	private static final String DEFAULT_CITY = "北京";
	
	private static final int STAR_WORLD_LIMIT = 6;
	
	private static final float NEAR_LABEL_RADIUS = 0.1f;
	
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
	
	@Autowired
	private BulletinCacheDao bulletinCacheDao;
	
	@Autowired
	private NearRecommendCityDao nearRecommendCityDao;
	
	@Autowired
	private NearRecommendCityCacheDao nearRecommendCityCacheDao;
	
	@Autowired
	private NearLabelMongoDao nearLabelMongoDao;
	
	@Autowired
	private NearLabelWorldDao nearLabelWroldDao;
	
	@Autowired
	private UserConcernService userConcernService;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Override
	public List<OpNearWorldDto> queryNearWorld(float radius, double longitude,
			double latitude, int maxId, int limit) {
		
		if(longitude == 0 && latitude == 0)
			throw new IllegalArgumentException("longitude && latitude can not be null");
		
		List<OpNearWorldDto> starList = null;
		
		if(maxId == 0) {
			starList = worldStarMongoDao.queryNear(longitude, latitude, 
					radius, STAR_WORLD_LIMIT);
		}
		
		final List<OpNearWorldDto> list = worldMongoDao.queryNear(maxId, longitude, latitude, 
				radius, limit);

		// 合并达人和普通织图列表
		if(starList != null && starList.size() > 0) {
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
	public void saveNearWorld(HTWorld world) {
		if(world.getLongitude() == null || world.getLongitude() > 180 || world.getLongitude() < -180
				|| world.getLatitude() == null || world.getLatitude() > 90 || world.getLatitude() < -90)
			return;
		
		OpNearWorldDto near = new OpNearWorldDto();
		near.setLoc(new Double[]{world.getLongitude(), world.getLatitude()});
		Integer serial = keyGenService.generateId(KeyGenServiceImpl.OP_NEAR_WORLD_SERIAL);
		near.setRecommendId(serial);
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
			Double latitude, int maxId, int limit, Map<String, Object> jsonMap,
			Integer commentLimit, Integer likedLimit, Integer userId) throws Exception {
		
		List<OpNearWorldDto> list = null;
		AddrCity city;
		
		if(longitude == null || latitude == null) {
			if(address != null && !"".equals(address.trim())){
				city = cityService.getCityByName(address);
				if(city == null)
					city = cityService.getCityByName(DEFAULT_CITY);
				
				list = queryNearWorld(city.getRadius(), city.getLongitude(), 
						city.getLatitude(), maxId, limit);
			}else{
				throw new IllegalArgumentException("either the address or the longitude and the latitude can not be null ");
			}
		} else {
			city = cityService.getNearCityByLoc(longitude, latitude);
			list = queryNearWorld(city.getRadius(), longitude, 
					latitude, maxId, limit);
		}
		
		worldService.extractLikeComment(commentLimit, likedLimit, list);
		userInfoService.extractVerify(list);
		userConcernService.extractConcernStatus(userId, list);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}


	@Override
	public void buildNearLabel(String address, Double longitude,
			Double latitude, int maxId, int limit, Map<String, Object> jsonMap)
			throws Exception {
		
		List<OpNearLabelDto> list = null;
		
		AddrCity city;
		
		if(longitude == null  || latitude == null) {
			if(address != null && !"".equals(address.trim())){
				city = cityService.getCityByName(address);
				if(city == null)
					city = cityService.getCityByName(DEFAULT_CITY);
				
				list = queryNearLabel(NEAR_LABEL_RADIUS, city.getLongitude(), 
						city.getLatitude(), maxId, limit + 1);
			}else{
				throw new IllegalArgumentException("either the address or the longitude and the latitude can not be null ");
			}
		} else {
			city = cityService.getNearCityByLoc(longitude, latitude);
			list = queryNearLabel(NEAR_LABEL_RADIUS, city.getLongitude(), 
					city.getLatitude(), maxId, limit + 1);
		}
		jsonMap.put(OptResult.JSON_KEY_MSG,list);
	}


	@Override
	public void buildNearBanner(String address, Double longitude, Double latitude,int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		
		List<OpMsgBulletin> list = null;
		AddrCity location = new AddrCity();
		if(longitude == null || latitude == null ){
			if(address != null && !"".equals(address.trim())){
				AddrCity loc = cityService.getCityByName(address);
				if(loc == null){
					throw new NullPointerException(address + " not exists");
				}else{
					list = queryNearBuilletin(loc.getLongitude(),loc.getLatitude(),start,limit);
				}
				location.setName(address);
				location.setLongitude(loc.getLongitude());
				location.setLatitude(loc.getLatitude());
			}else{
				throw new IllegalArgumentException("either the address or the longitude and the latitude can not be null ");
			}
		}else{
			list = queryNearBuilletin(longitude,latitude,start,limit);
			location.setLongitude(longitude);
			location.setLatitude(latitude);
		}
		
		jsonMap.put(OptResult.JSON_KEY_MSG,list);
		jsonMap.put(OptResult.JSON_KEY_LOCATION, location);
	}


	@Override
	public void buildNearLabelWorld(Integer labelId, Integer commentLimit,Integer likedLimit,int maxId, int limit,
			Map<String, Object> jsonMap, Integer userId) throws Exception {
		final List<OpNearLabelWorldDto> list = nearLabelWroldDao.queryNearLabelWorld(labelId, maxId, limit);
		
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
		
		worldService.extractLikeComment(commentLimit, likedLimit, list);
		userInfoService.extractVerify(list);
		userConcernService.extractConcernStatus(userId, list);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}


	@Override
	public List<OpNearLabelDto> queryNearLabel(float radius, double longitude, 
			double latitude, int maxId, int limit) {
		return nearLabelMongoDao.queryNear(maxId, longitude, latitude, radius, limit);
	}


	@Override
	public List<OpMsgBulletin> queryNearBuilletin(double longitude, 
			double latitude, int start, int limit) {
		return bulletinCacheDao.queryBulletin();
	}


	@Override
	public void buildRecommendCity(Map<String,Object>jsonMap) throws Exception {
		List<OpNearCityGroupDto> groupList = nearRecommendCityCacheDao.queryNearRecommendCityCache();
		if(groupList == null || groupList.isEmpty()){
			groupList = nearRecommendCityDao.queryNearCityGroup();
		}
		if(groupList != null){
			for(OpNearCityGroupDto dto:groupList){
				List<AddrCity> cityList = nearRecommendCityDao.queryNearRecommendCityByGroupId(dto.getId());
				if(cityList != null){
					dto.setCities(cityList);
				}else{
					dto.setCities(new ArrayList<AddrCity>());
				}
				
			}
		}
		jsonMap.put(OptResult.JSON_KEY_MSG, groupList);
	}

}
