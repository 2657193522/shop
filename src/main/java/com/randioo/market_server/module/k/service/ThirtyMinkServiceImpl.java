package com.randioo.market_server.module.k.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.mink.ThirtyMinkCache;
import com.randioo.market_server.comparator.MinkIdComparator;
import com.randioo.market_server.comparator.MinkMaxComparator;
import com.randioo.market_server.comparator.MinkMinComparator;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.dao.ThirtyMinkDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.module.k.component.Kthing;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.ThirtyMinkResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("thirtyMinkService")
public class ThirtyMinkServiceImpl extends ObserveBaseService implements ThirtyMinkService {
	@Autowired
	private Kthing kthing;
	@Autowired
	private DaykDAO dayKDao;
	@Autowired
	private ThirtyMinkDAO thirtyMinkDAO;
	@Autowired
	private MinkMaxComparator minkCoparator;
	@Autowired
	private MinkMinComparator minkMinComparator;
	@Autowired
	private MinkIdComparator minkIdComparator;
	@Autowired
	private Role roleChange;

	@Override
	public GeneratedMessage getThirtyMink(String type) {
		ThirtyMinkResponse.Builder builder = ThirtyMinkResponse.newBuilder();
		// 根据类型查询得到当天所有分K
		List<MinkBO> minkList = addMinkByToday(type);

		// 根据类型查询得到当天所有分K
		DaykBO day = dayKDao.getLastDayk(type);
		if (DateUtils.isToday(day.getDay_time())) {
			day = dayKDao.getLastTwoDayk(type);
		}
		int i = 0;
		if (minkList == null || minkList.size() == 0) {
			Collections.sort(minkList, minkIdComparator);
			String times = DateUtils.getNumTime(TimeUtils.getDetailTimeStr());
			i = DateUtils.getKTime(times, type);
			long time = DateUtils.getLongTime(times);
			time = time - (i * 300000) + 1800000;
			if (i > 48) {
				i = i - 24;
			}
			if (DateUtils.panduanTime() == 2) {
				i = 24;
			}
			if (DateUtils.panduanTime() == -1) {
				i = 48;
			}
			i = i / 6;
			for (int j = 0; j < i; j++) {
				if (day != null) {
					KData data = kthing.parseKData(day, time);
					builder.addKData(data);
				}
				time = time + 1800000;
				time = DateUtils.getNowTime(time);
			}
		} else {
			long time = 0;
			if (minkList.size() > 0) {
				Collections.sort(minkList, minkIdComparator);
				i = DateUtils.getKTime(minkList.get(0).getMin_time(), type);
				time = DateUtils.getLongTime(minkList.get(0).getMin_time());
				time = time - (i * 300000) + 1800000;
				if (i > 48) {
					i = i - 24;
				}
				i = i / 6;
				for (int j = 0; j < i - 1; j++) {
					if (day != null) {
						KData data = kthing.parseKData(day, time);
						builder.addKData(data);
					}
					time = time + 1800000;
					time = DateUtils.getNowTime(time);
				}
			}
			for (MinkBO mink : minkList) {
				KData kData = kthing.getMinkData(mink);
				builder.addKData(kData);
			}
		}
		double timeMax = 0.00;
		double timeMin = 0.00;
		if (StateCache.isThirtyPanduan()) {
			if (roleChange.isJudge()) {
				MinkBO thirtyMink = ThirtyMinkCache.getMapByTime(type);
				if (thirtyMink != null) {
					timeMax = thirtyMink.getMin_max_price();
					timeMin = thirtyMink.getMin_min_price();
					builder.addKData(kthing.getAllMinkData(thirtyMink, 30));
				}
			}
		}
		double max = 0;
		double min = 0;
		if (minkList.size() > 0) {
			Collections.sort(minkList, minkCoparator);
			max = minkList.get(minkList.size() - 1).getMin_max_price();
			Collections.sort(minkList, minkMinComparator);
			min = minkList.get(0).getMin_min_price();
			Collections.sort(minkList, minkIdComparator);
			if (max < timeMax) {
				max = timeMax;
			}
			if (min > timeMin) {
				if (timeMin != 0) {
					min = timeMin;
				}
			}
		} else {
			max = day.getDay_end_price();
			min = day.getDay_end_price();
		}
		return SC.newBuilder().setThirtyMinkResponse(builder.setHighPrice((int) (max * 100)).setLowPrice((int) (min * 100)))
				.build();
	}

	private List<MinkBO> addMinkByToday(String type) {
		List<MinkBO> minkList = ThirtyMinkCache.getOneMinkList(type);
		if (minkList == null || minkList.size() == 0) {
			minkList = new ArrayList<>();
			List<MinkBO> minks = thirtyMinkDAO.getNowDay(type);
			if (minks != null) {
				minkList.addAll(minks);
			}
			ThirtyMinkCache.getMinkList().put(type, minkList);
		}
		return minkList;
	}

	@Scheduled(cron = "0 30 00 * * ?")
	public void deleteList() {
		Map<String, List<TradingBO>> fifmap = ThirtyMinkCache.getTradMap();
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
				addMinkByToday(s);
			}
		}
	}
	
	@Scheduled(cron = "0 30 12 * * ?")
	public void deleteListMo() {
		Map<String, List<TradingBO>> fifmap = ThirtyMinkCache.getTradMap();
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
				addMinkByToday(s);
			}
		}
	}
}
