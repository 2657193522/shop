package com.randioo.market_server.module.userInfo.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface ActiveInfoService extends ObserveBaseServiceInterface{
	GeneratedMessage activeAccount(String account,String pwd);
	
}
