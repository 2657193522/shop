package com.randioo.market_server.cache.local;

public class StateCache {
	// 5分线变化
	private static boolean panduan;
	// 1分线变化
	private static boolean onePanduan;
	// 15分线变化
	private static boolean fifPanduan;
	// 30分线变化
	private static boolean thirtyPanduan;
	// 60分线变化
	private static boolean sixtyPanduan;

	public static boolean isPanduan() {
		return panduan;
	}

	public static void setPanduan(boolean panduan) {
		StateCache.panduan = panduan;
	}

	public static boolean isOnePanduan() {
		return onePanduan;
	}

	public static void setOnePanduan(boolean onePanduan) {
		StateCache.onePanduan = onePanduan;
	}

	public static boolean isFifPanduan() {
		return fifPanduan;
	}

	public static void setFifPanduan(boolean fifPanduan) {
		StateCache.fifPanduan = fifPanduan;
	}

	public static boolean isThirtyPanduan() {
		return thirtyPanduan;
	}

	public static void setThirtyPanduan(boolean thirtyPanduan) {
		StateCache.thirtyPanduan = thirtyPanduan;
	}

	public static boolean isSixtyPanduan() {
		return sixtyPanduan;
	}

	public static void setSixtyPanduan(boolean sixtyPanduan) {
		StateCache.sixtyPanduan = sixtyPanduan;
	}

}
