package com.randioo.market_server.entity.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.randioo.market_server.cache.file.CountConfigCache;

public class CountConfig{
	public static final String urlKey="count.tbl";
	/** id */
	public int id;
	/** 交易数量 */
	public int count;
	/** 用户 */
	public String user;
		
	public static void parse(ByteBuffer buffer){
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		while(buffer.hasRemaining()){
			CountConfig config = new CountConfig();
			config.id=buffer.getInt();
			config.count=buffer.getInt();
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.user = new String(b);}
			
			CountConfigCache.putConfig(config);
		}
	}
}
