package com.randioo.market_server.module.userInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.NumberCache;
import com.randioo.market_server.dao.NumberDAO;
import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.protocol.Entity.RoleData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.protocol.UserInfo.UserInfoResponse;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("numService")
public class UserInfoServiceImpl extends ObserveBaseService implements UserInfoService {
	@Autowired
	private NumberDAO numberDao;
	@Autowired
	private RoleDAO roleDao;

	@Override
	public GeneratedMessage getNumbers(String account, String type) {
		int count = 0;
		UserInfoResponse.Builder builder=UserInfoResponse.newBuilder();
		NumbersBO num = getNum(account, type);
		if (num != null) {
			count = num.getNum_count();
		}
		Role role = getRole(account);
		if (role != null) {
			builder.setRoleData(RoleData.newBuilder().setCount(count).setLevel(role.getVip_level())
									.setName(role.getAccount()).setRmbA((int)(role.getRole_rmbA() * 100))
									.setRoleId(role.getRoleId()));
		}else {
				return SC.newBuilder().setUserInfoResponse(builder.setErrorCode(ErrorCode.NO_ROLE_ACCOUNT.getNumber())).build();
		}
		return SC.newBuilder().setUserInfoResponse(builder).build();
	}

	private Role getRole(String account) {
//		Role sellerRole = RolesCache.getRole(account);
//		if (sellerRole == null) {
		Role sellerRole = roleDao.getRole(account);
//			if (sellerRole != null) {
//				RolesCache.getRoleMap().put(account, sellerRole);
//			}
//		}
		return sellerRole;
	}

	/**
	 * 得到该用户的类型数据
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	private NumbersBO getNum(String account, String type) {
		Role role=getRole(account);
		String accountType = String.valueOf(role.getRoleId()) + type;
		NumbersBO num = NumberCache.getNumber(accountType);
		if (num == null) {
			num = numberDao.selectNumber(role.getRoleId(), type);
			if (num != null) {
				NumberCache.getNumberMap().put(accountType, num);
			}
		}
		return num;
	}
}
