package com.randioo.market_server.cache.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randioo.market_server.entity.bo.TradingBO;

public class TradingCache {

	private static Map<String, List<TradingBO>> tradingList = new HashMap<>();

	public static List<TradingBO> getTradingList(String state) {
		return tradingList.get(state);
	}

	public static Map<String, List<TradingBO>> getTradingMap() {
		return tradingList;
	}

}
