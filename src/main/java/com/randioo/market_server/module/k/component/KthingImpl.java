package com.randioo.market_server.module.k.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.market_server.cache.local.DayKCache;
import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.TypeCache;
import com.randioo.market_server.cache.local.mink.MinKCache;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.dao.MinkDAO;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Component
public class KthingImpl implements Kthing {
	@Autowired
	private DaykDAO daykDAO;
	@Autowired
	private MinkDAO minkDAO;

	/**
	 * 根据类型获得日K
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public List<DaykBO> getDinkListType(String type) {
		List<DaykBO> dayList = DayKCache.getDaykList(type);
		if (dayList == null) {
			dayList = new ArrayList<>();
			List<DaykBO> list = daykDAO.selectAll(type);
			if (list != null) {
				dayList.addAll(list);
			}
			DayKCache.getDaykType().put(type, dayList);
		}
		return dayList;
	}

	/**
	 * 当上一个分K为空时 设置分K的数据获取Data
	 * 
	 * @param day
	 * @param time
	 * @return
	 */
	@Override
	public KData parseKData(DaykBO day, long time) {
		int percentage = DateUtils.getPercentage(day.getDay_end_price(), day.getDay_end_price());
		return KData.newBuilder().setDayTime(String.valueOf(time)).setEndPrice((int) (day.getDay_end_price() * 100))
				.setMinPrice((int) (day.getDay_end_price() * 100)).setPercentage(percentage)
				.setStartPrice((int) (day.getDay_end_price() * 100)).setSumCount(0).setSumPrice(0)
				.setMaxPrice((int) (day.getDay_end_price() * 100)).build();
	}

	/**
	 * 设置5分时的Data
	 * 
	 * @param mink
	 * @return
	 */
	@Override
	public KData getMinkData(MinkBO mink) {
		int percentage = DateUtils.getPercentage(mink.getMin_start_price(), mink.getMin_end_price());
		return KData.newBuilder().setDayTime(DateUtils.getTime(mink.getMin_time()))
				.setEndPrice((int) (mink.getMin_end_price() * 100)).setMinPrice((int)( mink.getMin_min_price() * 100))
				.setPercentage(percentage).setStartPrice((int) (mink.getMin_start_price() * 100))
				.setSumCount(mink.getMin_sum_count() * 2)
				.setSumPrice((int) (DateUtils.changeSum(2, mink.getMin_sum_price())) * 100)
				.setMaxPrice((int) (mink.getMin_max_price() * 100)).build();
	}

	/**
	 * 获得当天的分K
	 * 
	 * @param type
	 * @return
	 */
	public List<MinkBO> addMinkByToday(String type) {
		List<MinkBO> minkList = MinKCache.getMinkList(type);
		if (minkList == null || minkList.size() == 0) {
			minkList = new ArrayList<>();
			List<MinkBO> minks = minkDAO.getNowDay(type);
			if (minks != null) {
				minkList.addAll(minks);
			}
			MinKCache.getMapType().put(type, minkList);
		}
		return minkList;
	}

	/**
	 * 设置日K的数据
	 * 
	 * @param dayk
	 * @return
	 */
	@Override
	public KData getDaykData(DaykBO dayk) {
		int percentage = DateUtils.getPercentage(dayk.getDay_start_price(), dayk.getDay_end_price());
		int string = (int) (Double.parseDouble(String.format("%.2f", dayk.getDay_sum_price())) * 100);
		return KData.newBuilder()
				.setEndPrice((int) (Double.parseDouble(DateUtils.getDouble(dayk.getDay_end_price() * 100))))
				.setMinPrice((int) (dayk.getDay_min_price() * 100)).setDayTime(dayk.getDay_time())
				.setPercentage(percentage).setStartPrice((int) (dayk.getDay_start_price() * 100))
				.setSumCount(dayk.getDay_sum_count()).setSumPrice(string)
				.setMaxPrice((int) (dayk.getDay_max_price() * 100)).build();
	}

	@Override
	public KData getAllMinkData(MinkBO minks, int num) {
		return KData.newBuilder().setMinPrice((int)( minks.getMin_min_price() * 100))
				.setPercentage((int) (minks.getMin_percentage() * 100))
				.setStartPrice((int) (minks.getMin_start_price() * 100))
				.setDayTime(DateUtils.getTime(TimeUtils.getDetailTimeStr()))
				.setMaxPrice((int) (minks.getMin_max_price() * 100)).setNumber(num).setPanduan(StateCache.isPanduan())
				.setSumCount(minks.getMin_sum_count() * 2)
				.setSumPrice((int) (DateUtils.changeSum(2, minks.getMin_sum_price())* 100)).build();

	}

	@Override
	public List<String> addTypeList() {
		List<String> typeList = TypeCache.getTypeList();
		if (typeList.isEmpty()) {
			List<DaykBO> list = daykDAO.getTypeDayk();
			if (list != null) {
				for (DaykBO t : list) {
					if (!typeList.contains(t.getDay_type())) {
						typeList.add(t.getDay_type());
					}
				}
			}
		}
		return typeList;
	}
}
