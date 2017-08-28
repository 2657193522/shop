package com.randioo.market_server.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.bo.SellerBO;

@Component
public class GoodsComparator implements Comparator<SellerBO>{

	@Override
	public int compare(SellerBO o1, SellerBO o2) {
		// TODO Auto-generated method stub
		if (o1.getSell_price()> o2.getSell_price()) {
			return 1;
		}
		if (o1.getSell_price() == o2.getSell_price()) {
			return 0;
		}
		return -1;
	}
}
