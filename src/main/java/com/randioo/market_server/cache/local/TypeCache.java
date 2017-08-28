package com.randioo.market_server.cache.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeCache {
	private static Map<String, String> getType = new HashMap<>();
	private static List<String> strings = new ArrayList<>();

	public static String getType(String type) {
		return getType.get(type);
	}

	public static Map<String, String> getTypeMap() {
		return getType;
	}

	public static List<String> getTypeList() {
		return strings;
	}
}
