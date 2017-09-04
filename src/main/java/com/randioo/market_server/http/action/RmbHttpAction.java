package com.randioo.market_server.http.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.market_server.module.systemInfo.service.HttpServletService;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.sun.net.httpserver.HttpExchange;
@Controller
public class RmbHttpAction implements IActionSupport{
	@Autowired
	private HttpServletService httpServletService;
	
	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		HttpExchange request = (HttpExchange) data;
		try {
			String account=(String) request.getAttribute("account");
			String type=(String) request.getAttribute("type");
			httpServletService.scrmb(account,type);;
		} catch (Exception e) {
			System.out.println(e);
		}

	}

//	private String getMessage(InputStream inputStream) throws UnsupportedEncodingException, IOException {
//		try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
//			String readLine = null;
//			StringBuilder sb = new StringBuilder();
//			while ((readLine = responseReader.readLine()) != null) {
//				sb.append(readLine).append(StringUtils.lineSplit);
//			}
//			return sb.toString();
//		}
//	}
}
