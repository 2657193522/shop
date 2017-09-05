package com.randioo.market_server.module.userInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.dao.NumberDAO;
import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.dao_remote.CurencyUserDAO;
import com.randioo.market_server.dao_remote.MemberDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo_remote.CurencyUser;
import com.randioo.market_server.entity.bo_remote.Member;
import com.randioo.market_server.protocol.Entity.RoleData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.protocol.UserInfo.UserInfoResponse;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;

@Service("numService")
public class UserInfoServiceImpl extends ObserveBaseService implements UserInfoService {
	@Autowired
	private NumberDAO numberDao;
	@Autowired
	private RoleDAO roleDao;
	@Autowired
	private CurencyUserDAO curencyUserDAO;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private MemberDAO memberDao;

	@Override
	public GeneratedMessage getNumbers(String account, String type) {
		UserInfoResponse.Builder builder = UserInfoResponse.newBuilder();
		Role role = getRole(account);
		Member member = memberDao.getMember(account);
		if (role == null) {
			return SC.newBuilder().setUserInfoResponse(builder.setErrorCode(ErrorCode.NO_ROLE_DATA.getNumber()))
					.build();
		}
		CurencyUser user = curencyUserDAO.getCurrency(role.getRoleId(), Integer.parseInt(type));
		if (user == null) {
			// CurencyUser user = new CurencyUser();
			user = new CurencyUser();
			user.setCurrency_id(Integer.parseInt(type));
			user.setMember_id(role.getRoleId());
			user.setNum(0);
			user.setForzen_num(0);
			gameDB.getInsertPool().submit(new EntityRunnable<CurencyUser>(user) {
				@Override
				public void run(CurencyUser entity) {
					curencyUserDAO.insert(entity);
				}
			});
			NumbersBO numbersBO = new NumbersBO();
			numbersBO.setNum_account(String.valueOf(role.getRoleId()));
			numbersBO.setNum_count(0);
			numbersBO.setNum_id(role.getRoleId());
			numbersBO.setNum_type(type);
			numbersBO.setOperation_count(0);
			numbersBO.setOperation_time("0");
			gameDB.getInsertPool().submit(new EntityRunnable<NumbersBO>(numbersBO) {
				@Override
				public void run(NumbersBO entity) {
					numberDao.insert(entity);
				}
			});
		}
		builder.setRoleData(RoleData.newBuilder().setCount(user.getNum()).setLevel(member.getVip_level())
				.setRmbA((int) (Double.parseDouble(String.format("%.2f", member.getRmb() * 100))))
				.setRoleId(member.getMember_id()));

		return SC.newBuilder().setUserInfoResponse(builder).build();
	}

	private Role getRole(String account) {
		// Role sellerRole = RolesCache.getRole(account);
		// if (sellerRole == null) {
		Role sellerRole = roleDao.getRole(account);
		// if (sellerRole != null) {
		// RolesCache.getRoleMap().put(account, sellerRole);
		// }
		// }
		return sellerRole;
	}

	/**
	 * 得到该用户的类型数据
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
//	private NumbersBO getNum(String account, String type) {
//		Role role = getRole(account);
//		String accountType = String.valueOf(role.getRoleId()) + type;
//		NumbersBO num = NumberCache.getNumber(accountType);
//		if (num == null) {
//			num = numberDao.selectNumber(role.getRoleId(), type);
//			if (num != null) {
//				NumberCache.getNumberMap().put(accountType, num);
//			}
//		}
//		return num;
//	}
}
