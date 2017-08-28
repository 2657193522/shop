package com.randioo.market_server.module.userInfo.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface UserInfoService extends ObserveBaseServiceInterface {

	GeneratedMessage getNumbers(String account, String type);
}
