package com.randioo.market_server.module.deal.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.module.deal.service.DealService;
import com.randioo.market_server.protocol.Deal.DealRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(DealRequest.class)
public class DealAction implements IActionSupport {
	@Autowired
	private DealService dealService;

	@Override
	public void execute(Object data, IoSession session) {
		Role role = (Role) RoleCache.getRoleBySession(session);
		DealRequest request = (DealRequest) data;
		GeneratedMessage sc = dealService.getDeal(role, request.getType(),request.getStartNum(),request.getCount());
		SessionUtils.sc(session, sc);
	}
}
