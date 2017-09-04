package com.randioo.market_server.entity.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.randioo.market_server.cache.file.PortConfigCache;

public class PortConfig{
	public static final String urlKey="port.tbl";
	/** 端口号 */
	public int port;
		
	public static void parse(ByteBuffer buffer){
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		while(buffer.hasRemaining()){
			PortConfig config = new PortConfig();
			config.port=buffer.getInt();
			
			PortConfigCache.putConfig(config);
		}
	}
}
