package com.randioo.market_server.module.buyer.component.role;

import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.dao_remote.MemberDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("buyerRoleComponent")
public class BuyerRoleComponentImpl extends ObserveBaseService implements BuyerRoleComponent {

	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private MemberDAO memberDAO;

	@Override
	public void updateBuyerRole(String buyAccount, TradingBO tradingBO) {
		Role buyRole = getRole(buyAccount);
		if (buyRole != null) {
			updateBuyer(buyRole, tradingBO);
		}
	}

	@Override
	public void updateSellerRole(String sellAccount, TradingBO tradingBO) {
		Role sellerRole = getRole(sellAccount);
		if (sellerRole != null) {
			updateSeller(sellerRole, tradingBO);
		}
	}

	@Override
	public Role getRole(String account) {
		// Role role = RolesCache.getRole(account);
		// if (role == null) {
		Role role = roleDAO.getRole(account);
		// if (role != null) {
		// RolesCache.getRoleMap().put(account, role);
		// }
		// }
		return role;
	}

	/**
	 * 修改买的用户数量资金
	 * @param buyerRole
	 * @param trading
	 */
	private void updateBuyer(Role buyerRole, TradingBO trading) {
		// Lock lock = CacheLockUtil.getLock(Role.class, buyerRole.getRoleId());
		// try {
		// lock.lock();
		double price = 0.00;
		trading.setTrad_before_rmb(buyerRole.getRole_rmbA());
		// 扣除金额
		if (buyerRole.getRole_rmbA() - trading.getTrad_poundage() - trading.getTrad_sum() >= 0) {
			if (buyerRole.getVip_level() == 0) {
				buyerRole.setRole_rmbA(buyerRole.getRole_rmbA() - trading.getTrad_poundage() - trading.getTrad_sum());
				price = trading.getTrad_poundage() + trading.getTrad_sum();
				trading.setTrad_after_rmb(buyerRole.getRole_rmbA());
			} else if (buyerRole.getVip_level() == 1) {
				buyerRole.setRole_rmbA(buyerRole.getRole_rmbA() - trading.getTrad_sum());
				price = trading.getTrad_sum();
				trading.setTrad_poundage(0);
				trading.setTrad_after_rmb(buyerRole.getRole_rmbA());
			}
		}
		updateBuyMember(trading.getTrad_poundage() + trading.getTrad_sum(),buyerRole.getRoleId());
		// gameDB.getUpdatePool().submit(new EntityRunnable<Role>(buyerRole) {
		// @Override
		// public void run(Role entity) {
		//修改购买的用户金额
		roleDAO.update(buyerRole);
		// }
		// });
		// } finally {
		// lock.unlock();
		// }
	}

	private void updateBuyMember(double price,int roleId) {
		memberDAO.jianUpdate(price, roleId);
	}

	/**
	 * 修改卖的金额
	 * 
	 * @param sellerRole
	 * @param trading
	 */
	private void updateSeller(Role sellerRole, TradingBO trading) {
		Lock lock = CacheLockUtil.getLock(Role.class, sellerRole.getRoleId());
		try {
			lock.lock();
			double price = 0.00;
			trading.setTrad_before_rmb(sellerRole.getRole_rmbA());
//			trading.setTrad_before_count(count);
//			trading.setTrad_after_count(count);
			if (sellerRole.getVip_level() == 0) {
				sellerRole.setRole_rmbA(sellerRole.getRole_rmbA() - trading.getTrad_poundage() + trading.getTrad_sum());
				price = trading.getTrad_sum() - trading.getTrad_poundage();
				trading.setTrad_after_rmb(sellerRole.getRole_rmbA());
			} else if (sellerRole.getVip_level() == 1) {
				sellerRole.setRole_rmbA(sellerRole.getRole_rmbA() + trading.getTrad_sum());
				price = trading.getTrad_sum();
				trading.setTrad_poundage(0);
				trading.setTrad_after_rmb(sellerRole.getRole_rmbA());
			}
			updateSellRole(trading.getTrad_sum() - trading.getTrad_poundage(), sellerRole.getRoleId());
			// gameDB.getUpdatePool().submit(new
			// EntityRunnable<Role>(sellerRole) {
			// @Override
			// public void run(Role entity) {
			//修改出售用户的金额
			roleDAO.update(sellerRole);
			// }
			// });
		} finally {
			lock.unlock();
		}
	}

	public void updateSellRole(double price, int roleId) {
		memberDAO.addUpdate(price, roleId);
	}

}
