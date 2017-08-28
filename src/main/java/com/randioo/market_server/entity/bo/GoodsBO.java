package com.randioo.market_server.entity.bo;

import com.randioo.randioo_server_base.db.DataEntity;

public class GoodsBO extends DataEntity {
	private int goods_id;// 商品 唯一Id

	private String goods_account;// 商品归属用户帐号

	private int goods_num;// 商品 数量

	private String goods_type;// 商品 类型

	private double goods_price;// 商品 价格

	private String goods_time;// 商品操作时间

	private int goods_sell_id;// 商品出售的卖的订单号

	private int goods_active;// 商品当前状态

	private int goods_trad_id;// 商品成交单号

	public int getGoods_active() {
		return goods_active;
	}

	public void setGoods_active(int goods_active) {
		this.goods_active = goods_active;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_account() {
		return goods_account;
	}

	public void setGoods_account(String goods_account) {
		this.goods_account = goods_account;
	}

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public double getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_time() {
		return goods_time;
	}

	public void setGoods_time(String goods_time) {
		this.goods_time = goods_time;
	}

	public int getGoods_sell_id() {
		return goods_sell_id;
	}

	public void setGoods_sell_id(int goods_sell_id) {
		this.goods_sell_id = goods_sell_id;
	}

	public int getGoods_trad_id() {
		return goods_trad_id;
	}

	public void setGoods_trad_id(int goods_trad_id) {
		this.goods_trad_id = goods_trad_id;
	}

}
