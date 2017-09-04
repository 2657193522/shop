package com.randioo.market_server.entity.bo_remote;

import com.randioo.randioo_server_base.db.DataEntity;

public class Member extends DataEntity{
	private int member_id;//用户Id
	private double rmb;//用户金额
	private String pwdtrade;//交易密码
	private int vip_level;//用户等级
	
	public String getPwdtrade() {
		return pwdtrade;
	}
	public void setPwdtrade(String pwdtrade) {
		this.pwdtrade = pwdtrade;
	}
	public int getVip_level() {
		return vip_level;
	}
	public void setVip_level(int vip_level) {
		this.vip_level = vip_level;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public double getRmb() {
		return rmb;
	}
	public void setRmb(double rmb) {
		this.rmb = rmb;
		
	}
	

}
