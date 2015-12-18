package com.hts.web.operations.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.addr.service.CityService;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.AddrCity;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldCount;
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
import com.hts.web.operations.dao.NearLabelWorldUserDao;
import com.hts.web.operations.dao.NearRecommendCityCacheDao;
import com.hts.web.operations.dao.NearRecommendCityDao;
import com.hts.web.operations.dao.mongo.NearBulletinMongoDao;
import com.hts.web.operations.dao.mongo.NearLabelMongoDao;
import com.hts.web.operations.dao.mongo.NearWorldLastMongoDao;
import com.hts.web.operations.dao.mongo.NearWorldMongoDao;
import com.hts.web.operations.dao.mongo.NearWorldStarMongoDao;
import com.hts.web.operations.pojo.NearBulletin;
import com.hts.web.operations.service.NearService;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.service.UserConcernService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.service.ZTWorldService;

@Service("HTSNearService")
public class NearServiceImpl extends BaseServiceImpl implements NearService {
	
	private static final String DEFAULT_CITY = "北京";
	
	private static final float NEAR_LABEL_RADIUS = 0.1f;
	
	@Autowired
	private NearWorldMongoDao worldMongoDao;
	
	@Autowired
	private NearWorldStarMongoDao worldStarMongoDao;
	
	@Autowired
	private NearWorldLastMongoDao worldLastMongoDao;
	
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
	private NearBulletinMongoDao nearBulletinDao;
	
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
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private NearLabelWorldUserDao nearLabelWorldUserDao;
	
	@Override
	public List<OpNearWorldDto> queryNearWorld(AddrCity city, double longitude,
			double latitude, int maxId, int limit) {
		
		if(longitude == 0 && latitude == 0)
			throw new IllegalArgumentException("longitude && latitude can not be null");
		
//		List<OpNearWorldDto> starList = null;
//		
//		if(maxId == 0) {
//			starList = worldStarMongoDao.queryNear(longitude, latitude, 
//					city.getRadius(), STAR_WORLD_LIMIT);
//		}
		
		final List<OpNearWorldDto> list = worldMongoDao.queryNear(maxId, longitude, latitude, 
				city.getRadius(), limit);
		
		// 半径搜索为空,返回这个城市的织图
		if(list.isEmpty())
			list.addAll(worldMongoDao.queryNear(maxId, city.getId(), limit));
		
		// 合并达人和普通织图列表
//		if(starList != null && starList.size() > 0) {
//			Set<Integer> starWids = new HashSet<Integer>();
//			for(int i = 0; i < starList.size(); i++) {
//				starWids.add(starList.get(i).getId());
//			}
//			
//			for(int i = 0; i < list.size(); i++) {
//				if(starWids.contains(list.get(i).getId())) {
//					list.remove(i);
//					--i;
//					continue;
//				}
//			}
//			list.addAll(0, starList);
//		}
		
		// 查询用户信息
		if (!list.isEmpty()) {
			Integer[] ids = new Integer[list.size()];
			final Map<Integer, Integer> idIndexMap = new HashMap<Integer, Integer>();
			final Map<Integer,List<Integer>> indexMap = new HashMap<Integer, List<Integer>>();
			for (int i = 0; i < list.size(); i++) {
				ids[i] = list.get(i).getId();
				idIndexMap.put(list.get(i).getId(), i);
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
			
			worldDao.queryCount(ids, new RowCallback<HTWorldCount>() {

				@Override
				public void callback(HTWorldCount t) {
					Integer idx = idIndexMap.get(t.getId());
					list.get(idx).setClickCount(t.getClickCount());
					list.get(idx).setLikeCount(t.getLikeCount());
					list.get(idx).setCommentCount(t.getCommentCount());
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
		
		AddrCity cityDto = cityService.getCityByName(world.getCity());
		if(cityDto == null)
			cityDto = cityService.getNearCityByLoc(world.getLongitude(), world.getLatitude());
		
		if(cityDto != null) {
			near.setCityId(cityDto.getId());
		}
		
		if(world.getWorldDesc() != null) {
			try {
				near.setWorldDescByte(world.getWorldDesc().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
		near.setWorldURL("http://imzhitu.com/DT" + world.getShortLink());
		
		BeanUtils.copyProperties(world, near);
		worldLastMongoDao.saveWorld(near);
//		worldMongoDao.saveWorld(near);
//		if(userInfoDao.queryStar(world.getAuthorId()) > 0) {
//			worldStarMongoDao.saveWorld(near);
//		}
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
				
				list = queryNearWorld(city, city.getLongitude(), 
						city.getLatitude(), maxId, limit);
			}else{
				throw new IllegalArgumentException("either the address or the longitude and the latitude can not be null ");
			}
		} else {
			city = cityService.getNearCityByLoc(longitude, latitude);
			list = queryNearWorld(city, longitude, 
					latitude, maxId, limit);
		}
		
		worldService.extractLikeComment(userId, commentLimit, likedLimit, list);
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
		
		List<NearBulletin> list = null;
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
			AddrCity city = cityService.getNearCityByLoc(longitude, latitude);
			list = queryNearBuilletin(city.getLongitude(),city.getLatitude(),start,limit);
			location.setLongitude(longitude);
			location.setLatitude(latitude);
		}
		
		if(list != null && !list.isEmpty())
			jsonMap.put(OptResult.JSON_KEY_MSG, list);
		else
			jsonMap.put(OptResult.JSON_KEY_MSG, bulletinCacheDao.queryBulletin());
		
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
		
		worldService.extractLikeComment(userId, commentLimit, likedLimit, list);
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
	public List<NearBulletin> queryNearBuilletin(double longitude, 
			double latitude, int start, int limit) {
		List<NearBulletin> list = nearBulletinDao.queryNear(longitude, latitude, NEAR_LABEL_RADIUS, limit);
		if(list == null || list.isEmpty()) {
			AddrCity defaultCity = cityService.getCityByName("通用");
			list = nearBulletinDao.queryNear(defaultCity.getLongitude(), defaultCity.getLatitude(), NEAR_LABEL_RADIUS, limit);
		} else if(list.size() < 2) {
			AddrCity defaultCity = cityService.getCityByName("通用");
			if(defaultCity != null) {
				List<NearBulletin> defaultList = nearBulletinDao.queryNear(defaultCity.getLongitude(), defaultCity.getLatitude(), 
						NEAR_LABEL_RADIUS, limit - list.size());
				if(defaultList != null && !defaultList.isEmpty())
					list.addAll(defaultList);
			}
		}
		return list;
	}


	@Override
	public void buildRecommendCity(Map<String,Object>jsonMap) throws Exception {
		List<OpNearCityGroupDto> groupList = nearRecommendCityCacheDao.queryNearRecommendCityCache();
		jsonMap.put(OptResult.JSON_KEY_MSG, groupList);
	}

	@Override
	public void updateRecommendCityCache() throws Exception {
		List<OpNearCityGroupDto> groupList = nearRecommendCityDao.queryNearCityGroup();
		if(groupList != null){
			for(OpNearCityGroupDto dto:groupList){
				List<AddrCity> cityList = nearRecommendCityDao.queryNearRecommendCityByGroupId(dto.getId());
				if(cityList != null){
					dto.setCities(cityList);
				}else{
					dto.setCities(new ArrayList<AddrCity>());
				}
			}
			nearRecommendCityCacheDao.updateNearRecommendCityCache(groupList);
		}else{
			throw new NullPointerException("city group is null!");
		}
	}

	@Override
	public List<OpNearWorldDto> queryNearWorldByCityId(Integer cityId,int maxId,int limit)
			throws Exception {
		return worldMongoDao.queryNear(maxId, cityId, limit);
	}

	@Override
	public long queryNearWorldTotalCount(Integer cityId) throws Exception {
		return worldMongoDao.queryNearTotalCount(cityId);
	}

	@Override
	public void insertNearLabelWorldUser(Integer worldId,
			Integer worldAuthorId, Integer nearLabelId) throws Exception {
		Integer id = keyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_WORLD_ID);
		Integer serial = keyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_WORLD_SERIAL);
		nearLabelWorldUserDao.insertNearLabelWorldUser(id, worldId, worldAuthorId, nearLabelId, serial);
	}

}
