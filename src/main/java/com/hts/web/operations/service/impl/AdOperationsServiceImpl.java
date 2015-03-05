package com.hts.web.operations.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.OpAdAppLink;
import com.hts.web.common.pojo.OpAdAppLinkDto;
import com.hts.web.common.pojo.OpAdAppLinkRecord;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.AppLinkDao;
import com.hts.web.operations.dao.AppLinkRecordDao;
import com.hts.web.operations.service.AdOperationsService;

@Service("HTSAdOperationsService")
public class AdOperationsServiceImpl extends BaseServiceImpl implements
		AdOperationsService {
	
	/**
	 * APP链接访问前缀_NO
	 */
	public static final String APP_LINK_PREFIX_APP = "APP";
	
	@Autowired
	private AppLinkDao appLinkDao;
	
	@Autowired
	private AppLinkRecordDao appLinkRecordDao;
	
	@Override
	public String addAppLinkRecordFromURL(String url, String ip) throws Exception {
		String link = null;
		String shortLink = parseShortLinkFromURL(url);
		OpAdAppLink appLink = appLinkDao.queryIdByShortLink(shortLink);
		if(appLink != null) {
			appLinkDao.addClickCount(appLink.getId());
			appLinkRecordDao.saveRecord(new OpAdAppLinkRecord(ip, new Date(), appLink.getId()));
			return appLink.getAppLink();
		}
		return link;
	}

	/**
	 * 从URL解析短链或id
	 * 
	 * @param url
	 * @return
	 */
	public String parseShortLinkFromURL(String url) {
		
		String prefix = null;
		if(url.contains(APP_LINK_PREFIX_APP)) {
			prefix = APP_LINK_PREFIX_APP;
		} else {
			return null;
		}
		
		Integer index = url.indexOf(prefix);
		String shortLink = url.substring(index + 3, url.length()).trim();
		return StringUtil.checkIsNULL(shortLink) ? null : shortLink;
	}
	
	@Override
	public String getAppURLByUserAgent(String userAgent, OpAdAppLink link) {
		return null;
	}
	
	@Override
	public void buildAppLink(final Integer phoneCode, int maxSerial, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxSerial, start, limit, jsonMap, new SerializableListAdapter<OpAdAppLinkDto>() {

			@Override
			public List<OpAdAppLinkDto> getSerializables(
					RowSelection rowSelection) {
				return appLinkDao.queryAppLinkDto(Tag.FALSE, phoneCode, rowSelection);
			}

			@Override
			public List<OpAdAppLinkDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return appLinkDao.queryAppLinkDto(maxId, Tag.FALSE, phoneCode, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return appLinkDao.queryAppLinkCount(maxId, Tag.FALSE, phoneCode);
			}
		}, OptResult.JSON_KEY_APP, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
}
