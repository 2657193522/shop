package com.randioo.market_server.entity.bo;

import com.randioo.randioo_server_base.db.DataEntity;

public class NumbersBO extends DataEntity {
	private int num_id;// 商品类型数量表唯一Id
	private String num_account;// 拥有用户帐号
	private String num_type;// 商品类型
	private int num_count;// 拥有数量
	private int operation_count;//操作数量
	private String operation_time;//操作时间

	public int getOperation_count() {
		return operation_count;
	}

	public void setOperation_count(int operation_count) {
		this.operation_count = operation_count;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}

	public int getNum_id() {
		return num_id;
	}

	public void setNum_id(int num_id) {
		this.num_id = num_id;
	}

	public String getNum_account() {
		return num_account;
	}

	public void setNum_account(String num_account) {
		this.num_account = num_account;
	}

	public String getNum_type() {
		return num_type;
	}

	public void setNum_type(String num_type) {
		this.num_type = num_type;
	}

	public int getNum_count() {
		return num_count;
	}

	public void setNum_count(int num_count) {
		this.num_count = num_count;
	}

}
