package com.randioo.market_server.module.userInfo.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.userInfo.service.ActiveInfoService;
import com.randioo.market_server.protocol.UserInfo.ActiveInfoRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Controller
@PTAnnotation(ActiveInfoRequest.class)
public class ActiveAction implements IActionSupport {
	@Autowired
	private ActiveInfoService activeInfoService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		ActiveInfoRequest request = (ActiveInfoRequest) data;
		GeneratedMessage sc = activeInfoService.activeAccount(request.getAccount(),request.getPwd());
		SessionUtils.sc(session, sc);

	}

}
