package com.randioo.market_server.module.deal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.TradingCache;
import com.randioo.market_server.dao.ControlDAO;
import com.randioo.market_server.dao.TradingDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.protocol.Deal.DealResponse;
import com.randioo.market_server.protocol.Entity.TradingData;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("dealService")
public class DealServiceImpl extends ObserveBaseService implements DealService {
	@Autowired
	private TradingDAO tradingDao;
	@Autowired
	private ControlDAO controlDao;

	/**
	 * 
	 * 根据当天该类型的成交记录
	 */
	@Override
	public GeneratedMessage getDeal(Role role, String type, int startNum, int count) {
		if (controlDao.selectId(Constant.YES) == 1) {
			GeneratedMessage sc = SC.newBuilder()
					.setDealResponse(DealResponse.newBuilder().setErrorCode(ErrorCode.BUTTON_FAILURE.getNumber()))
					.build();
			return sc;
		}
		DealResponse.Builder builder = DealResponse.newBuilder();
		List<TradingBO> tradingList = getTradingListToday(type, Constant.NO);
		if (count != -1) {
			tradingList = DateUtils.getTrading(startNum, count, tradingList);
		}
		if (tradingList != null) {
			for (TradingBO tr : tradingList) {
				builder.addTradingData(TradingData.newBuilder().setBuyAccount(tr.getTrad_account())
						.setBuyId(tr.getTrad_buy_id()).setMoney((int)( tr.getTrad_poundage() * 100))
						.setTradCount(tr.getTrad_count() * 2).setTradId(tr.getTrad_id())
						.setTradPrice((int)(tr.getTrad_price() * 100))
						.setTradSum((int) (DateUtils.changeSum(2, tr.getTrad_sum())) * 100)
						.setTradTime(tr.getTrad_time()));
			}
		}
		return SC.newBuilder().setDealResponse(builder).build();
	}

	/**
	 * 找到当天所有成交记录
	 * 
	 * @param
	 * @return
	 */
	private List<TradingBO> getTradingListToday(String type, int state) {
		String typeState = type + String.valueOf(state);
		List<TradingBO> tradingList = TradingCache.getTradingList(typeState);
		if (tradingList == null || tradingList.size() == 0) {
			tradingList = new ArrayList<>();
			List<TradingBO> list = tradingDao.getAllTrading(type, Constant.STOP_SELL);
			if (list != null) {
				tradingList.addAll(list);
			}
			TradingCache.getTradingMap().put(typeState, list);
		}
		return tradingList;
	}
}
