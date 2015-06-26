package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelWorldDto;
import com.hts.web.common.util.Log;

public class ChannelServiceTest extends BaseTest {

	private Logger logger = Logger.getLogger(ChannelServiceTest.class);
	
	@Autowired
	private ChannelService service;
	
	@Test
	public void testBuildChannel() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildChannel(jsonMap);
			}
			
		});
	}
	
	@Test
	public void testBuildChannelWorld() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
//				service.buildChannelWorld(11832, 485, 10000, 1, 10, false, 0, 10, 4, jsonMap);
				service.buildChannelWorld(11819, 527, 0, 1, 10, false, 0, 10, 4, jsonMap);
				List<OpChannelWorldDto> list = (List<OpChannelWorldDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
				for(OpChannelWorldDto dto : list) {
					Log.debug(dto.getId() + " : " + dto.getSuperb());
				}
			}
		});
	}
	
	@Test
	public void testBuildSuperbChannelWorld() throws Exception {
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		service.buildChannelWorld(8, 527, 0, 1, 10, false, 0, 10, 4, jsonMap);
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildSuperbChannelWorld(1, 485, 10000, 1, 10, false, 0, 10, 4, jsonMap);
				service.buildSuperbChannelWorld(1, 527, 0, 1, 10, false, 0, 10, 4, jsonMap);
			}
			
		});
	}
	
	@Test
	public void testBuildChannelTopOne() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildChannelTopOne(485, jsonMap);
			}
		});
	}
	
	@Test
	public void buildSubscribeChannelTest() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildSubscribedChannel(true, 485, 0, 1, 10, jsonMap);
				service.buildSubscribedChannel(true, 485, 1000, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void buildChannelAbstractTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildChannelAbstract(11834, 485, jsonMap);
	}
	
	@Test
	public void buildChannelDetailTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildChannelDetail(11834,485, 10, jsonMap);
	}
	
	@Test
	public void saveMemberTest() throws Exception {
		service.saveMember(1, 485);
	}
	
	@Test
	public void deleteMemberTest() throws Exception {
		service.deleteMember(1, 485);
	}
	
	@Test
	public void buildHotTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildHot(1, 10, 485, jsonMap);
		List<OpChannel> list = (List<OpChannel>) jsonMap.get(OptResult.JSON_KEY_CHANNELS);
		for(OpChannel c : list) {
			Log.debug(c.getId() + " : " + c.getChannelName() + " : " + c.getThemeId());
		}
//		logObj(jsonMap);
	}
	
	@Test
	public void saveChannelWorldTest() throws Exception {
		int wid = (int)(10000 * Math.random());
		service.saveChannelWorld(9999, wid, 485, 1, Tag.TRUE);
	}
	
	@Test
	public void saveChannelTest() throws Exception {
//		service.saveChannel(485, "频道名称", null, null, null,
//				"http://imzhitu.qiniudn.com/op/channel/1416212996000.png",
//				"http://imzhitu.qiniudn.com/op/channel/1416212996000.png", 
//				Tag.CHANNEL_TYPE_NORMAL, null, Tag.TRUE, Tag.TRUE, Tag.TRUE);
	}
	
	
	@Test
	public void buildSysDanmuTest() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
//				service.buildSysDanmu(11575, 485, 0, 1, 10, jsonMap);
				service.buildSysDanmu(11575, 485, 1000, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void buildLinkChannelTest() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildLinkChannel(11579, jsonMap);
			}
		});
	}

	@Test
	public void buildThemeChannelTest() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildThemeChannel(10000, 0, 1, 10, jsonMap);
				service.buildThemeChannel(10000, 1000, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void buildUnValidChannelWorldTest() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildUnValidChannelWorld(11819, 485, 0, 
						1, 10, false, 0, 10, 4, jsonMap);
			}
		});
	}
	
}
