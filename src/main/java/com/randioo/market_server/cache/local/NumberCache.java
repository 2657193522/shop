package com.randioo.market_server.cache.local;

import java.util.HashMap;
import java.util.Map;

import com.randioo.market_server.entity.bo.NumbersBO;

public class NumberCache {
	//根据用户查找卖的用户类型信息
	private static Map<String, NumbersBO> numberMap = new HashMap<>();

	public static NumbersBO getNumber(String typeAccount) {
		return numberMap.get(typeAccount);
	}

	public static Map<String, NumbersBO> getNumberMap() {
		return numberMap;
	}
}
