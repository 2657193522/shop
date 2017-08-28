package com.randioo.market_server.cache.local.mink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.MinkBO;

public class SixtyMinkCache {
	// 获得60分钟K线图
	private static Map<String, List<MinkBO>> minkList = new HashMap<>();
	// 获得60分钟成交记录
	private static Map<String, List<TradingBO>> tradingList = new HashMap<>();

	// 存储60分钟内的一个成交记录
	private static Map<String, MinkBO> timeMap = new HashMap<>();
	public static Map<String, MinkBO> getMapTime() {
		return timeMap;
	}
	
	public static MinkBO getMapByTime(String timeType) {
		return timeMap.get(timeType);
	}
	public static Map<String, List<MinkBO>> getMinkList() {
		return minkList;
	}

	public static List<MinkBO> getOneMinkList(String type) {
		return minkList.get(type);
	}

	public static Map<String, List<TradingBO>> getTradMap() {
		return tradingList;
	}

	public static List<TradingBO> getTradList(String type) {
		return tradingList.get(type);
	}
}
