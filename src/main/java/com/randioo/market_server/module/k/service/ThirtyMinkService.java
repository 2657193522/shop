package com.randioo.market_server.module.k.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface ThirtyMinkService extends ObserveBaseServiceInterface{
	GeneratedMessage getThirtyMink(String type);

}
