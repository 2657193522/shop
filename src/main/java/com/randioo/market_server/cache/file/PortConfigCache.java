package com.randioo.market_server.cache.file;

import com.randioo.market_server.entity.file.PortConfig;

public class PortConfigCache {
	private static PortConfig portConfig = new PortConfig();

	public static void putConfig(PortConfig config) {
		// TODO Auto-generated method stub
		portConfig.port = config.port;
	}
	
	public static PortConfig getConfig() {
		return portConfig;
	}
}
