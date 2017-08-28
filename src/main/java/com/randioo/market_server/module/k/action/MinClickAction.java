package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.MinkClickService;
import com.randioo.market_server.protocol.K.MinkClickRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(MinkClickRequest.class)
public class MinClickAction implements IActionSupport {

	@Autowired
	private MinkClickService minkClickService;

	@Override
	public void execute(Object data, IoSession session) {
		MinkClickRequest request = (MinkClickRequest) data;
		GeneratedMessage sc = minkClickService.getMinkClick(request.getType());
		SessionUtils.sc(session, sc);
	}
}