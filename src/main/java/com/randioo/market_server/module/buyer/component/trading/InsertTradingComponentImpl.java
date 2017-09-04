package com.randioo.market_server.module.buyer.component.trading;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.cache.local.TradingCache;
import com.randioo.market_server.dao.GoodsDAO;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.dao.TradingDAO;
import com.randioo.market_server.entity.bo.BuyerBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.buyer.component.num.BuyerNumComponent;
import com.randioo.market_server.module.buyer.component.role.BuyerRoleComponent;
import com.randioo.market_server.module.buyer.component.seller.SellerCacheComponent;
import com.randioo.market_server.module.sc.service.RefushMinkService;
import com.randioo.market_server.module.sc.service.ScService;
import com.randioo.market_server.module.seller.service.SellerDeleteService;
import com.randioo.market_server.module.seller.service.SellerSelectService;
import com.randioo.market_server.protocol.Buyer.BuyerResponse;
import com.randioo.market_server.protocol.Entity.TradingData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.Gm.SCNotice;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.db.IdClassCreator;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.utils.SessionUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("insertTradingComponent")
public class InsertTradingComponentImpl extends ObserveBaseService implements InsertTradingComponent {
	@Autowired
	private TradingDAO tradingDAO;
	@Autowired
	private IdClassCreator idClassCreator;
	@Autowired
	private SellerDAO sellerDAO;
	@Autowired
	private SellerDeleteService sellerDeleteService;
	@Autowired
	private BuyerRoleComponent buyerRoleComponent;
	@Autowired
	private BuyerNumComponent buyerNumComponent;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private SellerCacheComponent sellerCacheComponent;
	@Autowired
	private SellerSelectService sellerSelectService;
	@Autowired
	private ScService scService;
	@Autowired
	private RefushMinkService refushMinkService;
	@Autowired
	private GoodsDAO goodsDao;

	@Override
	public void init() {
		Integer tradeId = tradingDAO.selectId();
		tradeId = tradeId == null ? 0 : tradeId;
		idClassCreator.initId(TradingBO.class, tradeId);
	}

	@Override
	public void insertTrading(BuyerBO buyerBo, int sellId, IoSession session, String sellAccount) {
		// TODO Auto-generated method stub
		Lock lock = CacheLockUtil.getLock(SellerBO.class, sellId);
		BuyerResponse.Builder builder = BuyerResponse.newBuilder();
		TradingBO tradingBO = null;
		Set<SellerBO> delSet = new HashSet<>();
		try {
			lock.lock();
			List<SellerBO> sellerListBySellId = getSellerListByAll(sellId);
			int buyCount = buyerBo.getBuy_count();
			if (sellerListBySellId != null) {
				for (SellerBO seller : sellerListBySellId) {
					int count = seller.getSell_count() - seller.getSell_overCount();
					if (buyCount > count) {
						GeneratedMessage sc = SC.newBuilder().setBuyerResponse(
								BuyerResponse.newBuilder().setErrorCode(ErrorCode.INFORMATION_IS_NOT.getNumber()))
								.build();
						SessionUtils.sc(session, sc);
						return;
					}
					tradingBO = new TradingBO();
					int id = idClassCreator.getId(TradingBO.class);
					// 设置买的成交单
					tradingBO = setBuyTrading(seller, tradingBO, buyerBo, id);
					// 计算数目成交
					count = this.matchCount(seller, tradingBO, buyCount, delSet);
					// 修改商品详情
					Role buyRole = buyerRoleComponent.getRole(buyerBo.getBuy_account());
					goodsDao.updateGoodsBuyer(String.valueOf(buyRole.getRoleId()), Constant.NO, id,
							TimeUtils.getDetailTimeStr(), sellId, Constant.YES, buyerBo.getBuy_count());
					// 改变相关缓存数据
					sellerCacheComponent.deleteSeller(seller);
					// 修改出售信息，更新数据库
					sellerDeleteService.updateSeller(seller);
					// // 修改买家用户金额
					// buyerRoleComponent.updateBuyerRole(buyerBo.getBuy_account(),
					// tradingBO);
					// // 修改买家的用户数量
					// buyerNumComponent.updateNumber(seller.getSell_account(),
					// buyerBo.getBuy_account(),tradingBO);

					// 通知发生交易信息改变
					this.noticeSeller(buyerBo.getBuy_account(), tradingBO.getTrad_count(), seller.getSell_account());
					// 生成成交数据生成K线
					notifyObservers(Constant.NOTICE, tradingBO);
					//
					// 主推买家卖家委托信息
					// sellerSelectService.scMySellerRequesr(seller.getSell_account());
					// sellerSelectService.scMySellerRequesr(buyerBo.getBuy_account());

					// 加入缓存
					addTradingList(tradingBO);
					TradingData.Builder tradBuilder = TradingData.newBuilder();
					builder.addTradingData(tradBuilder.setBuyAccount(tradingBO.getTrad_account())
							.setBuyId(tradingBO.getTrad_buy_id()).setMoney((int) (tradingBO.getTrad_poundage() * 100))
							.setTradCount(tradingBO.getTrad_count()).setSellAccount(seller.getSell_account())
							.setTradPrice((int) (tradingBO.getTrad_price() * 100))
							.setTradSum((int) (tradingBO.getTrad_sum() * 100)).setTradTime(tradingBO.getTrad_time())
							.setTradId(tradingBO.getTrad_id()));

					buyerRoleComponent.updateBuyerRole(buyerBo.getBuy_account(), tradingBO);
					TradingBO tradingSeller = insertTradingSeller(tradingBO, seller.getSell_account());
					buyerRoleComponent.updateSellerRole(seller.getSell_account(), tradingSeller);
					insertSellerTrading(tradingSeller);
					gameDB.getInsertPool().submit(new EntityRunnable<TradingBO>(tradingBO) {
						@Override
						public void run(TradingBO entity) {
							tradingDAO.insert(entity);
						}
					});
					// 修改买家用户金额
					// buyerRoleComponent.updateBuyerRole(buyerBo.getBuy_account(),
					// tradingBO);
					// 修改买家的用户数量
					buyerNumComponent.updateNumber(seller.getSell_account(), buyerBo.getBuy_account(), tradingBO);
					// 主推买家卖家委托信息
					sellerSelectService.scMySellerRequesr(seller.getSell_account());
					sellerSelectService.scMySellerRequesr(buyerBo.getBuy_account());
				}
			}
			sellerListBySellId.removeAll(delSet);
			SessionUtils.sc(session, SC.newBuilder().setBuyerResponse(builder).build());
			scService.scGoodsTy(buyerBo.getBuy_type());// 主推商品详细信息
			refushMinkService.refushMink(buyerBo.getBuy_type());
			Role role = buyerRoleComponent.getRole(buyerBo.getBuy_account());
			loggerinfo(role,
					"购买到的用户:" + role.getAccount() + ",购买价格:" + buyerBo.getBuy_price() + ",购买类型:" + buyerBo.getBuy_type()
							+ ",购买时间:" + tradingBO.getTrad_time() + ",购买数量:" + buyerBo.getBuy_count() + ",购买订单号:"
							+ buyerBo.getBuy_id());

			loggerinfo(role,
					"购买的用户成交单:" + ",成交单号:" + tradingBO.getTrad_id() + ",购买订单号:" + tradingBO.getTrad_buy_id() + "成交价格"
							+ tradingBO.getTrad_price() + ",成交数量:" + tradingBO.getTrad_count() + ",成交类型:"
							+ tradingBO.getTrad_type() + ",成交时间:" + tradingBO.getTrad_time() + ",成交总价:"
							+ tradingBO.getTrad_sum() + ",购买用户:" + tradingBO.getTrad_account());

		} finally {
			lock.unlock();
		}
	}

	/**
	 * 设置卖的成交信息（买是0，卖是1）YES是1，NO是0
	 * 
	 * @param tradingBO
	 * @param sell_account
	 */
	private TradingBO insertTradingSeller(TradingBO tradingBO, String sell_account) {
		TradingBO trading = new TradingBO();
		int id = idClassCreator.getId(TradingBO.class);
		trading.setTrad_id(id);
		trading.setTrad_buy_id(tradingBO.getTrad_buy_id());
		trading.setTrad_account(sell_account);
		trading.setTrad_time(TimeUtils.getDetailTimeStr());
		trading.setTrad_price(tradingBO.getTrad_price());
		trading.setTrad_type(tradingBO.getTrad_type());
		trading.setTrad_sum(tradingBO.getTrad_sum());
		trading.setTrad_poundage(tradingBO.getTrad_sum() * 0.002);
		trading.setTrad_count(tradingBO.getTrad_count());
		trading.setTrad_state(Constant.YES);
		// 修改卖家用户金额
		// NumbersBO numbersBO=buyerNumComponent.getNumber(sell_account,
		// tradingBO.getTrad_type());
		// 修改卖家用户金额
		// buyerRoleComponent.updateSellerRole(sell_account,trading);

		Role role2 = buyerRoleComponent.getRole(sell_account);
		loggerinfo(role2,
				"出售的用户成交单:" + ",成交单号:" + trading.getTrad_id() + ",购买订单号:" + trading.getTrad_buy_id() + ",成交价格:"
						+ trading.getTrad_price() + ",成交数量:" + trading.getTrad_count() + ",成交类型:"
						+ trading.getTrad_type() + ",成交时间:" + trading.getTrad_time() + ",成交总价:" + trading.getTrad_sum()
						+ ",购买用户:" + trading.getTrad_account());

		// System.out.println(System.currentTimeMillis());
		addTradingList(trading);
		return trading;
	}

	private void insertSellerTrading(TradingBO trading) {
		gameDB.getInsertPool().submit(new EntityRunnable<TradingBO>(trading) {

			@Override
			public void run(TradingBO entity) {
				tradingDAO.insert(entity);
			}
		});
	}

	/**
	 * 比较数目
	 * 
	 */
	private int matchCount(SellerBO sellerBO, TradingBO tradingBO, int buyCount, Set<SellerBO> delSet) {
		// GainsConfig config = GainsConfigCache.getConfig();
		if (sellerBO.getSell_count() - sellerBO.getSell_overCount() >= buyCount) {
			tradingBO.setTrad_count(buyCount);// 设置交易数量
			tradingBO.setTrad_sum(DateUtils.changeSum(buyCount, sellerBO.getSell_price()));// 设置交易总价
			tradingBO.setTrad_poundage(tradingBO.getTrad_sum() * 0.002);
			// 卖家设置成交数量
			sellerBO.setSell_overCount(sellerBO.getSell_overCount() + buyCount);
			// 当成交数量和出售数量同等时，设置卖单状态为停止交易
			if (sellerBO.getSell_overCount() >= sellerBO.getSell_count()) {
				sellerBO.setSell_active(Constant.STOP_SELL);
				delSet.add(sellerBO);
			}
		}
		return 0;
	}

	/**
	 * 通过所有获取Seller集合进行比较交易
	 * 
	 * @param buyerData
	 * @return
	 */
	private List<SellerBO> getSellerListByAll(int sellId) {
		String sellID = String.valueOf(sellId) + String.valueOf(Constant.START_SELL);
		List<SellerBO> sellerList = SellerCache.getListByAll(sellID);
		if (sellerList == null) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDAO.selectSellerByAll(sellId, Constant.START_SELL);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerByAll().put(sellID, sellerList);
		}
		return sellerList;
	}

	/**
	 * 设置成交信息
	 */
	private TradingBO setBuyTrading(SellerBO sellerBO, TradingBO tradingBO, BuyerBO buyerBO, int tradId) {
		tradingBO.setTrad_id(tradId);
		tradingBO.setTrad_buy_id(buyerBO.getBuy_id());
		tradingBO.setTrad_account(buyerBO.getBuy_account());
		tradingBO.setTrad_time(TimeUtils.getDetailTimeStr());
		tradingBO.setTrad_price(sellerBO.getSell_price());
		tradingBO.setTrad_type(buyerBO.getBuy_type());
		tradingBO.setTrad_state(Constant.STOP_SELL);
		return tradingBO;
	}

	/**
	 * 添加到交易缓存
	 * 
	 * @param trading
	 */
	private void addTradingList(TradingBO trading) {
		String typeState = trading.getTrad_type() + String.valueOf(trading.getTrad_state());
		List<TradingBO> list = TradingCache.getTradingList(typeState);
		if (list == null) {
			list = new ArrayList<>();
			List<TradingBO> tradList = tradingDAO.getAllTrading(trading.getTrad_type(), Constant.NO);
			if (tradList != null) {
				list.addAll(tradList);
			}
			TradingCache.getTradingMap().put(typeState, list);
		}
		list.add(trading);
	}

	/**
	 * 通知买家卖家交易生成数量
	 * 
	 * @param role
	 * @param trading
	 */
	private void noticeSeller(String buyAccount, int count, String sellAccount) {
		Role buyRole = buyerRoleComponent.getRole(buyAccount);
		Role sellRole = buyerRoleComponent.getRole(sellAccount);
		GeneratedMessage buy = SC.newBuilder().setSCNotice(SCNotice.newBuilder().setNotice(count)).build();
		GeneratedMessage sell = SC.newBuilder().setSCNotice(SCNotice.newBuilder().setNotice(count)).build();
		SessionUtils.sc(buyRole.getRoleId(), buy);
		SessionUtils.sc(sellRole.getRoleId(), sell);
	}

}
