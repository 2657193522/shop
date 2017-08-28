package com.randioo.market_server.module.k.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.k.service.DaykClickService;
import com.randioo.market_server.protocol.K.DayClickRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(DayClickRequest.class)
public class DaykAction implements IActionSupport {
	@Autowired
	private DaykClickService daykClickService;

	@Override
	public void execute(Object data, IoSession session) {
		DayClickRequest request = (DayClickRequest) data;
		GeneratedMessage sc = daykClickService.getAllDayk(request.getType());
		SessionUtils.sc(session, sc);
	}
}
