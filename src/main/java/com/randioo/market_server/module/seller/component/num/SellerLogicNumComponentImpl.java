package com.randioo.market_server.module.seller.component.num;

import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.NumberCache;
import com.randioo.market_server.dao.NumberDAO;
import com.randioo.market_server.dao.RecordDAO;
import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.dao_remote.CurencyUserDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.RecordBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.protocol.Gm.SCNumberNotice;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.utils.SessionUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("sellerLogicNumComponent")
public class SellerLogicNumComponentImpl extends ObserveBaseService implements SellerLogicNumComponent {
	@Autowired
	private NumberDAO numberDAO;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private CurencyUserDAO curencyUserDao;
	@Autowired
	private RecordDAO recordDao;

	@Override
	public Role getRoleByAccount(String account) {
		Role role = roleDAO.getRole(account);
		return role;
	}

	@Override
	public NumbersBO getNumbers(String account, String type) {
		Role role = getRoleByAccount(account);
		String accountType = String.valueOf(role.getRoleId()) + type;
		NumbersBO numbersBO = NumberCache.getNumber(accountType);
		if (numbersBO == null) {
			numbersBO = numberDAO.selectNumber(role.getRoleId(), type);
			if (numbersBO != null) {
				NumberCache.getNumberMap().put(accountType, numbersBO);
			}
		}
		return numbersBO;
	}

	/**
	 * 修改卖家拥有数量（上架减少）
	 */
	@Override
	public void updateNumbers(SellerBO sellerBO, int sellRoleId) {

		NumbersBO numbersBO = getNumbers(sellerBO.getSell_account(), sellerBO.getSell_type());
		if (numbersBO != null) {
			Lock lock = CacheLockUtil.getLock(Number.class, numbersBO.getNum_id());
			try {
				lock.lock();
				if (numbersBO.getNum_count() >= sellerBO.getSell_count()) {
					// 同步其他数据库数量
					updateDeleteCurrency(sellRoleId, numbersBO.getNum_type(), sellerBO.getSell_count(), numbersBO,sellerBO.getSell_account());
					numbersBO.setNum_count(numbersBO.getNum_count() - sellerBO.getSell_count());
					// 一周操作数量
					numbersBO.setOperation_count(numbersBO.getOperation_count() - sellerBO.getSell_count());
					numbersBO.setOperation_time(TimeUtils.getDetailTimeStr());
				}
				// 通知
				noticeNumber(sellerBO.getSell_account(), numbersBO);
				gameDB.getUpdatePool().submit(new EntityRunnable<NumbersBO>(numbersBO) {
					@Override
					public void run(NumbersBO entity) {
						numberDAO.update(entity);
					}
				});
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 撤销修改卖家拥有数量(增加)
	 */
	@Override
	public void updateRevokeNumbers(SellerBO sellerBO, int count) {
		NumbersBO numbersBO = getNumbers(sellerBO.getSell_account(), sellerBO.getSell_type());
		Role role = getRoleByAccount(sellerBO.getSell_account());
		if (numbersBO != null) {
			Lock lock = CacheLockUtil.getLock(Number.class, numbersBO.getNum_id());
			try {
				lock.lock();
				updateBuyNumber(role.getRoleId(), count, sellerBO.getSell_type(), role, numbersBO);
				numbersBO.setNum_count(numbersBO.getNum_count() + count);
				numbersBO.setOperation_count(numbersBO.getOperation_count() + count);
				numbersBO.setOperation_time(TimeUtils.getDetailTimeStr());
				noticeNumber(sellerBO.getSell_account(), numbersBO);
				loggerinfo(role,
						"撤销之后用户帐号" + role.getAccount() + "用户金额" + role.getRole_rmbA() + "撤销用户帐号"
								+ sellerBO.getSell_account() + "撤销的价格" + sellerBO.getSell_price() + "撤销类型"
								+ sellerBO.getSell_type() + "用户撤销之hou数据" + numbersBO.getNum_count());

				gameDB.getUpdatePool().submit(new EntityRunnable<NumbersBO>(numbersBO) {
					@Override
					public void run(NumbersBO entity) {
						numberDAO.update(entity);
					}
				});
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 操作其他数据库上架时减少数量
	 */
	public void updateDeleteCurrency(int roleId, String type, int count, NumbersBO numbersBO,String account) {
		curencyUserDao.jianUpdate(count, roleId, Integer.valueOf(type));
		deleteRecord(count, type, numbersBO,account);

	}

	/**
	 * 操作其他数据库 购买撤销添加数量
	 * 
	 * @param roleId
	 * @param count
	 * @param type
	 */
	public void updateBuyNumber(int roleId, int count, String type, Role role, NumbersBO numbersBO) {
		curencyUserDao.addUpdate(count, roleId, Integer.parseInt(type));
		insertRecord(count, type, role, numbersBO);
	}

	/**
	 * 撤销记录
	 * 
	 * @param count
	 * @param type
	 * @param role
	 * @param numbersBO
	 */
	private void insertRecord(int count, String type, Role role, NumbersBO numbersBO) {
		RecordBO recordBO = new RecordBO();
		recordBO.setCount(count);// 操作数量
		recordBO.setTime(TimeUtils.getDetailTimeStr());// 操作时间
		recordBO.setRmb(0.00);// 操作金额
		recordBO.setStart_count(numbersBO.getNum_count());// 原来数量
		recordBO.setEnd_count(numbersBO.getNum_count() + count);// 结束数量
		recordBO.setType(type);// 类型
		recordBO.setStart_rmb(role.getRole_rmbA());// 原来资金
		recordBO.setEnd_rmb(role.getRole_rmbA());// 结束资金
		recordBO.setAccount(role.getAccount());
		recordBO.setState(-1);
		gameDB.getInsertPool().submit(new EntityRunnable<RecordBO>(recordBO) {
			@Override
			public void run(RecordBO entity) {
				recordDao.insert(entity);
			}
		});
	}

	/**
	 * 上架减少
	 * 
	 * @param count
	 * @param type
	 * @param numbersBO
	 */
	private void deleteRecord(int count, String type, NumbersBO numbersBO,String account) {
		RecordBO recordBO = new RecordBO();
		recordBO.setCount(count);// 操作数量
		recordBO.setTime(TimeUtils.getDetailTimeStr());// 操作时间
		 recordBO.setRmb(0.00);// 操作金额
		recordBO.setStart_count(numbersBO.getNum_count());// 原来数量
		recordBO.setEnd_count(numbersBO.getNum_count() - count);// 结束数量
		recordBO.setType(type);// 类型
		recordBO.setAccount(account);
//		 recordBO.setStart_rmb(role.getRole_rmbA());//原来资金
//		 recordBO.setEnd_rmb(role.getRole_rmbA());//结束资金
		recordBO.setState(1);
		gameDB.getInsertPool().submit(new EntityRunnable<RecordBO>(recordBO) {
			@Override
			public void run(RecordBO entity) {
				recordDao.insert(entity);
			}
		});
	}

	/**
	 * 数量改变通知
	 */
	private void noticeNumber(String account, NumbersBO num) {
		Role sellRole = getRoleByAccount(account);
		GeneratedMessage sc = SC.newBuilder().setSCNumberNotice(SCNumberNotice.newBuilder().setAccount(account)
				.setCount(num.getNum_count()).setRmbA(String.valueOf(sellRole.getRole_rmbA()))).build();
		SessionUtils.sc(sellRole.getRoleId(), sc);
	}
}
