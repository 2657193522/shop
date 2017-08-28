package com.randioo.market_server.module.k.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface DaykClickService extends ObserveBaseServiceInterface{
	/**切换日K时得到所有日K数据*/
	GeneratedMessage getAllDayk(String type);

}
