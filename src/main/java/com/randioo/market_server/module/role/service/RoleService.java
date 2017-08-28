package com.randioo.market_server.module.role.service;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface RoleService extends ObserveBaseServiceInterface {

	void newRoleInit(Role role);

	public void roleInit(Role role);

}
