package com.randioo.market_server.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.cache.local.TradingCache;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.module.seller.service.SellerDeleteService;
import com.randioo.market_server.module.seller.service.SellerSelectService;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.template.EntityRunnable;

@Component
public class TimerScheduler {
	@Autowired
	private GameDB gameDB;
	@Autowired
	private SellerDAO sellerDAO;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumComponent;
	@Autowired
	private SellerSelectService sellerSelectService;
	@Autowired
	private SellerDeleteService sellerDeleteService;
	
	
	public void deleteMessage() {
		Map<String, List<TradingBO>> tradingList = TradingCache.getTradingMap();
		Iterator<Entry<String, List<TradingBO>>> it3 = tradingList.entrySet().iterator();
		while (it3.hasNext()) {
			it3.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it3.remove();
		}
		if (tradingList != null) {
			tradingList.clear();
		}
		List<SellerBO> list = sellerDAO.selectSellerByState(Constant.YES);
		List<String> strings = new ArrayList<>();
		if (list != null) {
			for (SellerBO seller : list) {
//				Role role = sellerLogicNumComponent.getRoleByAccount(seller.getSell_account());
//				NumbersBO num = sellerLogicNumComponent.getNumbers(seller.getSell_account(), seller.getSell_type());
				seller.setSell_active(Constant.SYSTEM_RRVOKE);
				sellerLogicNumComponent.updateRevokeNumbers(seller,
						seller.getSell_count() - seller.getSell_overCount());
				sellerDeleteService.updateGoodsRevoke(seller);

				sellerDeleteService.updateSeller(seller);
				strings.add(seller.getSell_account());
				gameDB.getUpdatePool().submit(new EntityRunnable<SellerBO>(seller) {

					@Override
					public void run(SellerBO entity) {
						sellerDAO.update(entity);
					}
				});
				sellerSelectService.scMySellerRequesr(seller.getSell_account());
			}
		}
		Map<String, List<SellerBO>> sellerList = SellerCache.getSellerByAll();
		Iterator<Entry<String, List<SellerBO>>> it = sellerList.entrySet().iterator();
		while (it.hasNext()) {
			it.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it.remove();
		}
		Map<String, List<SellerBO>> sellerListByType = SellerCache.getSellerByType();
		Iterator<Entry<String, List<SellerBO>>> it1 = sellerListByType.entrySet().iterator();
		while (it.hasNext()) {
			it1.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it1.remove();
		}
		Map<String, List<SellerBO>> sellerListByAccount = SellerCache.getSellerMapByAccount();
		Iterator<Entry<String, List<SellerBO>>> it2 = sellerListByAccount.entrySet().iterator();
		while (it2.hasNext()) {
			it2.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it2.remove();
		}
		for (String s : strings) {
			sellerSelectService.scMySellerRequesr(s);
		}
		if (sellerList != null) {
			sellerList.clear();
		}
		Map<String, List<SellerBO>> sellerListBy = SellerCache.getSellerByType();
		if (sellerListBy != null) {
			sellerListBy.clear();
		}
		Map<String, List<SellerBO>> sell = SellerCache.getSellerMapByAccount();
		if (sell != null) {
			sell.clear();
		}
		Map<String, List<TradingBO>> t = TradingCache.getTradingMap();
		if (t != null) {
			t.clear();
		}
	}

	@Scheduled(cron = "0 00 16 * * ?")
	public void deleteCache() {
		Map<String, List<SellerBO>> sellerList = SellerCache.getSellerByAll();
		if (sellerList != null) {
			sellerList.clear();
		}
		Map<String, List<SellerBO>> sellerListByType = SellerCache.getSellerByType();
		if (sellerListByType != null) {
			sellerListByType.clear();
		}
		Map<String, List<SellerBO>> sellerListByAccount = SellerCache.getSellerMapByAccount();
		if (sellerListByAccount != null) {
			sellerListByAccount.clear();
		}
		Map<String, List<TradingBO>> tradingList = TradingCache.getTradingMap();
		if (tradingList != null) {
			tradingList.clear();
		}
	}
}
