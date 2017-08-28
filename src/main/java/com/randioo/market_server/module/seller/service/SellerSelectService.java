package com.randioo.market_server.module.seller.service;

import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface SellerSelectService extends ObserveBaseServiceInterface {

	void selectMySeller(String account,IoSession session);
	
	List<SellerBO> getSellerByAccount(String account);
	//主推信息
	void scMySellerRequesr(String account);
}
