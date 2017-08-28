package com.randioo.market_server.module.k.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface MinkClickService extends ObserveBaseServiceInterface{
	/**点击5分K得到当天5分K数据*/
	GeneratedMessage getMinkClick(String type);
}
