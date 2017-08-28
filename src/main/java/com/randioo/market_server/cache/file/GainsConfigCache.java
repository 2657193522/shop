package com.randioo.market_server.cache.file;

import com.randioo.market_server.entity.file.GainsConfig;

public class GainsConfigCache {
	private static GainsConfig gainsConfig = new GainsConfig();

	public static void putConfig(GainsConfig config) {
		gainsConfig.addGains = Integer.parseInt(config.addGains) / 1000.0 + "";
		gainsConfig.downGains = Integer.parseInt(config.downGains) / 1000.0 + "";
		gainsConfig.poundage = Integer.parseInt(config.poundage) / 1000.0 + "";
	}

	public static GainsConfig getConfig() {
		return gainsConfig;
	}
}
