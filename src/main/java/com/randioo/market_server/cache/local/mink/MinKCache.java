package com.randioo.market_server.cache.local.mink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.MinkBO;

public class MinKCache {

	// 获得当天的5Kxiantu
	private static Map<String, List<MinkBO>> minkList = new HashMap<>();

	// 存储5分钟内的一个成交记录
	private static Map<String, MinkBO> timeMap = new HashMap<>();
	public static Map<String, MinkBO> getMapTime() {
		return timeMap;
	}
	
	public static MinkBO getMapByTime(String timeType) {
		return timeMap.get(timeType);
	}

	// 得到五分钟成交量
	private static Map<String, List<TradingBO>> tradingList = new HashMap<>();

	public static Map<String, List<TradingBO>> getTradListFive() {
		return tradingList;
	}

	public static List<TradingBO> getTradListFiveType(String type) {
		return tradingList.get(type);
	}


	public static List<MinkBO> getMinkList(String type) {
		return minkList.get(type);
	}

	public static Map<String, List<MinkBO>> getMapType() {
		return minkList;
	}
}
