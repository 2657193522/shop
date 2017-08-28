package com.randioo.market_server.module.buyer.service;

import org.apache.mina.core.session.IoSession;

import com.randioo.market_server.protocol.Entity.BuyerData;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface BuyerInsertService extends ObserveBaseServiceInterface {
	void insertBuyer(BuyerData buyerData, String buyAccount,IoSession session);
}
