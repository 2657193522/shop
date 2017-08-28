package com.randioo.market_server.module.seller.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.market_server.module.seller.service.SellerSelectService;
import com.randioo.market_server.protocol.Seller.SellerMyRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Component
@PTAnnotation(SellerMyRequest.class)
public class SelectSellerAction implements IActionSupport {
	@Autowired
	private SellerSelectService sellerSelectService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		SellerMyRequest request = (SellerMyRequest) data;
		sellerSelectService.selectMySeller(request.getAccount(),session);
	}
}
