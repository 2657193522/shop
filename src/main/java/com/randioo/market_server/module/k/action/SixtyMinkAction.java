package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.SixtyMinkService;
import com.randioo.market_server.protocol.K.SixMinkRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(SixMinkRequest.class)
public class SixtyMinkAction implements IActionSupport {
	@Autowired
	private SixtyMinkService sixtyMinkService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		SixMinkRequest request = (SixMinkRequest) data;
		GeneratedMessage sc = sixtyMinkService.getSixtyMink(request.getType());
		SessionUtils.sc(session, sc);

	}
}
