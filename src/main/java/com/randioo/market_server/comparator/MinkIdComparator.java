package com.randioo.market_server.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.util.DateUtils;

@Component
public class MinkIdComparator implements Comparator<MinkBO> {
	/**
	 * 比较日K的最高最低
	 */
	@Override
	public int compare(MinkBO o1, MinkBO o2) {
		if (DateUtils.getLongTime(o1.getMin_time()) > DateUtils.getLongTime(o2.getMin_time())) {
			return 1;
		}
		if (DateUtils.getLongTime(o1.getMin_time()) > DateUtils.getLongTime(o2.getMin_time())) {
			return 0;
		}
		return -1;
	}

}
