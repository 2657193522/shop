package com.randioo.market_server.http.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.http_server.server.LiteServlet;
import com.randioo.market_server.http.action.RmbHttpAction;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

@Service
public class RmbHttpServlet extends LiteServlet{

	@Autowired
	private RmbHttpAction rmbHttpAction;
	
	
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
				rmbHttpAction.execute(exchange, null);
				bos.write("ok".getBytes());
			} catch (Exception e) {
				e.printStackTrace();
				bos.write("kick failed".getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
