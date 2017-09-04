package com.randioo.market_server.module.goods.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.file.CountConfigCache;
import com.randioo.market_server.cache.file.GainsConfigCache;
import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.cache.local.TradingCache;
import com.randioo.market_server.cache.local.TypeCache;
import com.randioo.market_server.comparator.GoodsComparator;
import com.randioo.market_server.dao.ControlDAO;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.dao.GoodsDAO;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.dao.TradingDAO;
import com.randioo.market_server.dao_remote.CurrencyDAO;
import com.randioo.market_server.entity.bo.GoodsBO;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.file.CountConfig;
import com.randioo.market_server.entity.file.GainsConfig;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.protocol.Entity.GoodsData;
import com.randioo.market_server.protocol.Entity.GoodsTypeData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.Goods.GoodsResponse;
import com.randioo.market_server.protocol.GoodsTy.GoodsTyResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("goodsService")
public class GoodsServiceImpl extends ObserveBaseService implements GoodsService {
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private GoodsDAO goodsDao;
	@Autowired
	private DaykDAO daykDao;
	@Autowired
	private TradingDAO tradingDao;
	@Autowired
	private CurrencyDAO currencyDao;
	@Autowired
	private ControlDAO controlDao;
	@Autowired
	private GoodsComparator goodsComparator;

	/**
	 * 根据类型状态查找所有出售信息
	 * 
	 * @param type
	 * @param active
	 * @return
	 */
	@Override
	public GeneratedMessage getGoodsType(String type) {
		GoodsTyResponse.Builder builder = GoodsTyResponse.newBuilder();
		GainsConfig config = GainsConfigCache.getConfig();
		CountConfig countConfig = CountConfigCache.getConfig();
		if (type == null || type.equals("")) {
			return SC.newBuilder().setGoodsTyResponse(builder.setErrorCode(ErrorCode.INFORMATION_IS_NOT.getNumber()))
					.build();
		}
		List<String> typeList = TypeCache.getTypeList();
		if (!typeList.contains(type)) {
			typeList.add(type);
		}
		double drop = 0.00;
		double startPrice = 0.00;
		int count = 0;
		double timePrice = 0.00;
		double harden = 0.00;
		double sumPrice = 0.0;
		double junjia = 0.00;
		double gains = 0.00;
		DaykBO day = daykDao.getLastDayk(type);
		if (!DateUtils.isToday(day.getDay_time())) {
			if (day != null) {
				GainsConfig gain = GainsConfigCache.getConfig();
				double h = day.getDay_end_price() * Double.parseDouble(gain.addGains);// 涨停价格
				harden = DateUtils.getTwoDouble(h);
				double d = day.getDay_end_price() * Double.parseDouble(gain.downGains);
				drop = DateUtils.getTwoDouble(d);
				startPrice = day.getDay_end_price();
			}
		} else {
			DaykBO lastTwoDay = daykDao.getLastTwoDayk(type);
			if (lastTwoDay != null) {
				GainsConfig gain = GainsConfigCache.getConfig();
				double h = lastTwoDay.getDay_end_price() * Double.parseDouble(gain.addGains);// 涨停价格
				harden = DateUtils.getTwoDouble(h);
				double d = lastTwoDay.getDay_end_price() * Double.parseDouble(gain.downGains);
				drop = DateUtils.getTwoDouble(d);
				startPrice = lastTwoDay.getDay_end_price();
			}
		}

		timePrice = startPrice;
		// 初始开盘没有数据的时候数量为0
		TradingBO trading = tradingDao.getEndTrading(type);
		if (trading != null)
			timePrice = trading.getTrad_price();
		if (DateUtils.getKTime(TimeUtils.getDetailTimeStr(), type) == -2) {
			timePrice = day.getDay_end_price();
		}
		// 返回
		List<TradingBO> daykMap = getTradingListByToDay(type, Constant.NO);
		int j = daykMap.size();
		for (int i = 0; i < j; i++) {
			TradingBO tr = daykMap.get(i);
			count += tr.getTrad_count();
			sumPrice += tr.getTrad_sum();
		}
		count = count * 2;
		sumPrice = DateUtils.getTwoDouble(sumPrice * 2);
		// 涨幅率
		gains = DateUtils.getTwoDouble(((timePrice - startPrice) / startPrice) * 100);
		if (count != 0) {
			junjia = Double.parseDouble(String.format("%.2f", sumPrice / count));
		}
		if (junjia != 0) {
			// 尾数取0或5，保留两位小数
			junjia = Double.parseDouble(DateUtils.getDouble(junjia));
		}

		// 出售信息
		List<SellerBO> sellerList = getSellerTypeA(type);
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
		SC sc = SC.newBuilder()
				.setGoodsTyResponse(builder.setAddPrice((int) (Double.parseDouble(DateUtils.getDouble(harden)) * 100))
						.setDownPrice((int) (Double.parseDouble(DateUtils.getDouble(drop)) * 100))
						.setStartPrice((int) (Double.parseDouble(DateUtils.getDouble(startPrice)) * 100))
						.setCount(count).setTimePrice((int) (Double.parseDouble(DateUtils.getDouble(timePrice)) * 100))
						.setTypeName(currencyDao.getType(Integer.parseInt(type))).setGains((int) (gains * 100))
						.setType(getTypeId(type)).setPoundage(config.poundage).setMaxCount(countConfig.count)
						.setSumPrice((int) (sumPrice * 100))
						.setJunjia((int) (Double.parseDouble(DateUtils.getDouble(junjia)) * 100)))
				.build();
		return sc;
	}

	@Override
	public List<TradingBO> getTradingListByToDay(String type, int state) {
		String typeState = type + String.valueOf(state);
		List<TradingBO> list = TradingCache.getTradingList(typeState);
		if (list == null) {
			list = new ArrayList<>();
			List<TradingBO> tradList = tradingDao.getAllTrading(type, state);
			if (tradList != null) {
				list.addAll(tradList);
			}
			TradingCache.getTradingMap().put(typeState, list);
		}
		return list;
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
			type = currencyDao.getTypeId(Integer.parseInt(types));
			if (type != null) {
				TypeCache.getTypeMap().put(types, type);
			}
		}
		return type;
	}

	@Override
	public List<SellerBO> getSellerTypeA(String type) {
		String types = type + String.valueOf(Constant.YES);
		List<SellerBO> sellerList = SellerCache.getListByType(types);
		if (sellerList == null) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDao.ListByType(type, Constant.YES);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerByType().put(types, sellerList);
		}
		return sellerList;
	}

	/**
	 * 查找商品详细信息并返回
	 * 
	 * @param type
	 * @param price
	 * @param sellAccount
	 * @param sellId
	 * @param all
	 * @return
	 */
	@Override
	public GeneratedMessage getGoodsDetails(String type, int sellId) {
		GoodsResponse.Builder builder = GoodsResponse.newBuilder();
		if (controlDao.selectId(Constant.START_SELL) == 1) {
			return SC.newBuilder()
					.setGoodsResponse(GoodsResponse.newBuilder().setErrorCode(ErrorCode.BUTTON_FAILURE.getNumber()))
					.build();
		}
		// List<GoodsBO> goodsList = getGoodsListByAll(sellId);
		List<GoodsBO> goodsList = goodsDao.selectTypeByAll(sellId, Constant.START_SELL);
		if (goodsList != null) {
			// int j = goodsList.size();
			for (GoodsBO goods : goodsList) {
				// GoodsBO goods = goodsList.get(i);
				builder.addGoodsData(GoodsData.newBuilder().setGoodsId(goods.getGoods_id())
						.setGoodsNum(goods.getGoods_num()).setPrice((int) (goods.getGoods_price() * 100))
						.setSellAccount(goods.getGoods_account()).setSellId(goods.getGoods_sell_id())
						.setTime(goods.getGoods_time()).setType(getTypeId(type)));
			}
		}
		GeneratedMessage sc = SC.newBuilder().setGoodsResponse(builder).build();
		return sc;
	}

	/**
	 * 查找商品详细信息
	 * 
	 * @param type
	 * @param price
	 * @param sellAccount
	 * @param sellId
	 * @param all
	 * @return
	 */
	// private List<GoodsBO> getGoodsListByAll(int sellId) {
	// String all = String.valueOf(sellId) +
	// String.valueOf(Constant.START_SELL);
	// List<GoodsBO> goodsList = GoodsCache.getGoodsByAll(all);
	// if (goodsList == null) {
	// goodsList = new ArrayList<>();
	// List<GoodsBO> list = goodsDao.selectTypeByAll(sellId,
	// Constant.START_SELL);
	// if (list != null) {
	// goodsList.addAll(list);
	// }
	// GoodsCache.getGoodsMapByAll().put(all, goodsList);
	// }
	// return goodsList;
	// }

}