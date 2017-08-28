package com.randioo.market_server.entity.bo_remote;

import com.randioo.randioo_server_base.db.DataEntity;

public class Currency extends DataEntity{
	private int currency_id;//类型Id
	private String currency_name;//总姓
	public int getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(int currency_id) {
		this.currency_id = currency_id;
	}
	public String getCurrency_name() {
		return currency_name;
	}
	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}
	
	
	
	
	
}
