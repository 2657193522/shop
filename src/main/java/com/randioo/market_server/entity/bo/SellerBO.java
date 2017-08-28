package com.randioo.market_server.entity.bo;

import com.randioo.randioo_server_base.db.DataEntity;

/**
 * 卖的信息
 * 
 * @author mb
 *
 */
public class SellerBO extends DataEntity {

	private int sell_id;// 委托出售订单号(唯一)

	private String sell_account;// 委托卖家帐号

	public int sell_count;// 出售数量

	private double sell_price;// 出售价格

	private String sell_type;// 出售类型

	private double sell_sum;// 订单总价

	private String sell_time;// 委托时间

	private int sell_overCount;// 成交数量

	private int sell_active;// 单号状态

	public int getSell_id() {
		return sell_id;
	}

	public void setSell_id(int sell_id) {
		this.sell_id = sell_id;
	}

	public String getSell_account() {
		return sell_account;
	}

	public void setSell_account(String sell_account) {
		this.sell_account = sell_account;
	}

	public int getSell_count() {
		return sell_count;
	}

	public void setSell_count(int sell_count) {
		this.sell_count = sell_count;
	}

	public double getSell_price() {
		return sell_price;
	}

	public void setSell_price(double sell_price) {
		this.sell_price = sell_price;
	}

	public String getSell_type() {
		return sell_type;
	}

	public void setSell_type(String sell_type) {
		this.sell_type = sell_type;
	}

	public double getSell_sum() {
		return sell_sum;
	}

	public void setSell_sum(double sell_sum) {
		this.sell_sum = sell_sum;
	}

	public String getSell_time() {
		return sell_time;
	}

	public void setSell_time(String sell_time) {
		this.sell_time = sell_time;
	}

	public int getSell_overCount() {
		return sell_overCount;
	}

	public void setSell_overCount(int sell_overCount) {
		this.sell_overCount = sell_overCount;
	}

	public int getSell_active() {
		return sell_active;
	}

	public void setSell_active(int sell_active) {
		this.sell_active = sell_active;
	}

	@Override
	public String toString() {
		return "SellerBO [sell_id=" + sell_id + ", sell_account=" + sell_account + ", sell_count=" + sell_count
				+ ", sell_price=" + sell_price + ", sell_type=" + sell_type + ", sell_sum=" + sell_sum + ", sell_time="
				+ sell_time + ", sell_overCount=" + sell_overCount + ", sell_active=" + sell_active + "]";
	}

}
