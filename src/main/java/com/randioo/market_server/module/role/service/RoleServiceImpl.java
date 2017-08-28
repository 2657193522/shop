package com.randioo.market_server.module.role.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.module.login.LoginConstant;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.module.role.RoleHandler;
import com.randioo.randioo_server_base.module.role.RoleModelService;
import com.randioo.randioo_server_base.sensitive.SensitiveWordDictionary;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.Ref;

@Service("roleService")
public class RoleServiceImpl extends ObserveBaseService implements RoleService {


	@Autowired
	private RoleModelService roleModelService;

	@Override
	public void init() {
		roleModelService.setRoleHandler(new RoleHandler() {

			Pattern p = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");

			@Override
			public boolean checkNewNameIllege(String name, Ref<Integer> errorCode) {
				// TODO Auto-generated method stub

				if (name.length() >= 10) {
					errorCode.set(LoginConstant.CREATE_ROLE_NAME_TOO_LONG);
					return false;
				}

				if (SensitiveWordDictionary.containsSensitiveWord(name)) {
					errorCode.set(LoginConstant.CREATE_ROLE_NAME_SENSITIVE);
					return false;
				}

				if (RoleCache.getNameSet().containsKey(name)) {
					errorCode.set(LoginConstant.CREATE_ROLE_NAME_REPEATED);
					return false;
				}

				// 检查特殊字符
				if (!p.matcher(name).find()) {
					errorCode.set(LoginConstant.CREATE_ROLE_NAME_CHAR);
					return false;
				}

				return true;

			}
		});
	}

	@Override
	public void roleInit(Role role) {
		// TODO Auto-generated method stub
	}

	@Override
	public void newRoleInit(Role role) {
		// TODO Auto-generated method stub
	}
}
