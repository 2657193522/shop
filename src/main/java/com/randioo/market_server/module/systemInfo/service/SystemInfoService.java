package com.randioo.market_server.module.systemInfo.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface SystemInfoService extends ObserveBaseServiceInterface {
	void ServiceJudge();
	GeneratedMessage getTime();
}
