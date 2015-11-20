package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldChannelName;
import com.hts.web.common.pojo.HTWorldTextStyle;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldWeekDao;

import net.sf.json.JSONObject;

@Repository("HTSWorldWeekDao")
public class HTWorldWeekDaoImpl extends BaseDaoImpl implements HTWorldWeekDao {
	
	private static String table = HTS.HTWORLD_HTWORLD;
	
	/**
	 * 保存世界
	 */
	private static final String SAVE_WORLD = "insert into " + table 
			+ " (id, short_link, world_name, world_desc, world_label, world_type, type_id, date_added,"
			+ " date_modified,author_id, cover_path, title_path, bg_path, title_thumb_path,channel_name,channel_id," 
			+ "longitude,latitude,location_desc,location_addr, phone_code, province," 
			+ "city, size, child_count,ver,tp, valid, latest_valid, shield, text_style)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	@Autowired
	private HTWorldDao worldDao;

	@Override
	public void saveWorld(HTWorld htworld) {
		String styleStr = null;
		HTWorldTextStyle style = htworld.getTextStyle();
		if(style != null) {
			styleStr = JSONObject.fromObject(style).toString();
		}
		
		List<HTWorldChannelName> channelNames = htworld.getChannelNames();
		String channelName = null;
		String channelId = null;
		if(channelNames != null) {
			String[] names = worldDao.parseChannelNames(channelNames);
			channelName = names[0];
			channelId = names[1];
		}
		getMasterJdbcTemplate().update(SAVE_WORLD, new Object[]{
				htworld.getId(),
				htworld.getShortLink(),
				htworld.getWorldName(),
				htworld.getWorldDesc(),
				htworld.getWorldLabel(),
				htworld.getWorldType(),
				htworld.getTypeId(),
				htworld.getDateAdded(),
				htworld.getDateModified(),
				htworld.getAuthorId(),
				htworld.getCoverPath(),
				htworld.getTitlePath(),
				htworld.getBgPath(),
				htworld.getTitleThumbPath(),
				channelName,
				channelId,
				htworld.getLongitude(),
				htworld.getLatitude(),
				htworld.getLocationDesc(),
				htworld.getLocationAddr(),
				htworld.getPhoneCode(),
				htworld.getProvince(),
				htworld.getCity(),
				htworld.getSize(),
				htworld.getChildCount(),
				htworld.getVer(),
				htworld.getTp(),
				htworld.getValid(),
				htworld.getLatestValid(),
				htworld.getShield(),
				styleStr
		});
	}

}
