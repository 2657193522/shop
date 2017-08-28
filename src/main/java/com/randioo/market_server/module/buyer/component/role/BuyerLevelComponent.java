package com.randioo.market_server.module.buyer.component.role;

import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface BuyerLevelComponent extends ObserveBaseServiceInterface {

	int getLevel(String account, String type, int sellId, int count);
}
