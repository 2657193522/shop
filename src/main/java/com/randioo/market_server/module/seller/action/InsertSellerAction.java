package com.randioo.market_server.module.seller.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.module.seller.service.SellerInsertService;
import com.randioo.market_server.protocol.Seller.SellerRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;

@Component
@PTAnnotation(SellerRequest.class)
public class InsertSellerAction implements IActionSupport {
	@Autowired
	private SellerInsertService sellerService;

	@Override
	public void execute(Object data, IoSession session) {
		SellerRequest request = (SellerRequest) data;
		Role role = (Role) RoleCache.getRoleBySession(session);
		sellerService.insertSeller(role.getAccount(), request.getSellerInfoData(),session);
	}
}
