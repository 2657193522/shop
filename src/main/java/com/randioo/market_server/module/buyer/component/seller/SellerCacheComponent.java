package com.randioo.market_server.module.buyer.component.seller;

import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface SellerCacheComponent extends ObserveBaseServiceInterface {
	void deleteSeller(SellerBO seller);
}
