package com.randioo.market_server.http.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.http_server.utils.StringUtils;
import com.randioo.market_server.module.systemInfo.service.HttpServletService;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.sun.net.httpserver.HttpExchange;

@Controller
public class SystemHttpAction implements IActionSupport {

	private Logger logger = LoggerFactory.getLogger(SystemHttpAction.class);
	@Autowired
	private HttpServletService httpServletService;

	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		HttpExchange request = (HttpExchange) data;

		InputStream inputStream = request.getRequestBody();
		String message = null;
		try {
			message = getMessage(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] type = message.split("&");
		String lockString = type[0].split("=")[1];
		String time = type[1].split("=")[1];
		// String times=time.replace(" ", "");
//		String times = time.replaceAll(' ', '');
//		 String times = time.substring(0, 1);
		String times=time.trim();

		logger.info("changdu" + lockString.length() + "Http+++++");
		logger.info("changdu" + times.length() + "Http+++++");
		logger.info("HttpServlet开关" + lockString + "Http+++++");
		logger.info("HttpServlet时间" + times + "Http++++");
		try {
			int tim = Integer.parseInt(times);
			int lockStr = Integer.parseInt(lockString);
			httpServletService.repair(lockStr, tim);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private String getMessage(InputStream inputStream) throws UnsupportedEncodingException, IOException {
		try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append(StringUtils.lineSplit);
			}
			return sb.toString();
		}
	}

}
