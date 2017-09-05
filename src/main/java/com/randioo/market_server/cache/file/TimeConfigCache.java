package com.randioo.market_server.cache.file;

import com.randioo.market_server.entity.file.TimeConfig;

public class TimeConfigCache {
	private static TimeConfig timeConfig = new TimeConfig();

	public static void putConfig(TimeConfig config) {
		// TODO Auto-generated method stub
		timeConfig.seller = config.seller;
	}

	public static TimeConfig getConfig() {
		return timeConfig;
	}
}
