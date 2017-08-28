package com.randioo.market_server.module.userInfo.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.userInfo.service.UserInfoService;
import com.randioo.market_server.protocol.UserInfo.UserInfoRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Controller
@PTAnnotation(UserInfoRequest.class)
public class NumberAction implements IActionSupport {
	@Autowired
	private UserInfoService numberService;

	@Override
	public void execute(Object data, IoSession session) {
		UserInfoRequest request = (UserInfoRequest) data;
		GeneratedMessage sc=numberService.getNumbers(request.getAccount(), request.getType());
		SessionUtils.sc(session, sc);
	}
}
