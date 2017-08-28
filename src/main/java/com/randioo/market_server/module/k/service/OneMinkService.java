package com.randioo.market_server.module.k.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface OneMinkService extends ObserveBaseServiceInterface{
	GeneratedMessage getOneMink(String type);

}
