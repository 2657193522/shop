package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.FifMinkService;
import com.randioo.market_server.protocol.K.FifMinkRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(FifMinkRequest.class)
public class FifMinkAction implements IActionSupport {
	@Autowired
	private FifMinkService fifMinkService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		FifMinkRequest request = (FifMinkRequest) data;
		GeneratedMessage sc = fifMinkService.getFifMink(request.getType());
		SessionUtils.sc(session, sc);

	}

}
