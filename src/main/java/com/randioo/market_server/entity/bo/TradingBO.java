package com.randioo.market_server.entity.bo;

import com.randioo.randioo_server_base.db.DataEntity;

public class TradingBO extends DataEntity {
	private int trad_id;// 成交单号(唯一)

	private int trad_buy_id;// 订单号

	private String trad_account;// 购买的用户

	private double trad_price;// 成交价格

	private String trad_type;// 成交商品类型

	private int trad_count;// 成交数量

	private double trad_sum;// 成交总金额

	private String trad_time;// 成交时间

	private double trad_poundage;// 手续费

	private int trad_state;
	
	private double trad_before_rmb;//交易前余额
	
	private double trad_after_rmb;//交易后余额
	
	private int trad_before_count;//交易前数量
	
	private int trad_after_count;//交易后数量

	
	public double getTrad_before_rmb() {
		return trad_before_rmb;
	}

	public void setTrad_before_rmb(double trad_before_rmb) {
		this.trad_before_rmb = trad_before_rmb;
	}

	public double getTrad_after_rmb() {
		return trad_after_rmb;
	}

	public void setTrad_after_rmb(double trad_after_rmb) {
		this.trad_after_rmb = trad_after_rmb;
	}

	public int getTrad_before_count() {
		return trad_before_count;
	}

	public void setTrad_before_count(int trad_before_count) {
		this.trad_before_count = trad_before_count;
	}

	public int getTrad_after_count() {
		return trad_after_count;
	}

	public void setTrad_after_count(int trad_after_count) {
		this.trad_after_count = trad_after_count;
	}

	public int getTrad_id() {
		return trad_id;
	}

	public void setTrad_id(int trad_id) {
		this.trad_id = trad_id;
	}

	public int getTrad_buy_id() {
		return trad_buy_id;
	}

	public void setTrad_buy_id(int trad_buy_id) {
		this.trad_buy_id = trad_buy_id;
	}

	public String getTrad_account() {
		return trad_account;
	}

	public void setTrad_account(String trad_account) {
		this.trad_account = trad_account;
	}

	public double getTrad_price() {
		return trad_price;
	}

	public void setTrad_price(double trad_price) {
		this.trad_price = trad_price;
	}

	public String getTrad_type() {
		return trad_type;
	}

	public void setTrad_type(String trad_type) {
		this.trad_type = trad_type;
	}

	public int getTrad_count() {
		return trad_count;
	}

	public void setTrad_count(int trad_count) {
		this.trad_count = trad_count;
	}

	public double getTrad_sum() {
		return trad_sum;
	}

	public void setTrad_sum(double trad_sum) {
		this.trad_sum = trad_sum;
	}

	public String getTrad_time() {
		return trad_time;
	}

	public void setTrad_time(String trad_time) {
		this.trad_time = trad_time;
	}

	public double getTrad_poundage() {
		return trad_poundage;
	}

	public void setTrad_poundage(double trad_poundage) {
		this.trad_poundage = trad_poundage;
	}

	public int getTrad_state() {
		return trad_state;
	}

	public void setTrad_state(int trad_state) {
		this.trad_state = trad_state;
	}
	
}
