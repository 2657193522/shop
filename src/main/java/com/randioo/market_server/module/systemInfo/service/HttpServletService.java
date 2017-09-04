package com.randioo.market_server.module.systemInfo.service;

import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface HttpServletService extends ObserveBaseServiceInterface{
	void repair(int lockString,int time);
	
	void scrmb(String account,String type);
}
