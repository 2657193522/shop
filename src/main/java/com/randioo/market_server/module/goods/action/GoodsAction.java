package com.randioo.market_server.module.goods.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.goods.service.GoodsService;
import com.randioo.market_server.protocol.Goods.GoodsRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Controller
@PTAnnotation(GoodsRequest.class)
public class GoodsAction implements IActionSupport {
	@Autowired
	private GoodsService goodsServcie;

	@Override
	public void execute(Object data, IoSession session) {
		GoodsRequest request = (GoodsRequest) data;
		GeneratedMessage sc=goodsServcie.getGoodsDetails(request.getType(), request.getSellId());
		SessionUtils.sc(session, sc);
	}
}
