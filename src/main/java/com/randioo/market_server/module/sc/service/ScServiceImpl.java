package com.randioo.market_server.module.sc.service;

import java.util.Collections;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.TypeCache;
import com.randioo.market_server.comparator.GoodsComparator;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.dao.TradingDAO;
import com.randioo.market_server.dao_remote.CurrencyDAO;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.goods.service.GoodsService;
import com.randioo.market_server.protocol.Entity.GoodsTypeData;
import com.randioo.market_server.protocol.GoodsTy.SCGoodsType;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Service("scService")
public class ScServiceImpl extends ObserveBaseService implements ScService {
	@Autowired
	private DaykDAO daykDao;
	@Autowired
	private TradingDAO tradingDao;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private GoodsComparator goodsComparator;

	@Override
	public void scGoodsTy(String type) {
		SCGoodsType.Builder builder = SCGoodsType.newBuilder();
		// TODO Auto-generated method stub
		double timePrice = 0.00;
		double sumPrice = 0.0;
		int count = 0;
		double gains = 0.00;
		double junjia = 0.00;
		DaykBO day = daykDao.getLastDayk(type);
		timePrice = day.getDay_end_price();
		TradingBO trading = tradingDao.getEndTrading(type);
		if (trading != null)
			timePrice = trading.getTrad_price();
		List<TradingBO> daykMap = goodsService.getTradingListByToDay(type, Constant.NO);
		int d = daykMap.size();
		for (int i = 0; i < d; i++) {
			TradingBO tr = daykMap.get(i);
			count += tr.getTrad_count();
			sumPrice += tr.getTrad_sum();
		}
		count = count * 2;// 成交额
		sumPrice = Double.parseDouble(String.format("%.2f", sumPrice * 2));// 成交总额
		// 均价
		if (count != 0) {
			junjia = Double.parseDouble(String.format("%.2f", sumPrice / count));
		}
		if (junjia != 0) {
			junjia = Double.parseDouble(DateUtils.getDouble(junjia));
		}
		double g = ((timePrice - day.getDay_end_price()) / day.getDay_end_price()) * 100;
		gains = Double.parseDouble(String.format("%.2f", g));// 涨幅度

		List<SellerBO> sellerList = goodsService.getSellerTypeA(type);
		Collections.sort(sellerList, goodsComparator);
		if (sellerList != null) {
			int c = 0;
			for (SellerBO seller : sellerList) {
				if (c >= 12) {
					break;
				}
				// 出售数量大于成交数量
				int counts = seller.getSell_count() - seller.getSell_overCount();
				if (counts > 0) {
					builder.addGoodsTypeData(GoodsTypeData.newBuilder().setType(getTypeId(seller.getSell_type()))
							.setPrice(String.valueOf(seller.getSell_price())).setSellAccount(seller.getSell_account())
							.setSellId(seller.getSell_id()).setCount(counts));
					c++;
				}
			}
		}
		GeneratedMessage sc = SC.newBuilder()
				.setSCGoodsType(builder.setGains((int) (gains * 100)).setCount(count)
						.setJunjia((int) (Double.parseDouble(DateUtils.getDouble(junjia * 100))))
						.setTimePrice((int) (Double.parseDouble(DateUtils.getDouble(timePrice * 100))))
						.setSumPrice((int) (sumPrice * 100)))
				.build();
		for (IoSession session : SessionCache.getAllSession()) {
			SessionUtils.sc(session, sc);
		}
	}

	/**
	 * 找到类型代码
	 * 
	 * @param types
	 * @return
	 */
	public String getTypeId(String types) {
		String type = TypeCache.getType(types);
		if (type == null) {
			type = currencyDAO.getTypeId(Integer.parseInt(types));
			if (type != null) {
				TypeCache.getTypeMap().put(types, type);
			}
		}
		return type;
	}

}
