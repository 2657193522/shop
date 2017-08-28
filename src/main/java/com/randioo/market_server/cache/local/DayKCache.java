package com.randioo.market_server.cache.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.DaykBO;

public class DayKCache {
	// 根据类型获取天K
	private static Map<String, List<DaykBO>> daykList = new HashMap<>();
	// 得到当天成交单
	private static Map<String, List<TradingBO>> tradingToday = new HashMap<>();

	public static List<DaykBO> getDaykList(String type) {
		return daykList.get(type);
	}

	public static Map<String, List<DaykBO>> getDaykType() {
		return daykList;
	}

	public static Map<String, List<TradingBO>> getTradMapToDay() {
		return tradingToday;
	}

	public static List<TradingBO> getTradingByTypeTo(String type) {
		return tradingToday.get(type);
	}
}