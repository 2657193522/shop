package com.randioo.market_server.module;

public class Constant {
	/** 服务器关闭 */
	public static final String CLOSE = "Server close";
	/** 服务器开启 */
	public static final String START = "Server open";
	/** 今日不开盘 */
	public static final String CLOSE_EVER = "0";
	/** 休市状态 */
	public static final String FREE_TIME = "Free time";
	/** 修复状态 */
	public static final String REPQIR_TIME = "repair time";
	/** 商品开始售卖 */
	public static final int START_SELL = 1;// 开始售卖
	/** 商品停止售卖 */
	public static final int STOP_SELL = 0;// 停止售卖
	/** 交易生成 */
	public static final String NOTICE = "Trading generates";
	/** 没有数据 */
	public static final String NOBASE = "NOBASE";// 没有数据
	/** 撤销成功 */
	public static final String FREE_BACK = "Revoke success";
	/**撤销失败*/
	public static final String FREE_FAIL = "Revoke fail";
	/** 是为1 正在出售中*/
	public static final int YES = 1;
	/** 否为0  全部已成交 */
	public static final int NO = 0;
	/** 用户撤单*/
	public static final int SOME_SELL=2;//用户撤单
	/**系统撤单*/
	public static final int SYSTEM_RRVOKE=3;//系统撤单
}
