package com.randioo.market_server.module.buyer.component.trading;

import org.apache.mina.core.session.IoSession;

import com.randioo.market_server.entity.bo.BuyerBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface InsertTradingComponent extends ObserveBaseServiceInterface {

	void insertTrading(BuyerBO buyerBo, int sellId,IoSession session,String sellAccount);
	

}
