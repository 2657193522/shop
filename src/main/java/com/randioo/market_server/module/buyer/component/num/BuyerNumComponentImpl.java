package com.randioo.market_server.module.buyer.component.num;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.NumberCache;
import com.randioo.market_server.dao.NumberDAO;
import com.randioo.market_server.dao.RecordDAO;
import com.randioo.market_server.dao_remote.CurencyUserDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.RecordBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.module.buyer.component.role.BuyerRoleComponent;
import com.randioo.market_server.protocol.Gm.SCNumberNotice;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.utils.SessionUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("buyerNumComponent")
public class BuyerNumComponentImpl extends ObserveBaseService implements BuyerNumComponent {
	@Autowired
	private NumberDAO numberDAO;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private BuyerRoleComponent buyerRoleComponent;
	@Autowired
	private CurencyUserDAO curencyUserDao;
	@Autowired
	private RecordDAO recordDao;

	/**
	 * 交易买家改变数量
	 */
	@Override
	public void updateNumber(String sellAccount, String buyAccount,TradingBO tradingBO) {
		// TODO Auto-generated method stub
		Role buyRole = buyerRoleComponent.getRole(buyAccount);
		Role sellRole = buyerRoleComponent.getRole(sellAccount);
		NumbersBO buyNum = getNumber(buyAccount, tradingBO.getTrad_type());
		NumbersBO sellNum = getNumber(sellAccount, tradingBO.getTrad_type());
		if (buyNum != null) {
			tradingBO.setTrad_before_count(buyNum.getNum_count());
			// 修改其他数据库
			updateBuyNumber(buyRole.getRoleId(), tradingBO.getTrad_count(), tradingBO.getTrad_type(), tradingBO.getTrad_sum(), buyRole, buyNum);
			buyNum.setNum_count(buyNum.getNum_count() +  tradingBO.getTrad_count());
			buyNum.setOperation_count(buyNum.getOperation_count() +  tradingBO.getTrad_count());
			buyNum.setOperation_time(TimeUtils.getDetailTimeStr());
			// 通知
			noticeNumber(sellRole, buyRole, buyNum, sellNum);
			tradingBO.setTrad_after_count(buyNum.getNum_count());
			loggerinfo(buyRole, "购买之后的用户信息:" + ",用户金额:" + buyRole.getRole_rmbA() + ",用户帐号:" + buyRole.getAccount()
					+ "用户数量" + buyNum.getNum_count());
			loggerinfo(sellRole, "出售之后的用户信息:" + ",用户金额:" + sellRole.getRole_rmbA() + ",用户帐号:" + sellRole.getAccount()
					+ ",用户数量:" + sellNum.getNum_count());
			gameDB.getUpdatePool().submit(new EntityRunnable<NumbersBO>(buyNum) {
				@Override
				public void run(NumbersBO entity) {
					numberDAO.update(entity);
				}
			});
		}
	}

	public void updateBuyNumber(int roleId, int count, String type, Double price, Role role, NumbersBO numbersBO) {
		curencyUserDao.addUpdate(count, roleId, Integer.parseInt(type));
		insertRecord(count, type, role, numbersBO, price);

	}

	private void insertRecord(int count, String type, Role role, NumbersBO numbersBO, double price) {
		RecordBO recordBO = new RecordBO();
		recordBO.setCount(count);// 操作数量
		recordBO.setTime(TimeUtils.getDetailTimeStr());// 操作时间
		recordBO.setRmb(price);// 操作金额
		recordBO.setStart_count(numbersBO.getNum_count());
		recordBO.setEnd_count(numbersBO.getNum_count() + count);
		recordBO.setType(type);
		recordBO.setStart_rmb(role.getRole_rmbA() + price);
		recordBO.setEnd_rmb(role.getRole_rmbA());
		recordBO.setAccount(role.getAccount());
		recordBO.setState(0);
		gameDB.getInsertPool().submit(new EntityRunnable<RecordBO>(recordBO) {
			@Override
			public void run(RecordBO entity) {
				recordDao.insert(entity);
			}
		});
	}

	/**
	 * 根据帐号类型获取用户拥有数量
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	@Override
	public NumbersBO getNumber(String account, String type) {
		Role role = buyerRoleComponent.getRole(account);
		String accountType = String.valueOf(role.getRoleId()) + type;
		NumbersBO num = NumberCache.getNumber(accountType);
		if (num == null) {
			num = numberDAO.selectNumber(role.getRoleId(), type);
			if (num != null) {
				NumberCache.getNumberMap().put(accountType, num);
			}
		}
		return num;
	}

	/**
	 * 通知买家卖家数量变化
	 * 
	 * @param role
	 * @param sellAccount
	 * @param type
	 */
	public void noticeNumber(Role sellRole, Role buyRole, NumbersBO buyNum, NumbersBO sellNum) {

		GeneratedMessage buy = SC.newBuilder()
				.setSCNumberNotice(SCNumberNotice.newBuilder().setAccount(buyRole.getAccount())
						.setCount(buyNum.getNum_count()).setRmbA(String.format("%.2f", buyRole.getRole_rmbA())))
				.build();
		GeneratedMessage sell = SC.newBuilder()
				.setSCNumberNotice(SCNumberNotice.newBuilder().setAccount(sellRole.getAccount())
						.setCount(sellNum.getNum_count()).setRmbA(String.format("%.2f", sellRole.getRole_rmbA())))
				.build();
		SessionUtils.sc(buyRole.getRoleId(), buy);
		SessionUtils.sc(sellRole.getRoleId(), sell);
	}
}
