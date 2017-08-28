package com.randioo.market_server.cache.file;

import com.randioo.market_server.entity.file.CountConfig;

public class CountConfigCache {
	private static CountConfig countConfig = new CountConfig();

	public static void putConfig(CountConfig config) {
		countConfig.id = config.id;
		countConfig.count = config.count;
		countConfig.user = config.user;
	}

	public static CountConfig getConfig() {
		return countConfig;
	}
}
