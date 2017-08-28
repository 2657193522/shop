package com.randioo.market_server.module.seller.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.market_server.module.seller.service.SellerDeleteService;
import com.randioo.market_server.protocol.Seller.RevokeSellerRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Component
@PTAnnotation(RevokeSellerRequest.class)
public class deleteSellerAction implements IActionSupport {
	@Autowired
	private SellerDeleteService sellerDeleteService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		RevokeSellerRequest request = (RevokeSellerRequest) data;
		sellerDeleteService.deleteSeller(request.getType(), request.getPrice(), request.getSellAccount(),
				request.getSellId(), session);
	}
}
