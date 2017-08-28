package com.randioo.market_server.module.login.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.login.service.LoginService;
import com.randioo.market_server.protocol.Login.LoginGetRoleDataRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(LoginGetRoleDataRequest.class)
public class LoginGetRoleDataAction implements IActionSupport {

	@Autowired
	private LoginService loginService;

	@Override
	public void execute(Object data, IoSession session) {
		LoginGetRoleDataRequest request = (LoginGetRoleDataRequest) data;
		GeneratedMessage sc = loginService.getRoleData(request.getAccount(), session, request.getAddress());
		if (sc != null) {
			session.write(sc);
		}
	}
}
