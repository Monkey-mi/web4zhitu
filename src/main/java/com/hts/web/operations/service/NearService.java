package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.AddrCity;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.hts.web.common.pojo.OpNearWorldDto;
import com.hts.web.common.service.BaseService;
import com.hts.web.operations.pojo.NearBulletin;

/**
 * 附近的织图数据访问接口
 * 
 * @author lynch 2015-12-01
 *
 */
public interface NearService extends BaseService {
	
	/**
	 * 查询附近织图
	 * @param address 地址一般指城市。若为空，则经纬度不能为空
	 * @param longitude 经度，和纬度成对出现。若为空，则address不能为空
	 * @param latitude 纬度，和经度成对出现。若为空，则address不能为空
	 * @param maxId
	 * @param limit
	 * @param jsonMap
	 * @param commentLimit
	 * @param likedLimit
	 * @param userId
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:08:13
	 * @author lynch 2015-12-07
	 */
	public void buildNearWorld(Integer districtId,String address,Double longitude, Double latitude,
			int maxId, int limit,Map<String,Object>jsonMap,Integer commentLimit,
			Integer likedLimit, Integer userId)throws Exception;
	
	/**
	 * 查询附近标签
	 * @param address 地址一般指城市。若为空，则经纬度不能为空
	 * @param longitude 经度，和纬度成对出现。若为空，则address不能为空
	 * @param latitude 纬度，和经度成对出现。若为空，则address不能为空
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:10:08
	 */
	public void buildNearLabel(String address,Double longitude, Double latitude,
			int maxId, int limit,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 查询附近的banner
	 * @param address  地址一般指城市。
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:11:02
	 */
	public void buildNearBanner(String address,Double longitude, Double latitude,
			int start, int limit,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 查询附近标签对应的织图
	 * @param labelId 附近标签id
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param userId
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:11:29
	 * @author lynch 2015-12-07
	 */
	public void buildNearLabelWorld(Integer labelId,Integer commentLimit,Integer likedLimit,
			int maxId, int limit,Map<String,Object> jsonMap, Integer userId)throws Exception;
	
	/**
	 * 根据经纬度和搜索城市附近的织图
	 * 
	 * @param city 搜索的城市
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param maxId 起始id,0表示第一页
	 * @param limit 每页限定记录数量
	 * @return 瀑布流织图列表(包含用户信息)
	 * @return
	 * @author lynch 2015-12-03
	 */
	public List<OpNearWorldDto> queryNearWorld(AddrCity city, double longitude, double latitude, 
			int maxId, int limit);

	/**
	 * 保存用户所发的最新附近织图
	 * 
	 * @param world 织图信息
	 * @author lynch 2015-12-01
	 * @modified by zxx 2015-12-30 10:19:46
	 */
	public void saveNearWorldLast(HTWorld world);
	
	/**
	 * 保存附近的织图
	 * 
	 * @param world 织图信息
	 * @author zxx 2015-12-30 10:20:00
	 */
	public void saveNearWorld(HTWorld world);
	
	/**
	 * 根据id删除附近的织图
	 * 
	 * @param id
	 * @author lynch 2015-12-01
	 */
	public void deleteNearWorld(Integer id);
	
	
	/**
	 * 根据经纬度查询附近的标签
	 * 
	 * @param radius
	 * @param longitude
	 * @param latitude
	 * @param maxId
	 * @param limit
	 * @return
	 * @author lynch 2015-12-02
	 */
	public List<OpNearLabelDto> queryNearLabel(float radius, double longitude, 
			double latitude, int maxId, int limit);
	
	
	/**
	 * 查询附近的公告
	 * 
	 * @param longitude
	 * @param latitude
	 * @param start
	 * @param limit
	 * @return
	 * @author lynch 2015-12-02
	 */
	public List<NearBulletin> queryNearBuilletin(double longitude, 
			double latitude, int start, int limit);
	
	/**
	 * 查询附近的推荐城市
	 * @throws Exception
	 * @author zxx 2015-12-3 17:16:18
	 */
	public void buildRecommendCity(Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 更新附近推荐城市缓存
	 * @throws Exception
	 * @author zxx 2015-12-8 12:28:56
	 */
	public void updateRecommendCityCache()throws Exception;
	
	/**
	 * 根据城市id查询附近织图，For admin
	 * @param cityId
	 * @return
	 * @throws Exception
	 * @author zxx 2015-12-8 19:47:56
	 */
	public List<OpNearWorldDto> queryNearWorldByCityId(Integer cityId,int maxId,int limit)throws Exception;
	
	/**
	 * 根据城市id查询附近织图总数。 For admin
	 * @param cityId
	 * @return
	 * @throws Exception
	 * @author zxx 2015-12-8 20:21:27
	 */
	public long queryNearWorldTotalCount(Integer cityId)throws Exception;
	
	/**
	 * 添加织图到用户所发的附近标签织图里面去
	 * @param worldId
	 * @param worldAuthorId
	 * @param nearLabelId
	 * @throws Exception
	 * @author zxx 2015-12-17 10:01:19
	 */
	public void insertNearLabelWorldUser(Integer worldId,Integer worldAuthorId,Integer nearLabelId)throws Exception;
	
	/**
	 * 根据织图id删除用户所发的附近标签织图
	 * @param worldId
	 * @throws Exception
	 * @author zxx 2015-12-17 10:01:19
	 */
	public void deleteNearLabelWorldUserByWorldIdAndLabelId(Integer worldId,Integer nearLabelId)throws Exception;
	
	/**
	 * 根据织图id删除用户所发的附近标签织图
	 * @param worldId
	 * @throws Exception
	 * @author zxx 2015-12-17 10:01:19
	 */
	public void deleteNearLabelWorldUserByWorldId(Integer worldId)throws Exception;
	
	/**
	 * 根据地理位置获取附近推荐城市
	 * @param cityName
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author zxx 2015-12-18 15:41:31
	 */
	public AddrCity getNearRecommendCity(String cityName,Double longitude,Double latitude,Boolean isReturnCacheLocation);
	
}
