package com.randioo.market_server.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.po.DaykBO;
@Component
public class DaykMinComparator  implements Comparator<DaykBO> {
	/**
	 * 比较日K的最小值
	 */
	
	@Override
	public int compare(DaykBO o1, DaykBO o2) {
		if (o1.getDay_min_price()> o2.getDay_min_price()) {
			return 1;
		}
		if (o1.getDay_min_price() == o2.getDay_min_price()) {
			return 0;
		}
		return -1;
	}

}
