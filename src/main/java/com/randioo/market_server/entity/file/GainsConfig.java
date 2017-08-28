package com.randioo.market_server.entity.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.randioo.market_server.cache.file.GainsConfigCache;

public class GainsConfig{
	public static final String urlKey="gaining.tbl";
	/** 涨幅率 */
	public String addGains;
	/** 跌幅率 */
	public String downGains;
	/** poundage */
	public String poundage;
		
	public static void parse(ByteBuffer buffer){
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		while(buffer.hasRemaining()){
			GainsConfig config = new GainsConfig();
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.addGains = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.downGains = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.poundage = new String(b);}
			
			GainsConfigCache.putConfig(config);
		}
	}
}
