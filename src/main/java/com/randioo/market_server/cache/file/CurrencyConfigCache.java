package com.randioo.market_server.cache.file;

import java.util.HashMap;
import java.util.Map;

import com.randioo.market_server.entity.file.CurrencyConfig;

public class CurrencyConfigCache {
	private static Map<Integer, CurrencyConfig> map = new HashMap<Integer, CurrencyConfig>();

	public static void putConfig(CurrencyConfig config) {
		map.put(config.currency_id, config);
	}

	public static Map<Integer, CurrencyConfig> getCurrencyConfigMap() {
		return map;
	}

	public static CurrencyConfig getConfigById(int id) {
		return map.get(id);
	}

}
