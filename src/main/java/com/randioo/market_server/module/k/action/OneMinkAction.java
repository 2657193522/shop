package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.OneMinkService;
import com.randioo.market_server.protocol.K.OneMinkRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(OneMinkRequest.class)
public class OneMinkAction implements IActionSupport {
	@Autowired
	private OneMinkService oneMinkService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		OneMinkRequest request = (OneMinkRequest) data;
		GeneratedMessage sc = oneMinkService.getOneMink(request.getType());
		SessionUtils.sc(session, sc);

	}

}
