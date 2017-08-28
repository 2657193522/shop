package com.randioo.market_server.entity.bo;

import org.springframework.stereotype.Component;

@Component
public class Role extends GameRole {

	private double role_rmbA;// 用户余额

	private String passWord;
	private boolean judge;
	private int vip_level;

	public int getVip_level() {
		return vip_level;
	}

	public void setVip_level(int vip_level) {
		this.vip_level = vip_level;
	}


	public boolean isJudge() {
		return judge;
	}

	public void setJudge(boolean judge) {
		setChange(true);
		this.judge = judge;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public double getRole_rmbA() {
		return role_rmbA;
	}

	public void setRole_rmbA(double role_rmbA) {
		this.role_rmbA = role_rmbA;
	}

}
