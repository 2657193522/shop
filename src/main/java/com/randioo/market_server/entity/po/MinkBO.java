package com.randioo.market_server.entity.po;

import com.randioo.randioo_server_base.db.DataEntity;

public class MinkBO extends DataEntity {
	/** 唯一Id */
	private int min_id;
	/** 最高价格 */
	private double min_max_price;
	/** 最低价格 */
	private double min_min_price;
	/** 总出售额 */
	private double min_sum_price;
	/** 总出售量 */
	private int min_sum_count;
	/** 涨幅 */
	private double min_percentage;
	/** 开盘价格 */
	private double min_start_price;
	/** 收盘价格 */
	private double min_end_price;
	/** 日期 */
	private String min_time;
	
	private String min_type;
	
	public String getMin_type() {
		return min_type;
	}
	public void setMin_type(String min_type) {
		this.min_type = min_type;
	}
	public int getMin_id() {
		return min_id;
	}
	public void setMin_id(int min_id) {
		this.min_id = min_id;
	}
	public double getMin_max_price() {
		return min_max_price;
	}
	public void setMin_max_price(double min_max_price) {
		this.min_max_price = min_max_price;
	}
	public double getMin_min_price() {
		return min_min_price;
	}
	public void setMin_min_price(double min_min_price) {
		this.min_min_price = min_min_price;
	}
	public double getMin_sum_price() {
		return min_sum_price;
	}
	public void setMin_sum_price(double min_sum_price) {
		this.min_sum_price = min_sum_price;
	}
	public int getMin_sum_count() {
		return min_sum_count;
	}
	public void setMin_sum_count(int min_sum_count) {
		this.min_sum_count = min_sum_count;
	}
	public double getMin_percentage() {
		return min_percentage;
	}
	public void setMin_percentage(double min_percentage) {
		this.min_percentage = min_percentage;
	}
	public double getMin_start_price() {
		return min_start_price;
	}
	public void setMin_start_price(double min_start_price) {
		this.min_start_price = min_start_price;
	}
	public double getMin_end_price() {
		return min_end_price;
	}
	public void setMin_end_price(double min_end_price) {
		this.min_end_price = min_end_price;
	}
	public String getMin_time() {
		return min_time;
	}
	public void setMin_time(String min_time) {
		this.min_time = min_time;
	}

	

}
