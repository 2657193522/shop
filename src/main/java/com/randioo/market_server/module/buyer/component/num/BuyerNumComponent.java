package com.randioo.market_server.module.buyer.component.num;

import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface BuyerNumComponent extends ObserveBaseServiceInterface {
	//买成功修改商品  新增
	void updateNumber(String sellAccount, String buyAccount, TradingBO tradingBO);
	
	NumbersBO getNumber(String account, String type);
}
