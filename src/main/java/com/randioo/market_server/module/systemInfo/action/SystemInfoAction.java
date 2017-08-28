package com.randioo.market_server.module.systemInfo.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.systemInfo.service.SystemInfoService;
import com.randioo.market_server.protocol.SystemInfo.SystemInfoRequest;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(SystemInfoRequest.class)
public class SystemInfoAction implements IActionSupport {
	@Autowired
	private SystemInfoService systemInfoService;

	@Override
	public void execute(Object data, IoSession session) {
		GeneratedMessage sc = systemInfoService.getTime();
		System.out.println(sc.toByteArray().length);
		SessionUtils.sc(session, sc);
	}
}
