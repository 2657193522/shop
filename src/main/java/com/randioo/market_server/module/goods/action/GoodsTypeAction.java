package com.randioo.market_server.module.goods.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.module.goods.service.GoodsService;
import com.randioo.market_server.protocol.GoodsTy.GoodsTyRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Controller
@PTAnnotation(GoodsTyRequest.class)
public class GoodsTypeAction implements IActionSupport {

	@Autowired
	private GoodsService goodsServcie;

	@Override
	public void execute(Object data, IoSession session) {
		GoodsTyRequest request = (GoodsTyRequest) data;
		GeneratedMessage sc=goodsServcie.getGoodsType(request.getGoodsType());
		SessionUtils.sc(session, sc);
	}
}
