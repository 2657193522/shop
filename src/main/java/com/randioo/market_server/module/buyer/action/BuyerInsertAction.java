package com.randioo.market_server.module.buyer.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.module.buyer.service.BuyerInsertService;
import com.randioo.market_server.protocol.Buyer.BuyerRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;

@Component
@PTAnnotation(BuyerRequest.class)
public class BuyerInsertAction implements IActionSupport {
	@Autowired
	private BuyerInsertService buyerInsertService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		BuyerRequest request = (BuyerRequest) data;
		Role role = (Role) RoleCache.getRoleBySession(session);
		buyerInsertService.insertBuyer(request.getBuyerData(), role.getAccount(), session);
	}
}
