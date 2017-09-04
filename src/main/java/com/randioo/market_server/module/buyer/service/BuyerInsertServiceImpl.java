package com.randioo.market_server.module.buyer.service;

import java.util.concurrent.locks.Lock;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.dao.BuyerDAO;
import com.randioo.market_server.dao.ControlDAO;
import com.randioo.market_server.dao_remote.CurencyUserDAO;
import com.randioo.market_server.entity.bo.BuyerBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo_remote.CurencyUser;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.buyer.component.role.BuyerLevelComponent;
import com.randioo.market_server.module.buyer.component.trading.InsertTradingComponent;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.protocol.Buyer;
import com.randioo.market_server.protocol.Buyer.BuyerResponse;
import com.randioo.market_server.protocol.Entity.BuyerData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.market_server.util.MD5Util;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.db.IdClassCreator;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.utils.SessionUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("buyerInsertService")
public class BuyerInsertServiceImpl extends ObserveBaseService implements BuyerInsertService {
	@Autowired
	private BuyerDAO buyerDAO;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private IdClassCreator idClassCreator;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumService;
	@Autowired
	private Role roleChange;
	@Autowired
	private ControlDAO controlDAO;
	@Autowired
	private BuyerLevelComponent buyerLevelComponent;
	@Autowired
	private InsertTradingComponent insertTradingComponent;
	@Autowired
	private CurencyUserDAO curencyUserDAO;

	public void insertBuyer(BuyerData buyerData, String buyAccount, IoSession session) {
		Role role = sellerLogicNumService.getRoleByAccount(buyAccount);
		if (role == null) {
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(BuyerResponse.newBuilder().setErrorCode(ErrorCode.NO_ROLE_DATA.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		// 判断是否是自己的信息
		if (buyerData.getSellAccount().equals(buyAccount)) {
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(
							BuyerResponse.newBuilder().setErrorCode(ErrorCode.NOBUY_MYSELF_MESSAGE.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		CurencyUser curencyUser=curencyUserDAO.getCurrency(role.getRoleId(), Integer.parseInt(buyerData.getType()));
		if(curencyUser==null){
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(
							BuyerResponse.newBuilder().setErrorCode(ErrorCode.INFORMATION_IS_NOT.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		// 按钮控制
		if (controlDAO.selectId(Constant.YES) == 1) {
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(BuyerResponse.newBuilder().setErrorCode(ErrorCode.BUTTON_FAILURE.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		// 判断服务器是个开启，(开启为OK)
		if (!roleChange.isJudge()) {
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(BuyerResponse.newBuilder().setErrorCode(ErrorCode.CLOSE_ERROR.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		// 验证交易密码是否OK
		if (role.getVip_level() == 0) {
			if (!MD5Util.string2MD5(buyerData.getPWD()).equals(role.getPassWord())) {
				GeneratedMessage sc = SC.newBuilder()
						.setBuyerResponse(
								BuyerResponse.newBuilder().setErrorCode(ErrorCode.PASSWORD_MISTAKE.getNumber()))
						.build();
				SessionUtils.sc(session, sc);
				return;
			}
		}
		// 判断金额
		double money = DateUtils.changeSum(buyerData.getBuyCount(), Double.parseDouble(buyerData.getBuyPrice()));
		if (role.getRole_rmbA() < money) {
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(
							BuyerResponse.newBuilder().setErrorCode(ErrorCode.NOT_SUFFICIENT_FUNDS.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		// 判断数量
		int i = buyerLevelComponent.getLevel(role.getAccount(), buyerData.getType(), buyerData.getSellId(),
				buyerData.getBuyCount());

		if (i == Constant.NO) {
			// 交易数量超额
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(BuyerResponse.newBuilder().setErrorCode(ErrorCode.EXCESS_QUANTITY.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		} else if (i == Constant.YES) {
			// 输入数量不正确
			GeneratedMessage sc = SC.newBuilder()
					.setBuyerResponse(
							BuyerResponse.newBuilder().setErrorCode(ErrorCode.INCORRECT_INPUT_QUANTYTY.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		Lock roleLock = CacheLockUtil.getLock(Role.class, role.getRoleId());
		try {
			roleLock.lock();
			getSetBuyerMessage(buyerData, buyAccount, session);
		} finally {
			roleLock.unlock();
		}
	}

	private void getSetBuyerMessage(BuyerData buyerData, String buyAccount, IoSession session) {
		BuyerBO buyerBO = new BuyerBO();
		int buyId = idClassCreator.getId(Buyer.class);
		if (buyerData != null) {
			buyerBO.setBuy_id(buyId);
			buyerBO.setBuy_account(buyAccount);
			buyerBO.setBuy_price(Double.parseDouble(buyerData.getBuyPrice()));
			buyerBO.setBuy_count(buyerData.getBuyCount());
			buyerBO.setBuy_type(buyerData.getType());
			buyerBO.setBuy_time(TimeUtils.getDetailTimeStr());
			// loggerinfo(role,"进来生成买的信息:" + ",订单号:" + buyId + ",购买的用户:" +
			// role.getAccount() + ",购买价格:" + buyerData.getBuyPrice()
			// + ",购买类型:" + buyerData.getType() + ",购买数量:" +
			// buyerData.getBuyCount() + ",购买时间:"
			// + TimeUtils.getDetailTimeStr());
			// loggerinfo(role, "未成交前的购买用户的信息:" + ",用户金额:" + role.getRole_rmbA()
			// + ",用户帐号:" + role.getAccount());
			gameDB.getInsertPool().submit(new EntityRunnable<BuyerBO>(buyerBO) {
				@Override
				public void run(BuyerBO entity) {
					buyerDAO.insert(entity);
				}
			});
		}
		insertTradingComponent.insertTrading(buyerBO, buyerData.getSellId(), session, buyerData.getSellAccount());

	}
}
