package com.randioo.market_server.module.k.state;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.file.TimingConfigCache;
import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.mink.MinKCache;
import com.randioo.market_server.comparator.MinkIdComparator;
import com.randioo.market_server.comparator.MinkMaxComparator;
import com.randioo.market_server.comparator.MinkMinComparator;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.file.TimingConfig;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.module.k.component.Kthing;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.KResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Component
public class StateMink implements StateKService {
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

	@Override
	public GeneratedMessage getMink(String type) {
		KResponse.Builder builder = KResponse.newBuilder();
		List<MinkBO> minkList = kthing.addMinkByToday(type);
		// if (minkList != null && minkList.size() > 0) {
		// if (!DateUtils.isToday(minkList.get(minkList.size() -
		// 1).getMin_time())) {
		// minkList.clear();
		// }
		// }
		DaykBO day = dayKDao.getLastDayk(type);

		int i = 0;
		double max = 0;
		double min = 0;
		if (minkList == null || minkList.size() == 0) {
			addMinkNo(type, builder, minkList, day);
		} else {
			addMinkYes(type, builder, minkList, day, i);
		}
		double timeMax = 0.00;
		double timeMin = 0.00;
		if (StateCache.isPanduan()) {
			if(roleChange.isJudge()){
			MinkBO minks = MinKCache.getMapByTime(type);
			if (minks != null) {
				timeMin = minks.getMin_min_price();
				timeMax = minks.getMin_max_price();
				builder.addKData(kthing.getAllMinkData(minks, 5));
			}}
		}
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
		GeneratedMessage sc = SC.newBuilder()
				.setKResponse(builder.setHighPrice((int)(max*100)).setLowPrice((int)(min*100))).build();
		return sc;
	}

	/**
	 * 为空的时候生成K线
	 * 
	 * @param type
	 * @param builder
	 * @param minkList
	 * @param day
	 */
	private void addMinkNo(String type, KResponse.Builder builder, List<MinkBO> minkList, DaykBO day) {
		if (DateUtils.isToday(day.getDay_time())) {
			day = dayKDao.getLastTwoDayk(type);
		}
		int i;
		Collections.sort(minkList, minkIdComparator);
		String times = DateUtils.getNumTime(TimeUtils.getDetailTimeStr());
		i = DateUtils.getKTime(times, type);
		long time = DateUtils.getLongTime(times);
		time = time - (i * 300000) + 300000;
		if (i >= 48) {
			i = i - 24;
		}
		if (DateUtils.panduanTime() == 2) {
			i = 24;
		}
		for (int j = 0; j < i; j++) {
			if (day != null) {
				KData data = kthing.parseKData(day, time);
				builder.addKData(data);
			}
			time = time + 300000;
			time = getNowTime(time);
		}
	}

	private long getNowTime(long time) {
		int weekday = DateUtils.getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekday);
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = ca.getTime();
		String nowDateStr = sdf.format(nowDate);
		String ymd = nowDateStr.split(" ")[0];
		String mEndTimeStr = ymd + " " + timing.mEndTime;
		String aStartTimeStr = ymd + " " + timing.aStartTime;
		Long long1 = DateUtils.getLongTime(mEndTimeStr);
		Long long2 = DateUtils.getLongTime(aStartTimeStr);
		if (time < long2 && time > long1 + 60000) {
			time = time + (24 * 300000);
		}
		return time;
	}

	/**
	 * 有值的时候生成K线
	 * 
	 * @param type
	 * @param builder
	 * @param minkList
	 * @param day
	 * @param i
	 */
	private void addMinkYes(String type, KResponse.Builder builder, List<MinkBO> minkList, DaykBO day, int i) {
		if (DateUtils.isToday(day.getDay_time())) {
			day = dayKDao.getLastTwoDayk(type);
		}
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
				time = getNowTime(time);
			}
		}
		for (MinkBO mink : minkList) {
			KData kData = kthing.getMinkData(mink);
			builder.addKData(kData);
		}
	}

}
