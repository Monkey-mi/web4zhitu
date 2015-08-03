package com.hts.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.hts.web.common.util.Log;
import com.imzhitu.filter.common.dao.ADKeywordCacheDao;

/**
 * 广告关键字列表消息监听
 * 
 * @author lynch
 *
 */
public class AdKeywordMsgListener implements MessageListener {

	
	@Autowired
	private ADKeywordCacheDao keywordCacheDao;
	
	@Override
	public void onMessage(Message msg, byte[] arg1) {
//		byte[] body = msg.getBody();
//		byte[] channel = msg.getChannel();

		// 获取Body一定要用getValueSerializer()方法，因为传递过来的Message
		// msg中有乱码，要用获取value的序列化方法
//		String msgBody = (String) keywordCacheDao.getRedisTemplate().getValueSerializer().deserialize(body);
//		String msgChannel = (String) keywordCacheDao.getRedisTemplate().getStringSerializer().deserialize(channel);

		// 若接收到admin发布的消息，且消息为"admin change the ADKeywordCache"，则证明admin端通知刷新字典
//		if (msgChannel.equals(RedisConstant.subscribeChannel) && msgBody.equals(RedisConstant.subscribeMessage)) {
//			cacheService.refreshADKeywordSet();
//		}
		Log.info("修改关键字列表");
		keywordCacheDao.refreshADKeywordSet();
	}

}
