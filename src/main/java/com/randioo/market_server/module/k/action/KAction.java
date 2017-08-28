package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.KService;
import com.randioo.market_server.protocol.K.KRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Controller
@PTAnnotation(KRequest.class)
public class KAction implements IActionSupport {
	@Autowired
	private KService kService;

	@Override
	public void execute(Object data, IoSession session) {
		KRequest request = (KRequest) data;
		GeneratedMessage sc = kService.getK(request.getType(), request.getSumDay());
		SessionUtils.sc(session, sc);
	}
}
