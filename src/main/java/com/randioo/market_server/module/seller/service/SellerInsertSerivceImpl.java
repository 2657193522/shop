package com.randioo.market_server.module.seller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.file.CountConfigCache;
import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.dao.ControlDAO;
import com.randioo.market_server.dao.GoodsDAO;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.file.CountConfig;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.sc.service.ScService;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.protocol.Entity.SellerData;
import com.randioo.market_server.protocol.Entity.SellerInfoData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.Seller.SellerResponse;
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

@Service("sellerInsertService")
public class SellerInsertSerivceImpl extends ObserveBaseService implements SellerInsertService {
	@Autowired
	private SellerDAO sellerDAO;
	@Autowired
	private IdClassCreator IdClassCreator;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumService;
	@Autowired
	private Role roleChange;
	@Autowired
	private ControlDAO controlDAO;
	@Autowired
	private SellerSelectService sellerSelectService;
	@Autowired
	private ScService scService;
	@Autowired
	private GoodsDAO goodsDao;

	@Override
	public void init() {
		Integer maxId = sellerDAO.selectId();
		IdClassCreator.initId(SellerBO.class, maxId == null ? 1000000000 : maxId);
	}

	/**
	 * 上架出售信息
	 * 
	 * @author wss 2017-8-1 11:07
	 */
	@Override
	public void insertSeller(String sellAccount, SellerInfoData sellerInfoData, IoSession session) {
		if (controlDAO.selectId(Constant.YES) == 1) {
			GeneratedMessage sc = SC.newBuilder()
					.setSellerResponse(SellerResponse.newBuilder().setErrorCode(ErrorCode.BUTTON_FAILURE.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;

		}

		// 判断服务器是个开启，(开启为OK)
		if (!roleChange.isJudge()) {
			GeneratedMessage sc = SC.newBuilder()
					.setSellerResponse(SellerResponse.newBuilder().setErrorCode(ErrorCode.CLOSE_ERROR.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		Role role = sellerLogicNumService.getRoleByAccount(sellAccount);
		if (role == null) {
			GeneratedMessage sc = SC.newBuilder()
					.setSellerResponse(SellerResponse.newBuilder().setErrorCode(ErrorCode.NO_ROLE_DATA.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		// 验证价格尾数（0或者5为OK）
		String d = DateUtils.getEnd(sellerInfoData.getPrice());
		if (!d.equals("5")) {
			if (!d.equals("0")) {
				GeneratedMessage sc = SC.newBuilder()
						.setSellerResponse(SellerResponse.newBuilder().setErrorCode(ErrorCode.MATH_ERROR.getNumber()))
						.build();
				SessionUtils.sc(session, sc);
				return;
			}
		}

		// 验证输入数量(0到自己拥有的数量为OK)
		NumbersBO numbersBO = sellerLogicNumService.getNumbers(sellAccount, sellerInfoData.getType());
		if (numbersBO.getNum_count() < sellerInfoData.getCount() || sellerInfoData.getCount() < 0) {
			GeneratedMessage sc = SC.newBuilder()
					.setSellerResponse(
							SellerResponse.newBuilder().setErrorCode(ErrorCode.INCORRECT_INPUT_QUANTYTY.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}

		// 验证交易密码是否OK
		if (role.getVip_level() == 0) {
			if (!MD5Util.string2MD5(sellerInfoData.getPWD()).equals(role.getPassWord())) {
				GeneratedMessage sc = SC.newBuilder()
						.setSellerResponse(
								SellerResponse.newBuilder().setErrorCode(ErrorCode.PASSWORD_MISTAKE.getNumber()))
						.build();
				SessionUtils.sc(session, sc);
				return;
			}
		}

		// 控制成交数量(一周开始拥有数量正负1600，单笔输入不超1600)
		CountConfig countConfig = CountConfigCache.getConfig();
		if (role.getVip_level() == 0) {
			if (numbersBO.getOperation_time() != null) {
				if (!numbersBO.getOperation_time().equals("0")) {
					long time = DateUtils.getLongTime(numbersBO.getOperation_time());
					if (!DateUtils.isThisWeek(time)) {
						numbersBO.setOperation_count(0);
					}
				}
			} else {
				numbersBO.setOperation_count(0);
			}

			if (numbersBO != null) {
				if (numbersBO.getNum_count() < sellerInfoData.getCount() || sellerInfoData.getCount() < 0
						|| sellerInfoData.getCount() > countConfig.count) {
					GeneratedMessage sc = SC.newBuilder().setSellerResponse(
							SellerResponse.newBuilder().setErrorCode(ErrorCode.INCORRECT_INPUT_QUANTYTY.getNumber()))
							.build();
					SessionUtils.sc(session, sc);
					return;
				}
				// 控制数量
				int c = numbersBO.getOperation_count() - sellerInfoData.getCount();
				if (Math.abs(c) > countConfig.count) {
					GeneratedMessage sc = SC.newBuilder()
							.setSellerResponse(
									SellerResponse.newBuilder().setErrorCode(ErrorCode.EXCESS_QUANTITY.getNumber()))
							.build();
					SessionUtils.sc(session, sc);
					return;
				}
			}
		} else if (role.getVip_level() == 1) {
			if (numbersBO != null) {
				if (numbersBO.getNum_count() < sellerInfoData.getCount() || sellerInfoData.getCount() < 0
						|| sellerInfoData.getCount() > countConfig.count) {
					GeneratedMessage sc = SC.newBuilder().setSellerResponse(
							SellerResponse.newBuilder().setErrorCode(ErrorCode.INCORRECT_INPUT_QUANTYTY.getNumber()))
							.build();
					SessionUtils.sc(session, sc);
					return;
				}
			}
		}

		// TODO Auto-generated method stub
		// 上架出售信息
		Lock roleLock = CacheLockUtil.getLock(Role.class, role.getRoleId());
		try {
			roleLock.lock();
			getInsertSeller(role.getAccount(), sellerInfoData, session);
		} finally {
			roleLock.unlock();
		}
	}

	/**
	 * 上架出售信息
	 * 
	 * @param sellAccount
	 * @param sellerInfoData
	 * @return
	 * @author wss 2017-8-1
	 */
	private void getInsertSeller(String sellAccount, SellerInfoData sellerInfoData, IoSession session) {
		SellerResponse.Builder builder = SellerResponse.newBuilder();
		SellerBO seller = null;
		Role role = sellerLogicNumService.getRoleByAccount(sellAccount);
		loggerinfo(role,
				"上架之前用户帐号" + role.getAccount() + "用户金额" + role.getRole_rmbA() + "上架用户帐号" + sellAccount + "用户上架数量"
						+ sellerInfoData.getCount() + "上架的价格" + sellerInfoData.getPrice() + "上架类型"
						+ sellerInfoData.getType());

		int sellId = IdClassCreator.getId(SellerBO.class);
		double sum = 0.00;
		if (sellerInfoData != null) {
			seller = setSellerInfo(sellAccount, sellerInfoData, sellId);
			// 改变商品详情属性
			updateGoodsSeller(seller);
			// 改变数量
			sellerLogicNumService.updateNumbers(seller, role.getRoleId());
			addSellerCache(seller);
			gameDB.getInsertPool().submit(new EntityRunnable<SellerBO>(seller) {
				@Override
				public void run(SellerBO entity) {
					sellerDAO.insert(entity);
				}
			});
		}

		builder.setSellerData(SellerData.newBuilder().setSellId(sellId).setSellAccount(sellAccount)
				.setPrice((int) (seller.getSell_price() * 100)).setSellCount(sellerInfoData.getCount())
				.setSum((int) (sum * 100)).setSellTime(seller.getSell_time()));
		SessionUtils.sc(session, SC.newBuilder().setSellerResponse(builder).build());
		scService.scGoodsTy(seller.getSell_type());
		sellerSelectService.scMySellerRequesr(sellAccount);
		loggerinfo(role,
				"上架之后用户帐号" + role.getAccount() + "用户金额" + role.getRole_rmbA() + "上架用户帐号" + sellAccount + "用户上架数量"
						+ sellerInfoData.getCount() + "上架的价格" + sellerInfoData.getPrice() + "上架类型"
						+ sellerInfoData.getType());

	}

	private void updateGoodsSeller(SellerBO seller) {
		gameDB.getInsertPool().submit(new EntityRunnable<SellerBO>(seller) {
			@Override
			public void run(SellerBO seller) {
				Role role = sellerLogicNumService.getRoleByAccount(seller.getSell_account());
				goodsDao.updateGoodsSeller(seller.getSell_price(), seller.getSell_id(), seller.getSell_active(),
						TimeUtils.getDetailTimeStr(), seller.getSell_type(), String.valueOf(role.getRoleId()),
						Constant.NO, seller.getSell_count());
			}
		});
	}

	/**
	 * 设置出售信息
	 * 
	 * @param sellAccount
	 * @param sellerInfoData
	 * @param sellId
	 * @return
	 * @author wss 2017-8-1
	 */
	private SellerBO setSellerInfo(String sellAccount, SellerInfoData sellerInfoData, int sellId) {
		SellerBO seller = new SellerBO();
		seller.setSell_id(sellId);
		seller.setSell_account(sellAccount);
		seller.setSell_count(sellerInfoData.getCount());
		seller.setSell_price(Double.parseDouble(sellerInfoData.getPrice()));
		seller.setSell_type(sellerInfoData.getType());
		seller.setSell_overCount(0);// 成交数
		seller.setSell_active(Constant.START_SELL);
		// 设置总价
		seller.setSell_sum(
				DateUtils.changeSum(sellerInfoData.getCount(), Double.parseDouble(sellerInfoData.getPrice())));
		seller.setSell_time(TimeUtils.getDetailTimeStr());
		return seller;
	}

	private void addSellerCache(SellerBO sellerBO) {
		addSellerByType(sellerBO);
		addSellerBySellId(sellerBO);
		addSellerByAccount(sellerBO);
	}

	// /**
	// * 操作其他数据库
	// */
	// public void updateDeleteCurrency(int roleId, String type, int count) {
	// curencyUserDao.jianUpdate(count, roleId, Integer.valueOf(type));
	// }

	/**
	 * 类型状态查找出售信息
	 */
	private void addSellerByType(SellerBO sellerBO) {
		String types = sellerBO.getSell_type() + Constant.START_SELL;
		List<SellerBO> sellerListByType = SellerCache.getListByType(types);
		if (sellerListByType == null) {
			sellerListByType = new ArrayList<>();
			List<SellerBO> listByType = sellerDAO.ListByType(sellerBO.getSell_type(), Constant.START_SELL);
			if (listByType != null) {
				sellerListByType.addAll(listByType);
			}
			SellerCache.getSellerByType().put(types, sellerListByType);
		}
		sellerListByType.add(sellerBO);
	}

	/**
	 * 卖的Id查找出售信息
	 */
	private void addSellerBySellId(SellerBO sellerBO) {
		String sellActive = String.valueOf(sellerBO.getSell_id()) + Constant.START_SELL;
		List<SellerBO> sellerListBySellId = SellerCache.getListByAll(sellActive);
		if (sellerListBySellId == null) {
			sellerListBySellId = new ArrayList<>();
			List<SellerBO> listBySellId = sellerDAO.selectSellerByAll(sellerBO.getSell_id(), Constant.START_SELL);
			if (listBySellId != null) {
				sellerListBySellId.addAll(listBySellId);
			}
			SellerCache.getSellerByType().put(sellActive, sellerListBySellId);
		}
		sellerListBySellId.add(sellerBO);
	}

	/**
	 * 用户帐号状态查找出售信息
	 */
	private void addSellerByAccount(SellerBO sellerBO) {
		String accountActive = sellerBO.getSell_account() + Constant.START_SELL;
		List<SellerBO> sellerListByAccount = SellerCache.getSellerListByAccount(accountActive);
		if (sellerListByAccount == null) {
			sellerListByAccount = new ArrayList<>();
			List<SellerBO> listByAccount = sellerDAO.getListByAccount(sellerBO.getSell_account(), Constant.START_SELL);
			if (listByAccount != null) {
				sellerListByAccount.addAll(listByAccount);
			}
			SellerCache.getSellerByType().put(accountActive, sellerListByAccount);
		}
		sellerListByAccount.add(sellerBO);
	}

	// public void scMySellerMessage(){
	// SCSellerMyMessage.Builder builder=SCSellerMyMessage.newBuilder();
	//
	// }

}
