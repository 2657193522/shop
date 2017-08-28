package com.randioo.market_server.module.buyer.component.seller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.module.Constant;
import com.randioo.randioo_server_base.service.ObserveBaseService;
@Service("sellerCacheComponent")
public class SellerCacheComponentImpl extends ObserveBaseService implements SellerCacheComponent {
	@Autowired
	private SellerDAO sellerDAO;

	/**
	 * 删除缓存
	 * 
	 * @param seller
	 */
	private void removeSeller(SellerBO seller) {
		List<SellerBO> sellerAccount = deleteSellerAccount(seller.getSell_account(), Constant.START_SELL);
		List<SellerBO> sellerListByType = deleteSellerType(seller.getSell_type(), Constant.START_SELL);
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

	@Override
	public void deleteSeller(SellerBO seller) {
		List<SellerBO> sellerListByType = deleteSellerType(seller.getSell_type(), seller.getSell_active());
		List<SellerBO> sellerListByAccount = deleteSellerAccount(seller.getSell_account(), seller.getSell_active());
		if (seller.getSell_active() == Constant.YES) {
			for (SellerBO type : sellerListByType) {
				if (type.getSell_id() == seller.getSell_id()) {
					type.setSell_overCount(seller.getSell_overCount());
				}
			}
			for (SellerBO account : sellerListByAccount) {
				if (account.getSell_id() == seller.getSell_id()) {
					account.setSell_overCount(seller.getSell_overCount());
				}
			}
		} else {
			removeSeller(seller);
		}
	}

	/**
	 * 
	 * @param seller
	 */
	private List<SellerBO> deleteSellerAccount(String sellAccount, int active) {
		String accountActive = sellAccount + String.valueOf(active);
		List<SellerBO> sellerList = SellerCache.getSellerListByAccount(accountActive);
		if (sellerList == null) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDAO.getListByAccount(sellAccount, active);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerMapByAccount().put(accountActive, sellerList);
		}
		return sellerList;
	}

	/**
	 * 根据类型状态删除
	 * 
	 */
	private List<SellerBO> deleteSellerType(String type, int active) {
		String typeActive = type + String.valueOf(active);
		List<SellerBO> sellerList = SellerCache.getListByType(typeActive);
		if (sellerList == null) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDAO.ListByType(type, active);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerByType().put(typeActive, sellerList);
		}
		return sellerList;
	}
}
