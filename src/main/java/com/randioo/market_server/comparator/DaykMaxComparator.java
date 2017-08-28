package com.randioo.market_server.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.po.DaykBO;

@Component
public class DaykMaxComparator implements Comparator<DaykBO> {
	/**
	 * 比较日K的最高最低
	 */
	@Override
	public int compare(DaykBO o1, DaykBO o2) {
		if (o1.getDay_max_price() > o2.getDay_max_price()) {
			return 1;
		}
		if (o1.getDay_max_price() == o2.getDay_max_price()) {
			return 0;
		}
		return -1;
	}

}
