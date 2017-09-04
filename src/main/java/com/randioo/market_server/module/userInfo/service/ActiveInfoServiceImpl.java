package com.randioo.market_server.module.userInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.dao_remote.MemberDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo_remote.Member;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.protocol.UserInfo.ActiveInfoResponse;
import com.randioo.market_server.util.MD5Util;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("activeInfoService")
public class ActiveInfoServiceImpl extends ObserveBaseService implements ActiveInfoService {
	@Autowired
	private MemberDAO memberDao;
	@Autowired
	private RoleDAO roleDao;
	@Autowired
	private GameDB gameDB;

	@Override
	public GeneratedMessage activeAccount(String account, String pwd) {
		// TODO Auto-generated method stub
		ActiveInfoResponse.Builder builder = ActiveInfoResponse.newBuilder();
		Role role = roleDao.getRole(account);
		if (role == null) {
			Member member = memberDao.getMember(account);
			if (member != null) {
				if (MD5Util.string2MD5(pwd).equals(member.getPwdtrade())) {
					role = new Role();
					role.setAccount(account);
					role.setPassWord(member.getPwdtrade());
					role.setVip_level(member.getVip_level());
					role.setRoleId(member.getMember_id());
					role.setRole_rmbA(member.getRmb());
					// gameDB.getInsertPool().submit(new
					// EntityRunnable<Role>(role2)
					// {
					// @Override
					// public void run(Role entity) {
					roleDao.insert(role);
					// }
					// });
				}
			}
		}
		Role r = roleDao.getRole(account);
		if (r != null)
			builder.setActive(Constant.YES);
		return SC.newBuilder().setActiveInfoResponse(builder).build();
	}
}
