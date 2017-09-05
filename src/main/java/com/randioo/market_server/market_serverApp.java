package com.randioo.market_server;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

import com.randioo.market_server.cron.QuartzManager;
import com.randioo.market_server.entity.file.TimeConfig;
import com.randioo.market_server.handler.HeartTimeOutHandler;
import com.randioo.market_server.protocol.ClientMessage.CS;
import com.randioo.market_server.protocol.Heart.CSHeart;
import com.randioo.market_server.protocol.Heart.HeartRequest;
import com.randioo.market_server.protocol.Heart.HeartResponse;
import com.randioo.market_server.protocol.Heart.SCHeart;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.config.ConfigLoader;
import com.randioo.randioo_server_base.config.GlobleConfig;
import com.randioo.randioo_server_base.config.GlobleConfig.GlobleEnum;
import com.randioo.randioo_server_base.heart.ProtoHeartFactory;
import com.randioo.randioo_server_base.init.GameServerInit;
import com.randioo.randioo_server_base.utils.SpringContext;
import com.randioo.randioo_server_base.utils.StringUtils;

/**
 * Hello world!
 *
 */
/**
 * @author mb
 *
 */
public class market_serverApp {
	public static void main(String[] args) {
		StringUtils.printArgs(args);
		GlobleConfig.init(args);

		ConfigLoader.loadConfig("com.randioo.market_server.entity.file", "./config.zip");
		SpringContext.initSpringCtx("classpath:ApplicationContext.xml");

		GameServerInit gameServerInit = (GameServerInit) SpringContext.getBean("gameServerInit");

		// 心跳工厂
		ProtoHeartFactory protoHeartFactory = new ProtoHeartFactory();
		protoHeartFactory.setHeartRequest(CS.newBuilder().setHeartRequest(HeartRequest.newBuilder()).build());
		protoHeartFactory.setHeartResponse(SC.newBuilder().setHeartResponse(HeartResponse.newBuilder()).build());
		protoHeartFactory.setScHeart(SC.newBuilder().setSCHeart(SCHeart.newBuilder()).build());
		protoHeartFactory.setCsHeart(CS.newBuilder().setCSHeart(CSHeart.newBuilder()).build());

		gameServerInit.setProtocol(CS.getDefaultInstance());
		HeartTimeOutHandler heartTimeOutHandler = SpringContext.getBean("heartTimeOutHandler");
		gameServerInit.setKeepAliveFilter(
				new KeepAliveFilter(protoHeartFactory, IdleStatus.READER_IDLE, heartTimeOutHandler, 10, 10));
		gameServerInit.start();
		GlobleConfig.set(GlobleEnum.LOGIN, true);
		HttpInit.init();
	
		QuartzManager.openQuartz();
	}
}
