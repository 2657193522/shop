package com.randioo.market_server.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.po.MinkBO;
@Component
public class MinkMinComparator  implements Comparator<MinkBO> {
	/**
	 * 比较分K的最小
	 */
	
	@Override
	public int compare(MinkBO o1, MinkBO o2) {
		if (o1.getMin_min_price()> o2.getMin_min_price()) {
			return 1;
		}
		if (o1.getMin_min_price() == o2.getMin_min_price()) {
			return 0;
		}
		return -1;
	}

}
