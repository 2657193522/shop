package com.randioo.market_server;

import java.io.IOException;

import com.randioo.http_server.server.LiteHttpServer;
import com.randioo.http_server.server.LiteServlet;
import com.randioo.randioo_server_base.config.GlobleConfig;
import com.randioo.randioo_server_base.config.GlobleConfig.GlobleEnum;
import com.randioo.randioo_server_base.utils.SpringContext;

public class HttpInit {

	private static LiteHttpServer httpServer = new LiteHttpServer();

	public static void init() {
		httpServer.setPort(GlobleConfig.Int(GlobleEnum.PORT) + 10000);
		httpServer.setRootPath("/market-server");
		httpServer.addLiteServlet("/repair", (LiteServlet) SpringContext.getBean("systemHttpServlet"));
		httpServer.addLiteServlet("/scrmb", (LiteServlet) SpringContext.getBean("rmbHttpServlet"));
		try {
			httpServer.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static LiteHttpServer getHttpServer() {
		return httpServer;
	}

}
