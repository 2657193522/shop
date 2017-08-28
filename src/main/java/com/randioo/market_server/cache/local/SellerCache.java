package com.randioo.market_server.cache.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randioo.market_server.entity.bo.SellerBO;

public class SellerCache {

	// 从类型状态查找卖的信息
	private static Map<String, List<SellerBO>> sellerMapByType = new HashMap<>();
	// 卖的ID
	private static Map<String, List<SellerBO>> sellerMapById = new HashMap<>();
	// 每个人的出售信息
	private static Map<String, List<SellerBO>> sellerMapByAccount = new HashMap<>();

	public static List<SellerBO> getSellerListByAccount(String account) {
		return sellerMapByAccount.get(account);
	}

	public static Map<String, List<SellerBO>> getSellerMapByAccount() {
		return sellerMapByAccount;
	}

	public static List<SellerBO> getListByType(String types) {
		return sellerMapByType.get(types);
	}

	public static Map<String, List<SellerBO>> getSellerByType() {
		return sellerMapByType;
	}

	public static Map<String, List<SellerBO>> getSellerByAll() {
		return sellerMapById;
	}

	public static List<SellerBO> getListByAll(String sellId) {
		return sellerMapById.get(sellId);
	}

}
