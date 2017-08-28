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
import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.mink.MinKCache;
import com.randioo.market_server.comparator.MinkIdComparator;
import com.randioo.market_server.comparator.MinkMaxComparator;
import com.randioo.market_server.comparator.MinkMinComparator;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.module.k.component.Kthing;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.MinkClickResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.Observer;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("minkClickService")
public class MinkClickServiceImpl extends ObserveBaseService implements Observer, MinkClickService {
	@Autowired
	private MinkMaxComparator minkCoparator;
	@Autowired
	private MinkMinComparator minkMinComparator;
	@Autowired
	private MinkIdComparator minkIdComparator;
	@Autowired
	private DaykDAO dayKDao;
	@Autowired
	private Kthing kthing;
	@Autowired
	private Role roleChange;

	/**
	 * 点击请求5分钟所有数据 MINCLICKACTION
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public GeneratedMessage getMinkClick(String type) {

		MinkClickResponse.Builder builder = MinkClickResponse.newBuilder();
		// 根据类型查询得到当天所有分K
		List<MinkBO> minkList = kthing.addMinkByToday(type);

		// 根据类型查询得到当天所有分K
		DaykBO day = dayKDao.getLastDayk(type);
		if (DateUtils.isToday(day.getDay_time())) {
			day = dayKDao.getLastTwoDayk(type);
		}
		int i = 0;
		if (minkList == null || minkList.size() == 0) {
			String times = DateUtils.getNumTime(TimeUtils.getDetailTimeStr());
			i = DateUtils.getKTime(times, type);
			long time = DateUtils.getLongTime(times);
			time = time - (i * 300000) + 300000;
			if (i > 48) {
				i = i - 24;
			}
			if (DateUtils.panduanTime() == 2) {
				i = 24;
			}
			if (DateUtils.panduanTime() == -1) {
				i = 48;
			}
			for (int j = 0; j < i; j++) {
				if (day != null) {
					KData data = kthing.parseKData(day, time);
					builder.addKData(data);
				}
				time = time + 300000;
				time = DateUtils.getNowTime(time);
			}
		} else {
			long time = 0;
			if (minkList.size() > 0) {
				Collections.sort(minkList, minkIdComparator);
				i = DateUtils.getKTime(minkList.get(0).getMin_time(), type);
				time = DateUtils.getLongTime(minkList.get(0).getMin_time());
				time = time - (i * 300000) + 300000;
				if (i > 48) {
					i = i - 24;
				}
				for (int j = 0; j < i - 1; j++) {
					if (day != null) {
						KData data = kthing.parseKData(day, time);
						builder.addKData(data);
					}
					time = time + 300000;
					time = DateUtils.getNowTime(time);
				}
			}
			for (MinkBO mink : minkList) {
				KData kData = kthing.getMinkData(mink);
				builder.addKData(kData);
			}
		}
		double timeMax=0.00;
		double timeMin=0.00;
		if (StateCache.isPanduan()) {
			if(roleChange.isJudge()){
			MinkBO minks = MinKCache.getMapByTime(type);
			if (minks != null) {
				timeMin=minks.getMin_min_price();
				timeMax=minks.getMin_max_price();
				builder.addKData(kthing.getAllMinkData(minks, 5));
			}}
		}
		double max = 0;
		double min = 0;
		if (minkList.size() > 0) {
			Collections.sort(minkList, minkCoparator);
			max = minkList.get(minkList.size() - 1).getMin_max_price();
			Collections.sort(minkList, minkMinComparator);
			min = minkList.get(0).getMin_min_price();
			Collections.sort(minkList, minkIdComparator);
			if(max<timeMax){
				max=timeMax;
			}
			if(min>timeMin){
				if (timeMin != 0) {
					min = timeMin;
				}
			}
		} else {
			max = day.getDay_end_price();
			min = day.getDay_end_price();
		}
		return SC.newBuilder()
				.setMinkClickResponse(builder.setHighPrice((int)(max*100)).setLowPrice((int)(min*100)))
				.build();
	}

	@Scheduled(cron = "0 05 00 * * ?")
	public void deleteList() {
		Map<String, List<MinkBO>> mink = MinKCache.getMapType();
		Iterator<Entry<String, List<MinkBO>>> its = mink.entrySet().iterator();
		while (its.hasNext()) {
			its.next();
			// 此处可以做额外的判断，相应符合条件的元素
			its.remove();
		}
		if (mink != null) {
			mink.clear();
		}
		List<String> typeList = kthing.addTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				kthing.addMinkByToday(s);
			}
		}
	}
	
	@Scheduled(cron = "0 05 12 * * ?")
	public void deleteListMo() {
		Map<String, List<MinkBO>> mink = MinKCache.getMapType();
		Iterator<Entry<String, List<MinkBO>>> its = mink.entrySet().iterator();
		while (its.hasNext()) {
			its.next();
			// 此处可以做额外的判断，相应符合条件的元素
			its.remove();
		}
		if (mink != null) {
			mink.clear();
		}
		List<String> typeList = kthing.addTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				kthing.addMinkByToday(s);
			}
		}
	}
}
