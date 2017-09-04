package com.randioo.market_server.entity.bo_remote;

import com.randioo.randioo_server_base.db.DataEntity;

public class CurencyUser extends DataEntity {
	private int cu_id;
	private int member_id;//用户id
	private int currency_id;//类型
	private int num;//数量
	private int forzen_num;
	
	
	public int getCu_id() {
		return cu_id;
	}
	public void setCu_id(int cu_id) {
		this.cu_id = cu_id;
	}
	public int getForzen_num() {
		return forzen_num;
	}
	public void setForzen_num(int forzen_num) {
		this.forzen_num = forzen_num;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public int getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(int currency_id) {
		this.currency_id = currency_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "CurencyUser [member_id=" + member_id + ", currency_id=" + currency_id + ", num=" + num + "]";
	}

	
}
