package com.randioo.market_server.entity.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.randioo.market_server.cache.file.CurrencyConfigCache;

public class CurrencyConfig{
	public static final String urlKey="currency.tbl";
	/** currency_id */
	public int currency_id;
	/** currency_name */
	public String currency_name;
	/** currency_mark */
	public String currency_mark;
		
	public static void parse(ByteBuffer buffer){
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		while(buffer.hasRemaining()){
			CurrencyConfig config = new CurrencyConfig();
			config.currency_id=buffer.getInt();
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.currency_name = new String(b);}
			{byte[] b = new byte[buffer.getShort()];buffer.get(b);config.currency_mark = new String(b);}
			
			CurrencyConfigCache.putConfig(config);
		}
	}
}
