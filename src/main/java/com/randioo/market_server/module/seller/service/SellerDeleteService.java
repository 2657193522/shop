package com.randioo.market_server.module.seller.service;

import org.apache.mina.core.session.IoSession;

import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface SellerDeleteService extends ObserveBaseServiceInterface {
	// 撤销出售信息
	void deleteSeller(String type, String price, String sellAccount, int sellId,IoSession session);

	void updateSeller(SellerBO seller);
	 void updateGoodsRevoke(SellerBO seller);
}
