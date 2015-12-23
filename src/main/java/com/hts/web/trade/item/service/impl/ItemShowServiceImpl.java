package com.hts.web.trade.item.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.util.Log;
import com.hts.web.trade.item.dao.ItemShowDao;
import com.hts.web.trade.item.dto.ItemShow;
import com.hts.web.trade.item.dto.ItemShowDTO;
import com.hts.web.trade.item.service.ItemShowService;

/**
 * 买家秀信息获取
 * @author mishengliang
 *
 */
@Service("com.hts.web.trade.item.service.impl.ItemShowServiceImpl")
public class ItemShowServiceImpl implements ItemShowService {

	@Autowired
	private ItemShowDao itemShowDao; 
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldService zTWorldService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService userInfoService;
	
	//格式转换，丰富买家秀信息
	private List<ItemShowDTO> transFormate(List<ItemShow> list) throws Exception{
		List<ItemShowDTO> listDto = new ArrayList<ItemShowDTO>();
		if (list != null) {
			for(ItemShow itemShow: list){
				ItemShowDTO itemShowDTO = new ItemShowDTO(); 
				//查找world和user的附加信息
				HTWorldDto htWorldDto= zTWorldService.getHTWorldDtoById(itemShow.getWorldId(),true);
				if(htWorldDto != null){
					UserInfo userInfo = userInfoService.getUserInfoById(htWorldDto.getAuthorId());
					itemShowDTO.setUserAvatar(userInfo.getUserAvatar());
					itemShowDTO.setUserName(userInfo.getUserName());
					itemShowDTO.setAddr(htWorldDto.getLocationAddr());
					itemShowDTO.setTitle_thumb_path(htWorldDto.getTitleThumbPath());
					itemShowDTO.setWorldDes(htWorldDto.getWorldDesc());
				}
				
				itemShowDTO.setId(itemShow.getId());
				itemShowDTO.setItemSetId(itemShow.getItemSetId());
				itemShowDTO.setWorldId(itemShow.getWorldId());
				itemShowDTO.setSerial(itemShow.getSerial());
				
				listDto.add(itemShowDTO);
			}
		} else {
			listDto = null;
		}
		
		return listDto;
	} 
	
	/**
	 * 查询指定集合下的买家秀信息
	 */
	@Override
	public void queryItemShowInfo(Integer itemSetId, Map<String, Object> jsonMap) throws Exception {
		List<ItemShow> list = itemShowDao.queryItemShow(itemSetId);
		
		//增加买家秀的附加信息
		List<ItemShowDTO> listDto = transFormate(list);
		
		jsonMap.put(OptResult.ROWS, listDto);
	}

}
