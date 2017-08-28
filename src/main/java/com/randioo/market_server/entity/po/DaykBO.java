package com.randioo.market_server.entity.po;

import com.randioo.randioo_server_base.db.DataEntity;

public class DaykBO  extends DataEntity{
	/** 唯一Id */
	private int day_id;
	/** 最高价格 */
	private double day_max_price;
	/** 最低价格 */
	private double day_min_price;
	/** 总出售额 */
	private double day_sum_price;
	/** 总出售量 */
	private int day_sum_count;
	/** 涨幅 */
	private double day_percentage;
	/** 开盘价格 */
	private double day_start_price;
	/** 收盘价格 */
	private double day_end_price;
	/** 日期 */
	private String day_time;
	private String day_type;
	
	public String getDay_type() {
		return day_type;
	}
	public void setDay_type(String day_type) {
		this.day_type = day_type;
	}
	public int getDay_id() {
		return day_id;
	}
	public void setDay_id(int day_id) {
		this.day_id = day_id;
	}
	public double getDay_max_price() {
		return day_max_price;
	}
	public void setDay_max_price(double day_max_price) {
		this.day_max_price = day_max_price;
	}
	public double getDay_min_price() {
		return day_min_price;
	}
	public void setDay_min_price(double day_min_price) {
		this.day_min_price = day_min_price;
	}
	public double getDay_sum_price() {
		return day_sum_price;
	}
	public void setDay_sum_price(double day_sum_price) {
		this.day_sum_price = day_sum_price;
	}
	public int getDay_sum_count() {
		return day_sum_count;
	}
	public void setDay_sum_count(int day_sum_count) {
		this.day_sum_count = day_sum_count;
	}
	public double getDay_percentage() {
		return day_percentage;
	}
	public void setDay_percentage(double day_percentage) {
		this.day_percentage = day_percentage;
	}
	public double getDay_start_price() {
		return day_start_price;
	}
	public void setDay_start_price(double day_start_price) {
		this.day_start_price = day_start_price;
	}
	public double getDay_end_price() {
		return day_end_price;
	}
	public void setDay_end_price(double day_end_price) {
		this.day_end_price = day_end_price;
	}
	public String getDay_time() {
		return day_time;
	}
	public void setDay_time(String day_time) {
		this.day_time = day_time;
	}


}
