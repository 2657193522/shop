package com.randioo.market_server.module.sc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.module.sc.state.TrueStateImpl;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("refushMinkService")
public class RefushMinkServiceImpl extends ObserveBaseService implements RefushMinkService {
	@Autowired
	private Role roleChange;


	@Override
	public void refushMink(String type) {
		// TODO Auto-generated method stub
		// if (!StateCache.isPanduan()) {
		// new FalseStateImpl().refushMessage(type);
		//
		// } else {
		if (roleChange.isJudge()) {
			new TrueStateImpl().refushMessage(type);
		}
		// }
		// StateCache.setPanduan(true);
	}

}
