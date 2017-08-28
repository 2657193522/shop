package com.randioo.market_server.module.k.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;
import com.randioo.randioo_server_base.template.Observer;

public interface KService extends ObserveBaseServiceInterface{
	/**成交通知*/
	void update(Observer observer, String msg, Object... args);
	/**进来通过类型请求K线图数据,并根据服务器状态发送K线图数据*/
	GeneratedMessage getK(String type,int sumDay);
	
}

