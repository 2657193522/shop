package com.randioo.market_server.module.seller.service;

import org.apache.mina.core.session.IoSession;

import com.randioo.market_server.protocol.Entity.SellerInfoData;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface SellerInsertService extends ObserveBaseServiceInterface {

	// 添加卖的信息
	void insertSeller(String sellAccount, SellerInfoData sellerInfoData,IoSession session);
	
	
}
