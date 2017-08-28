package com.randioo.market_server.cache.file;

import java.util.HashMap;
import java.util.Map;

import com.randioo.market_server.entity.file.TimingConfig;

public class TimingConfigCache {
	private static Map<Integer, TimingConfig> map = new HashMap<Integer, TimingConfig>();

	public static void putConfig(TimingConfig config) {
		map.put(config.id, config);
	}

	public static Map<Integer, TimingConfig> getTimingMap() {
		return map;
	}

	public static TimingConfig getConfigById(int id) {
		return map.get(id);
	}

}
