package com.randioo.market_server.entity.bo;

import com.randioo.randioo_server_base.db.DataEntity;

public class RecordBO extends DataEntity {

	/** 记录id */
	private int record_id;
	/** 开始金额 */
	private double start_rmb;
	/** 结束金额 */
	private double end_rmb;
	/** kaishi */
	private int start_count;
	/***/
	private int end_count;
	private String time;
	private int count;
	private double rmb;
	private String type;
	private String account;
	private int state;
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRecord_id() {
		return record_id;
	}

	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	public double getStart_rmb() {
		return start_rmb;
	}

	public void setStart_rmb(double start_rmb) {
		this.start_rmb = start_rmb;
	}

	public double getEnd_rmb() {
		return end_rmb;
	}

	public void setEnd_rmb(double end_rmb) {
		this.end_rmb = end_rmb;
	}

	public int getStart_count() {
		return start_count;
	}

	public void setStart_count(int start_count) {
		this.start_count = start_count;
	}

	public int getEnd_count() {
		return end_count;
	}

	public void setEnd_count(int end_count) {
		this.end_count = end_count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getRmb() {
		return rmb;
	}

	public void setRmb(double rmb) {
		this.rmb = rmb;
	}

}
