package com.randioo.market_server.http.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.http_server.server.LiteServlet;
import com.randioo.market_server.http.action.SystemHttpAction;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

@Service
public class SystemHttpServlet extends LiteServlet {
	@Autowired
	private SystemHttpAction systemHttpAction;

	private Logger logger = LoggerFactory.getLogger(SystemHttpServlet.class);

	@Override
	protected void doGet(HttpExchange exchange) {
		doPost(exchange);
	}

	@Override
	protected void doPost(HttpExchange exchange) {
		// doGet(exchange);
		Headers responseHeaders = exchange.getResponseHeaders();
		try {
			exchange.sendResponseHeaders(200, 0);
			responseHeaders.set("Content-Type", "text/plain");
		} catch (IOException e) {
			e.printStackTrace();
		}
		OutputStream outputStream = exchange.getResponseBody();
		try (BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {
			try {
				logger.info("HttpServlet" + "Ok");
				systemHttpAction.execute(exchange, null);
				bos.write("ok".getBytes());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("HttpServlet" + "失败");
				bos.write("kick failed".getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
