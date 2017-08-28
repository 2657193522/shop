package com.randioo.market_server.module.goods.service;

import java.util.List;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface GoodsService extends ObserveBaseServiceInterface {
	/** 通过类型，商品状态查询所有出售信息 */
	GeneratedMessage getGoodsType(String type);

	/** 通过类型，状态 ，商品，用户，出售单号 得到该订单的所有商品详情 */
	GeneratedMessage getGoodsDetails(String type, int sellId);
	
	List<TradingBO> getTradingListByToDay(String type,int state);

	List<SellerBO> getSellerTypeA(String type);
}
