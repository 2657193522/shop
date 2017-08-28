package com.randioo.market_server.module.login.service;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.RolesCache;
import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.protocol.Entity.RoleData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.Gm.SCLoginOtherSide;
import com.randioo.market_server.protocol.Login.LoginGetRoleDataResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.entity.RoleInterface;
import com.randioo.randioo_server_base.module.login.Facility;
import com.randioo.randioo_server_base.module.login.LoginHandler;
import com.randioo.randioo_server_base.module.login.LoginInfo;
import com.randioo.randioo_server_base.module.login.LoginModelConstant;
import com.randioo.randioo_server_base.module.login.LoginModelService;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.Ref;
import com.randioo.randioo_server_base.utils.SessionUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("loginService")
public class LoginServiceImpl extends ObserveBaseService implements LoginService {

	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private LoginModelService loginModelService;


	@Override
	public void init() {

		loginModelService.setLoginHandler(new LoginHandler() {

			@Override
			public Facility saveFacility(Facility facility) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void noticeOtherPlaceLogin(Facility oldFacility) {
				IoSession session = oldFacility.getSession();
				SessionUtils.sc(session, SC.newBuilder().setSCLoginOtherSide(SCLoginOtherSide.newBuilder()).build());
			}

			@Override
			public void loginRoleModuleDataInit(RoleInterface roleInterface) {
				// TODO Auto-generated method stub

			}

			@Override
			public RoleInterface getRoleInterfaceFromDBById(int roleId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RoleInterface getRoleInterfaceFromDBByAccount(String account) {
				return roleDao.getRole(account);
			}

			@Override
			public Facility getFacilityFromDB(int roleId, String macAddress) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean createRoleCheckAccount(LoginInfo info, Ref<Integer> errorCode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public RoleInterface createRole(LoginInfo loginInfo) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	// private class LoginHandlerImpl implements LoginHandler {
	//
	// @Override
	// public boolean createRoleCheckAccount(LoginCreateInfo info, Ref<Integer>
	// errorCode) {
	//
	// // 账号姓名不可为空
	// if (StringUtils.isNullOrEmpty(info.getAccount())) {
	// errorCode.set(LoginConstant.CREATE_ROLE_NAME_SENSITIVE);
	// return false;
	// }
	//
	// return true;
	// }
	// @Override
	// public RoleInterface createRole(LoginCreateInfo loginCreateInfo) {
	// String account = loginCreateInfo.getAccount();
	// // 用户数据
	// // 创建用户
	// Role role = new Role();
	// role.setAccount(account);
	//
	// roleService.newRoleInit(role);
	//
	// gameDB.getInsertPool().submit(new EntityRunnable<Role>(role) {
	//
	// @Override
	// public void run(Role t) {
	// try {
	// roleDao.insert(t);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// return role;
	// }
	//
	// // @Override
	// // public RoleInterface getRoleInterface(LoginInfo loginInfo) {
	// // return getRoleByAccount(loginInfo.getAccount());
	// // }
	//
	// @Override
	// public boolean canSynLogin() {
	// return true;
	// }
	//
	// @Override
	// public RoleInterface getRoleInterfaceFromDBById(int roleId) {
	// return roleDao.get(null, roleId);
	// }
	//
	// @Override
	// public RoleInterface getRoleInterfaceFromDBByAccount(String account) {
	// return roleDao.get(account, 0);
	// }
	//
	// @Override
	// public void loginRoleModuleDataInit(RoleInterface roleInterface) {
	// // 将数据库中的数据放入缓存中
	// Role role = (Role) roleInterface;
	//
	// // roleService.roleInit(role);
	// }
	//
	// }

	private Role getRole(String account) {
		Role sellerRole = RolesCache.getRole(account);
		if (sellerRole == null) {
			sellerRole = roleDao.getRole(account);
			if (sellerRole != null) {
				RolesCache.getRoleMap().put(account, sellerRole);
			}
		}
		return sellerRole;
	}

	@Override
	public GeneratedMessage getRoleData(String account, IoSession ioSession,String address) {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setAccount(account);
		loginInfo.setMacAddress(address);

		Ref<Integer> errorCode = new Ref<>();

		RoleInterface roleInterface = loginModelService.getRoleData(loginInfo, errorCode, ioSession);

		if (roleInterface != null) {
			Role role = (Role) roleInterface;
			SC sc = SC.newBuilder().setLoginGetRoleDataResponse(LoginGetRoleDataResponse.newBuilder()
					.setServerTime(TimeUtils.getNowTime()).setRoleData(getRoleData(role))).build();
			return sc;
		}

		ErrorCode errorEnum = null;
		switch (errorCode.get()) {
		case LoginModelConstant.GET_ROLE_DATA_NOT_EXIST:
			errorEnum = ErrorCode.NO_ROLE_DATA;
			break;
		case LoginModelConstant.GET_ROLE_DATA_IN_LOGIN:
			errorEnum = ErrorCode.IN_LOGIN;
			break;
		}
		SC sc = SC.newBuilder()
				.setLoginGetRoleDataResponse(LoginGetRoleDataResponse.newBuilder().setErrorCode(errorEnum.getNumber()))
				.build();
		return sc;
	}

	// @Override
	// public GeneratedMessage creatRole(String account) {
	// LoginCreateInfo loginInfo = new LoginCreateInfo();
	// loginInfo.setAccount(account);
	//
	// Ref<Integer> errorCode = new Ref<>();
	// boolean result = loginModelService.createRole(loginInfo, errorCode);
	// if (result) {
	// return
	// SC.newBuilder().setLoginCreateRoleResponse(LoginCreateRoleResponse.newBuilder()).build();
	// }
	//
	// ErrorCode errorEnum = null;
	// switch (errorCode.get()) {
	// case LoginModelConstant.CREATE_ROLE_EXIST:
	// errorEnum = ErrorCode.EXIST_ROLE;
	// break;
	// case LoginModelConstant.CREATE_ROLE_FAILED:
	// errorEnum = ErrorCode.CREATE_FAILED;
	// break;
	// case LoginConstant.CREATE_ROLE_NAME_SENSITIVE:
	// errorEnum = ErrorCode.NAME_SENSITIVE;
	// break;
	// case LoginConstant.CREATE_ROLE_NAME_REPEATED:
	// errorEnum = ErrorCode.NAME_REPEATED;
	// break;
	// case LoginConstant.CREATE_ROLE_NAME_TOO_LONG:
	// errorEnum = ErrorCode.NAME_TOO_LONG;
	// break;
	// }
	//
	// return SC.newBuilder()
	// .setLoginCreateRoleResponse(LoginCreateRoleResponse.newBuilder().setErrorCode(errorEnum.getNumber()))
	// .build();
	// }

	// @Override
	// public GeneratedMessage login(String account) {
	// LoginInfo info = new LoginInfo();
	// info.setAccount(account);
	// if (!GlobleConfig.Boolean(GlobleEnum.LOGIN)) {
	// return SC.newBuilder()
	// .setLoginCheckAccountResponse(
	// LoginCheckAccountResponse.newBuilder().setErrorCode(ErrorCode.REJECT_LOGIN.getNumber()))
	// .build();
	// }
	//
	// boolean isNewAccount = loginModelService.login(info);
	//
	// return SC.newBuilder()
	// .setLoginCheckAccountResponse(LoginCheckAccountResponse.newBuilder()
	// .setErrorCode(isNewAccount ? ErrorCode.NO_ROLE_ACCOUNT.getNumber() :
	// ErrorCode.OK.getNumber()))
	// .build();
	// }

	private RoleData getRoleData(Role role) {
		Role roles = getRole(role.getAccount());
		RoleData.Builder roleDataBuilder = RoleData.newBuilder().setRoleId(role.getRoleId()).setName(role.getAccount())
				.setRmbA((int)roles.getRole_rmbA()*100);
		return roleDataBuilder.build();
	}

	@Override
	public Role getRoleById(int roleId) {
		RoleInterface roleInterface = loginModelService.getRoleInterfaceById(roleId);
		return roleInterface == null ? null : (Role) roleInterface;
	}

	@Override
	public Role getRoleByAccount(String account) {
		RoleInterface roleInterface = loginModelService.getRoleInterfaceByAccount(account);
		return roleInterface == null ? null : (Role) roleInterface;
	}
}
