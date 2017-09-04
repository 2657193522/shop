package com.randioo.market_server.module.seller.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.dao.ControlDAO;
import com.randioo.market_server.dao.GoodsDAO;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.sc.service.ScService;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.Gm.SCNumberNotice;
import com.randioo.market_server.protocol.Seller.RevokeSellerResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("sellerDeleteService")
public class SellerDeleteServiceImpl extends ObserveBaseService implements SellerDeleteService {
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private ControlDAO controlDAO;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumService;
	@Autowired
	private SellerSelectService sellerSelectService;
	@Autowired
	private ScService scService;
	@Autowired
	private GoodsDAO goodsDao;

	/**
	 * 撤销出售信息
	 * 
	 * @author wss 2017-8-1 11:06:00
	 */
	@Override
	public void deleteSeller(String type, String price, String sellAccount, int sellId, IoSession session) {
		// TODO Auto-generated method stub
		if (controlDAO.selectId(Constant.YES) == 1) {
			GeneratedMessage sc = SC.newBuilder()
					.setRevokeSellerResponse(
							RevokeSellerResponse.newBuilder().setErrorCode(ErrorCode.BUTTON_FAILURE.getNumber()))
					.build();
			SessionUtils.sc(session, sc);
			return;
		}
		Role role = sellerLogicNumService.getRoleByAccount(sellAccount);
		NumbersBO num = sellerLogicNumService.getNumbers(sellAccount, type);
		loggerinfo(role, "撤销之前用户帐号" + role.getAccount() + "用户金额" + role.getRole_rmbA() + "撤销用户帐号" + sellAccount
				+ "撤销的价格" + price + "撤销类型" + type + "用户撤销之前数据" + num.getNum_count());
		Lock lock = CacheLockUtil.getLock(SellerBO.class, sellId);
		try {
			lock.lock();
			Set<SellerBO> deleteSet = new HashSet<>();
			// 状态为正在出售（1）
			List<SellerBO> list = getSellerListByAll(sellId, Constant.START_SELL);
			List<SellerBO> sellerAccount = getSellerByAccount(sellAccount, Constant.START_SELL);
			List<SellerBO> sellerListByType = deleteSellerType(type);
			if (list != null) {
				for (SellerBO seller : list) {
					if (seller.getSell_count() - seller.getSell_overCount() <= 0) {
						GeneratedMessage sc = SC.newBuilder().setRevokeSellerResponse(
								RevokeSellerResponse.newBuilder().setErrorCode(ErrorCode.REVOKE_FAIL.getNumber()))
								.build();
						SessionUtils.sc(session, sc);
						scService.scGoodsTy(type);
						return;
					}
					// 商品属性变动
					updateGoodsRevoke(seller);
					removeSeller(seller);
					deleteSet.add(seller);
					seller.setSell_active(Constant.SOME_SELL);
					updateSeller(seller);
//					updateRevokeNumber(type, seller.getSell_count() - seller.getSell_overCount(), sellAccount);
					sellerLogicNumService.updateRevokeNumbers(seller,seller.getSell_count() - seller.getSell_overCount());
					loggerinfo(role, "撤销的数量" + (seller.getSell_count() - seller.getSell_overCount()));
				}
				sellerSelectService.scMySellerRequesr(sellAccount);
				sellerAccount.removeAll(deleteSet);// 帐号状态查找
				sellerListByType.removeAll(deleteSet);// 类型状态查找
				list.removeAll(deleteSet);// 卖的Id状态查找
			}
			GeneratedMessage sc = SC.newBuilder()
					.setRevokeSellerResponse(RevokeSellerResponse.newBuilder().setFreeback(Constant.FREE_BACK)).build();
			SessionUtils.sc(session, sc);
			scService.scGoodsTy(type);
			loggerinfo(role, "撤销之后用户帐号" + role.getAccount() + "用户金额" + role.getRole_rmbA() + "撤销用户帐号" + sellAccount
					+ "撤销的价格" + price + "撤销类型" + type + "撤销之后用户数量" + num.getNum_count());
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 商品属性变动
	 * 
	 * @param seller
	 * @author WSS 2017-8-10
	 */
	@Override
	public void updateGoodsRevoke(SellerBO seller) {
		gameDB.getUpdatePool().submit(new EntityRunnable<SellerBO>(seller) {
			@Override
			public void run(SellerBO entity) {
				goodsDao.updateGoodsRevoke(Constant.NO, TimeUtils.getDetailTimeStr(), entity.getSell_id(), Constant.YES,
						entity.getSell_count() - entity.getSell_overCount());
			}
		});
	}

	/**
	 * 改变商品属性(撤销，未出售商品加回去)
	 * 
	 * @param type
	 * @param count
	 * @param account
	 */
//	public void updateRevokeNumber(String type, int count, String account) {
//		Role role = sellerLogicNumService.getRoleByAccount(account);
//		NumbersBO num = sellerLogicNumService.getNumbers(account, type);
//		if (num == null) {
//			return; // 加个主推
//		}
//		num.setNum_count(num.getNum_count() + count);
//		// 改变本周操作数量
//		num.setOperation_count(num.getOperation_count() + count);
//		num.setOperation_time(TimeUtils.getDetailTimeStr());
//		// 通知
//		updateAddCurrency(role.getRoleId(), type, count,role,num);
//		noticeNumber(role, num);
//		gameDB.getUpdatePool().submit(new EntityRunnable<NumbersBO>(num) {
//			@Override
//			public void run(NumbersBO entity) {
//				numberDao.update(entity);
//			}
//		});
//	}

	/**
	 * 通知用户数量金额改变
	 * 
	 * @param role
	 * @param num
	 */
	public void noticeNumber(Role sellRole, NumbersBO num) {
		GeneratedMessage sc = SC.newBuilder()
				.setSCNumberNotice(SCNumberNotice.newBuilder().setAccount(sellRole.getAccount())
						.setCount(num.getNum_count()).setRmbA(String.valueOf(sellRole.getRole_rmbA())))
				.build();
		SessionUtils.sc(sellRole.getRoleId(), sc);
	}

	/**
	 * 操作其他数据库
	 */
//	public void updateAddCurrency(int roleId, String type, int count,Role role,NumbersBO numbersBO) {
//		insertRecord(count,type,role,numbersBO);
//		curencyUserDao.addUpdate(count, roleId, Integer.valueOf(type));
//	}

	
	/**
	 * 撤销记录
	 * 
	 * @param count
	 * @param type
	 * @param role
	 * @param numbersBO
	 */
//	private void insertRecord(int count, String type, Role role, NumbersBO numbersBO) {
//		RecordBO recordBO = new RecordBO();
//		recordBO.setCount(count);// 操作数量
//		recordBO.setTime(TimeUtils.getDetailTimeStr());// 操作时间
//		recordBO.setRmb(0.00);// 操作金额
//		recordBO.setStart_count(numbersBO.getNum_count());// 原来数量
//		recordBO.setEnd_count(numbersBO.getNum_count() + count);// 结束数量
//		recordBO.setType(type);// 类型
//		recordBO.setStart_rmb(role.getRole_rmbA());// 原来资金
//		recordBO.setEnd_rmb(role.getRole_rmbA());// 结束资金
//		recordBO.setAccount(role.getAccount());
//		recordBO.setState(-1);
//		gameDB.getInsertPool().submit(new EntityRunnable<RecordBO>(recordBO) {
//			@Override
//			public void run(RecordBO entity) {
//				recordDAO.insert(entity);
//			}
//		});
//	}
	/**
	 * 删除缓存
	 * 
	 * @param seller
	 */
	private void removeSeller(SellerBO seller) {
		// 帐号状态(1)
		List<SellerBO> sellerAccount = getSellerByAccount(seller.getSell_account(), seller.getSell_active());
		// 类型状态(1)
		List<SellerBO> sellerListByType = deleteSellerType(seller.getSell_type());
		Iterator<SellerBO> it = sellerAccount.iterator();
		while (it.hasNext()) {
			SellerBO userObj = it.next();
			if (userObj.getSell_id() == seller.getSell_id()) {
				it.remove();
			}
		}
		Iterator<SellerBO> its = sellerListByType.iterator();
		while (its.hasNext()) {
			SellerBO userObj = its.next();
			if (userObj.getSell_id() == seller.getSell_id()) {
				its.remove();
			}
		}
	}

	/**
	 * 加入到Seller缓存
	 * 
	 * @param seller
	 */
	private List<SellerBO> getSellerListByAll(int sellId, int active) {
		String all = String.valueOf(sellId) + String.valueOf(active);
		List<SellerBO> sellerList = SellerCache.getListByAll(all);
		if (sellerList == null||sellerList.size()==0) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDao.selectSellerByAll(sellId, active);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerByAll().put(all, sellerList);
		}
		return sellerList;
	}

	/**
	 * 找到个人正在出售的信息
	 * 
	 * @param seller
	 */
	private List<SellerBO> getSellerByAccount(String account, int active) {
		String accountActive = account + String.valueOf(active);
		List<SellerBO> sellerList = SellerCache.getSellerListByAccount(accountActive);
		if (sellerList == null||sellerList.size()==0) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDao.getListByAccount(account, active);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerMapByAccount().put(accountActive, sellerList);
		}
		return sellerList;
	}

	/**
	 * 删除缓存
	 * 
	 * @param type
	 * @param set
	 */
	private List<SellerBO> deleteSellerType(String type) {
		String types = type + String.valueOf(Constant.START_SELL);
		List<SellerBO> sellerList = SellerCache.getListByType(types);
		if (sellerList == null||sellerList.size()==0) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDao.ListByType(type, Constant.START_SELL);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerByType().put(types, sellerList);
		}
		return sellerList;
	}

	@Override
	public void updateSeller(SellerBO seller) {

		gameDB.getUpdatePool().submit(new EntityRunnable<SellerBO>(seller) {
			@Override
			public void run(SellerBO entity) {
				sellerDao.update(entity);
			}
		});

	}
}
