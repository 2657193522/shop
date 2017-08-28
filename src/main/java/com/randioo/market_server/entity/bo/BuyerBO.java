package com.randioo.market_server.entity.bo;

import com.randioo.randioo_server_base.db.DataEntity;

public class BuyerBO extends DataEntity {

	private int buy_id;//订单号
	
	private String buy_account;//购买人Id
	
	private double buy_price;// 购买单价
	
	private int buy_count;//购买数量
	
	private	String buy_type;//购买类型
	
	private String buy_time;//生成时间
	public int getBuy_id() {
		return buy_id;
	}
	public void setBuy_id(int buy_id) {
		this.buy_id = buy_id;
	}
	public String getBuy_account() {
		return buy_account;
	}
	public void setBuy_account(String buy_account) {
		this.buy_account = buy_account;
	}
	public double getBuy_price() {
		return buy_price;
	}
	public void setBuy_price(double buy_price) {
		this.buy_price = buy_price;
	}
	public int getBuy_count() {
		return buy_count;
	}
	public void setBuy_count(int buy_count) {
		this.buy_count = buy_count;
	}
	public String getBuy_type() {
		return buy_type;
	}
	public void setBuy_type(String buy_type) {
		this.buy_type = buy_type;
	}
	public String getBuy_time() {
		return buy_time;
	}
	public void setBuy_time(String buy_time) {
		this.buy_time = buy_time;
	}
	
	
	
	
}
