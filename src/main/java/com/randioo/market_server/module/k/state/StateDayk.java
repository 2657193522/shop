package com.randioo.market_server.module.k.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.DayKCache;
import com.randioo.market_server.comparator.DaykIdComparator;
import com.randioo.market_server.comparator.DaykMaxComparator;
import com.randioo.market_server.comparator.DaykMinComparator;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.KResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;

@Component
public class StateDayk implements StateKService {
	@Autowired
	private DaykMaxComparator daykMaxComparator;
	@Autowired
	private DaykIdComparator daykIdComparator;
	@Autowired
	private DaykMinComparator daykMinComparator;
	@Autowired
	private DaykDAO dayKDao;
	

	@Override
	public GeneratedMessage getMink(String type) {
		// TODO Auto-generated method stub
		KResponse.Builder builder = KResponse.newBuilder();
		List<DaykBO> dayList = getDinkListType(type);
		double max = 0;
		double min = 0;
		if (dayList.size() > 0) {
			Collections.sort(dayList, daykMaxComparator);
			max = dayList.get(dayList.size() - 1).getDay_max_price();
			Collections.sort(dayList, daykMinComparator);
			min = dayList.get(0).getDay_min_price();
			Collections.sort(dayList, daykIdComparator);
		}
		if (dayList != null) {
			for (DaykBO dayk : dayList) {
				KData kData = getDaykData(dayk);
				builder.addKData(kData);
			}
		}
		GeneratedMessage sc = SC.newBuilder()
				.setKResponse(builder.setHighPrice((int)(max*100)).setLowPrice((int)(min*100))).build();
		return sc;
	}

	/**
	 * 根据类型获得日K
	 * 
	 * @param type
	 * @return
	 */
	private List<DaykBO> getDinkListType(String type) {
		List<DaykBO> dayList = DayKCache.getDaykList(type);
		if (dayList == null) {
			dayList = new ArrayList<>();
			List<DaykBO> list = dayKDao.selectAll(type);
			if (list != null) {
				dayList.addAll(list);
			}
			DayKCache.getDaykType().put(type, dayList);
		}
		return dayList;
	}

	/**
	 * 设置日K的数据
	 * 
	 * @param dayk
	 * @return
	 */
	private KData getDaykData(DaykBO dayk) {
		int percentage = DateUtils.getPercentage(dayk.getDay_start_price(), dayk.getDay_end_price());
		int string = (int)(Double.parseDouble(String.format("%.2f", dayk.getDay_sum_price()))*100);
		
		return KData.newBuilder().setEndPrice((int)(Double.parseDouble(DateUtils.getDouble(dayk.getDay_end_price()*100))))
				.setMinPrice((int)(dayk.getDay_min_price()*100)).setDayTime(dayk.getDay_time())
				.setPercentage(percentage).setStartPrice((int)(dayk.getDay_start_price()*100))
				.setSumCount(dayk.getDay_sum_count()).setSumPrice(string)
				.setMaxPrice((int)(dayk.getDay_max_price()*100)).build();
	}
}
