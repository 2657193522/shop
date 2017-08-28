package com.randioo.market_server.module.k.service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.DayKCache;
import com.randioo.market_server.comparator.DaykIdComparator;
import com.randioo.market_server.comparator.DaykMaxComparator;
import com.randioo.market_server.comparator.DaykMinComparator;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.module.k.component.Kthing;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.DayClickResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("daykClickService")
public class DaykClickServiceImpl extends ObserveBaseService implements DaykClickService {
	@Autowired
	private Kthing kthing;
	@Autowired
	private DaykMinComparator daykMinComparator;
	@Autowired
	private DaykMaxComparator daykMaxComparator;
	@Autowired
	private DaykIdComparator daykIdComparator;

	/**
	 * 请求日K
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public GeneratedMessage getAllDayk(String type) {
		DayClickResponse.Builder builder = DayClickResponse.newBuilder();
		List<DaykBO> dayList = kthing.getDinkListType(type);
		if (dayList != null) {
			for (DaykBO dayk : dayList) {
				KData kData = kthing.getDaykData(dayk);
				builder.addKData(kData);
			}
		}
		double max = 0;
		double min = 0;
		if (dayList.size() > 0) {
			Collections.sort(dayList, daykMaxComparator);
			max = dayList.get(dayList.size() - 1).getDay_max_price();
			Collections.sort(dayList, daykMinComparator);
			min = dayList.get(0).getDay_min_price();
			Collections.sort(dayList, daykIdComparator);
		}
		return SC.newBuilder().setDayClickResponse(builder.setHighPrice((int) (max * 100)).setLowPrice((int) (min * 100)))
				.build();
	}

	@Scheduled(cron = "0 30 00 * * ?")
	public void deleteList() {
		Map<String, List<TradingBO>> fifmap = DayKCache.getTradMapToDay();
		Iterator<Entry<String, List<TradingBO>>> fif = fifmap.entrySet().iterator();
		while (fif.hasNext()) {
			fif.next();
			fif.remove();
		}
		if (fifmap != null) {
			fifmap.clear();
		}
		List<String> typeList = kthing.addTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				kthing.getDinkListType(s);
			}
		}
	}
}
