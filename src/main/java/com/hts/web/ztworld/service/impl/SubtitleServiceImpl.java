package com.hts.web.ztworld.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldSubtitleDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.ztworld.dao.SubtitleCacheDao;
import com.hts.web.ztworld.dao.SubtitleDao;
import com.hts.web.ztworld.service.SubtitleService;

@Service("HTSubtitleServie")
public class SubtitleServiceImpl extends BaseServiceImpl implements
		SubtitleService {

	@Autowired
	private SubtitleCacheDao subtitleCacheDao;
	
	@Autowired
	private SubtitleDao subtitleDao;
	
	@Override
	public void buildSubtitle(Integer maxId, Integer start, Integer limit, 
			Map<String, Object> jsonMap)
			throws Exception {
		
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldSubtitleDto>(){

					@Override
					public List<HTWorldSubtitleDto> getSerializables(
							RowSelection rowSelection) {
						return subtitleCacheDao.querySubtitle();
					}

					@Override
					public List<HTWorldSubtitleDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						return subtitleDao.querySubtitleDto(maxId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}

		},OptResult.JSON_KEY_TITLE, null);
	}

}
