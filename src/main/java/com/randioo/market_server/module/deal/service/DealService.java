package com.randioo.market_server.module.deal.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.entity.bo.Role;

public interface DealService {
	/**查看当天该类型成交记录*/
	GeneratedMessage getDeal(Role role,String type,int startNum,int count);
	

}
