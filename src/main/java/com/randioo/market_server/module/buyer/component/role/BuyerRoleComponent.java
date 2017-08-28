package com.randioo.market_server.module.buyer.component.role;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface BuyerRoleComponent extends ObserveBaseServiceInterface {
	// 改变买用户数量
	void updateBuyerRole( String buyAccount, TradingBO tradingBO);
	
	Role getRole(String account);
	// 改变卖用户数量
	void updateSellerRole(String sellAccount,TradingBO tradingBO,int count);
	
}
