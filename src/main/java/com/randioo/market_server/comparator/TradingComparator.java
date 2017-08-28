package com.randioo.market_server.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.bo.TradingBO;

@Component
public class TradingComparator implements Comparator<TradingBO> {
	/**
	 * 比较成交的最高价格最低价格
	 */
	@Override
	public int compare(TradingBO o1, TradingBO o2) {
		if (o1.getTrad_price() > o2.getTrad_price()) {
			return 1;
		}
		if (o1.getTrad_price() == o2.getTrad_price()) {
			return 0;
		}
		return -1;
	}

}
