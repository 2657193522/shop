package com.randioo.market_server.entity.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.randioo.market_server.cache.file.TimingConfigCache;

public class TimingConfig{
	public static final String urlKey="timing.tbl";
	/** 星期几 */
	public int id;
	/** 上午开市时间 */
	public String mStartTime;
	/** 上午停市时间 */
	public String mEndTime;
	/** 下午开市时间 */
	public String aStartTime;
	/** 下午停市时间 */
	public String aEndTime;
	/** 周几 */
	public String number;
	/** 判断 */
	public int panduan;
		
	public static void parse(ByteBuffer buffer){
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		while(buffer.hasRemaining()){
			TimingConfig config = new TimingConfig();
			config.id=buffer.getInt();
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.mStartTime = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.mEndTime = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.aStartTime = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.aEndTime = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.number = new String(b);}
			config.panduan=buffer.getInt();
			
			TimingConfigCache.putConfig(config);
		}
	}
}
