package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.ThirtyMinkService;
import com.randioo.market_server.protocol.K.ThirtyMinkRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(ThirtyMinkRequest.class)
public class ThirtyMinkAction implements IActionSupport {
	@Autowired
	private ThirtyMinkService thirtyMinkService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		ThirtyMinkRequest request = (ThirtyMinkRequest) data;
		GeneratedMessage sc = thirtyMinkService.getThirtyMink(request.getType());
		SessionUtils.sc(session, sc);

	}

}
