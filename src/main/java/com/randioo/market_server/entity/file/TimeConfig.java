package com.randioo.market_server.entity.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.randioo.market_server.cache.file.TimeConfigCache;

public class TimeConfig{
	public static final String urlKey="time.tbl";
	/** 挂单定时 */
	public String seller;
		
	public static void parse(ByteBuffer buffer){
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		while(buffer.hasRemaining()){
			TimeConfig config = new TimeConfig();
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.seller = new String(b);}
			
			TimeConfigCache.putConfig(config);
		}
	}
}
