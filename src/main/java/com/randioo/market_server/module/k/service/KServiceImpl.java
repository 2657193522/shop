package com.randioo.market_server.module.k.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.local.DayKCache;
import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.mink.FifMinkCache;
import com.randioo.market_server.cache.local.mink.MinKCache;
import com.randioo.market_server.cache.local.mink.OneMinkCache;
import com.randioo.market_server.cache.local.mink.SixtyMinkCache;
import com.randioo.market_server.cache.local.mink.ThirtyMinkCache;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.dao.FifMinkDAO;
import com.randioo.market_server.dao.MinkDAO;
import com.randioo.market_server.dao.OneMinkDAO;
import com.randioo.market_server.dao.SixtyMinkDAO;
import com.randioo.market_server.dao.ThirtyMinkDAO;
import com.randioo.market_server.dao.TradingDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.buyer.component.trading.InsertTradingComponent;
import com.randioo.market_server.module.k.state.StateDayk;
import com.randioo.market_server.module.k.state.StateMink;
import com.randioo.market_server.protocol.Error.ErrorCode;
import com.randioo.market_server.protocol.K.KResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.db.IdClassCreator;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.Observer;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("kService")
public class KServiceImpl extends ObserveBaseService implements Observer, KService {

	@Autowired
	private MinkDAO minKDao;
	@Autowired
	private DaykDAO dayKDao;
	@Autowired
	private IdClassCreator idClassCreator;
	@Autowired
	private TradingDAO tradingDao;
	@Autowired
	private Role roleChange;
	@Autowired
	private InsertTradingComponent insertTradingComponent;
	@Autowired
	private StateMink stateMink;
	@Autowired
	private StateDayk stateDayk;
	@Autowired
	private OneMinkDAO oneMinkDAO;
	@Autowired
	private FifMinkDAO fifMinkDAO;
	@Autowired
	private ThirtyMinkDAO thirtyMinkDAO;
	@Autowired
	private SixtyMinkDAO sixMinkDAO;

	@Override
	public void init() {
		Integer id = minKDao.selectId();
		idClassCreator.initId(MinkBO.class, id == null ? 0 : id);

		Integer maxDayId = dayKDao.selectId();
		idClassCreator.initId(DaykBO.class, maxDayId == null ? 0 : maxDayId);
		insertTradingComponent.addObserver(this);
	}

	@Override
	public void initService() {
		StateCache.setFifPanduan(true);
		StateCache.setOnePanduan(true);
		StateCache.setPanduan(true);
		StateCache.setSixtyPanduan(true);
		StateCache.setThirtyPanduan(true);

	}

	/**
	 * 初始进来请求K线，根据服务器开关 KACTION
	 * 
	 */
	@Override
	public GeneratedMessage getK(String type, int sumDay) {
		KResponse.Builder builder=KResponse.newBuilder();
		if(type==null||type.equals("")){
			return SC.newBuilder().setKResponse(builder.setErrorCode(ErrorCode.INFORMATION_IS_NOT.getNumber())).build();
		}
		if (roleChange.isJudge()) {
			return stateMink.getMink(type);
		} else {
			return stateDayk.getMink(type);
		}
	}

	/**
	 * 成交后通知 收到通知生成数据并统计进行计算 生成5分钟K线图数据 累计数据生成日K线图
	 */
	@Override
	public void update(Observer observer, String msg, Object... args) {
		// TODO
		if (msg.equals(Constant.NOTICE)) {
			// 拿到交易信息
			TradingBO trading = (TradingBO) args[0];
			// 获得实时变化的5分K
			fiveMinkNowTime(trading);
			// 获得一分钟的实时变化
			oneMinkNowTime(trading);
			// 获得15分钟实时变化
			fifMinkNowTime(trading);
			// 获得30分钟实时变化
			thirtyMinkNowTime(trading);
			// 获得60分钟实时变化
			sixMinkNowTime(trading);

			// 当天成交记录
			getTradByType(trading);
			// 5分钟成交记录
			addFiveMink(trading);
			// 1分钟成交记录
			addOneMink(trading);
			// 15分钟成交记录
			addFifMink(trading);
			// 30分钟成交记录
			addThirtyMink(trading);
			// 60分钟成交记录
			addSixMink(trading);
		}
	}

	private void addSixMink(TradingBO trading) {
		// TODO Auto-generated method stub
		List<TradingBO> tradingList = SixtyMinkCache.getTradList(trading.getTrad_type());
		if (tradingList == null) {
			tradingList = new ArrayList<>();
		}
		SixtyMinkCache.getTradMap().put(trading.getTrad_type(), tradingList);
		tradingList.add(trading);
	}

	/**
	 * 30分钟成交记录
	 * 
	 * @param trading
	 */
	private void addThirtyMink(TradingBO trading) {
		// TODO Auto-generated method stub
		List<TradingBO> tradingList = ThirtyMinkCache.getTradList(trading.getTrad_type());
		if (tradingList == null) {
			tradingList = new ArrayList<>();
		}
		ThirtyMinkCache.getTradMap().put(trading.getTrad_type(), tradingList);
		tradingList.add(trading);
	}

	/**
	 * 60分钟实时变化
	 * 
	 * @param trading
	 */
	private void sixMinkNowTime(TradingBO trading) {
		// TODO Auto-generated method stub
		MinkBO lastMink = sixMinkDAO.getLastMink(trading.getTrad_type());
		Map<String, MinkBO> map = SixtyMinkCache.getMapTime();
		getMinkNow(trading, lastMink, map);
	}

	/**
	 * 30分钟实时变化
	 * 
	 * @param trading
	 */
	private void thirtyMinkNowTime(TradingBO trading) {
		// TODO Auto-generated method stub
		MinkBO lastMink = thirtyMinkDAO.getLastMink(trading.getTrad_type());
		Map<String, MinkBO> map = ThirtyMinkCache.getMapTime();
		getMinkNow(trading, lastMink, map);
	}

	/**
	 * 15分钟实时变化
	 * 
	 * @param trading
	 */
	private void fifMinkNowTime(TradingBO trading) {
		// TODO Auto-generated method stub
		MinkBO lastMink = fifMinkDAO.getLastMink(trading.getTrad_type());
		Map<String, MinkBO> map = FifMinkCache.getMapTime();
		getMinkNow(trading, lastMink, map);
	}

	/**
	 * 获得实时5分K
	 * 
	 * @param trading
	 */
	private void fiveMinkNowTime(TradingBO trading) {
		MinkBO lastMink = minKDao.getLastMink(trading.getTrad_type());
		Map<String, MinkBO> map = MinKCache.getMapTime();
		getMinkNow(trading, lastMink, map);
	}

	/**
	 * 获得一分钟的实时变化
	 * 
	 * @param trading
	 */
	private void oneMinkNowTime(TradingBO trading) {
		// TODO Auto-generated method stub
		MinkBO lastMink = oneMinkDAO.getLastMink(trading.getTrad_type());
		Map<String, MinkBO> map = OneMinkCache.getMapTime();
		getMinkNow(trading, lastMink, map);

	}

	/**
	 * 15分钟成交记录
	 * 
	 * @param trading
	 */
	private void addFifMink(TradingBO trading) {
		// TODO Auto-generated method stub
		List<TradingBO> tradingList = FifMinkCache.getTradList(trading.getTrad_type());
		if (tradingList == null) {
			tradingList = new ArrayList<>();
		}
		FifMinkCache.getTradMap().put(trading.getTrad_type(), tradingList);
		tradingList.add(trading);
	}

	/**
	 * 添加一分钟成交记录到缓存
	 * 
	 * @param trading
	 */
	private void addOneMink(TradingBO trading) {
		// TODO Auto-generated method stub
		List<TradingBO> tradingList = OneMinkCache.getTradList(trading.getTrad_type());
		if (tradingList == null) {
			tradingList = new ArrayList<>();
		}
		OneMinkCache.getTradMap().put(trading.getTrad_type(), tradingList);
		tradingList.add(trading);

	}

	/**
	 * 添加记录到5分钟缓存
	 * 
	 * @param trading
	 */
	private void addFiveMink(TradingBO trading) {
		List<TradingBO> tradingList = MinKCache.getTradListFiveType(trading.getTrad_type());
		if (tradingList == null) {
			tradingList = new ArrayList<>();
		}
		MinKCache.getTradListFive().put(trading.getTrad_type(), tradingList);
		tradingList.add(trading);
	}

	/**
	 * 获得实时变化分K
	 * 
	 * @param trading
	 */
	private void getMinkNow(TradingBO trading, MinkBO lastMink, Map<String, MinkBO> map) {
		MinkBO mink = new MinkBO();
		if (lastMink == null) {
			DaykBO lastDay = dayKDao.getLastDayk(trading.getTrad_type());
			if (lastDay != null) {
				mink = matchMaxMin(trading, mink, lastDay);
				map.put(trading.getTrad_type(), mink);
			}
		} else {
			mink = matchMinkMin(trading, mink, lastMink);
			MinkBO minkMap = map.get(trading.getTrad_type());
			if (minkMap == null) {
				minkMap = new MinkBO();
				minkMap = setMinkMessage(trading, mink, minkMap, lastMink);
			} else {
				minkMap = setMinkMessage(trading, mink, minkMap, lastMink);
			}
			map.put(trading.getTrad_type(), minkMap);
		}
	}

	/**
	 * 从类型获得所有成交记录
	 * 
	 * @param trading
	 */
	private void getTradByType(TradingBO trading) {
		List<TradingBO> tradList = DayKCache.getTradingByTypeTo(trading.getTrad_type());

		if (tradList == null) {
			tradList = new ArrayList<>();
			List<TradingBO> list = tradingDao.selectListByTypeToday(trading.getTrad_type());// 当天的
			if (list != null) {
				tradList.addAll(list);
				DayKCache.getTradMapToDay().put(trading.getTrad_type(), tradList);
			}
		}
		tradList.add(trading);
	}

	/**
	 * 设置当前随时变化分K数据
	 * 
	 * @param trading
	 * @param mink
	 * @param minkMap
	 */
	private MinkBO setMinkMessage(TradingBO trading, MinkBO mink, MinkBO minkMap, MinkBO lastMink) {
		if (mink.getMin_max_price() > minkMap.getMin_max_price()) {
			minkMap.setMin_max_price(mink.getMin_max_price());
		} else {
			minkMap.setMin_max_price(minkMap.getMin_max_price());
		}
		if (mink.getMin_min_price() < minkMap.getMin_min_price()) {
			minkMap.setMin_min_price(mink.getMin_min_price());
		} else {
			if (minkMap.getMin_min_price() != 0) {
				minkMap.setMin_min_price(minkMap.getMin_min_price());
			} else {
				minkMap.setMin_min_price(mink.getMin_min_price());
			}
		}
		minkMap.setMin_start_price(mink.getMin_start_price());
		minkMap.setMin_percentage(mink.getMin_percentage() * 100);
		minkMap.setMin_type(trading.getTrad_type());
		minkMap.setMin_time(TimeUtils.getDetailTimeStr());
		minkMap.setMin_sum_count(minkMap.getMin_sum_count() + mink.getMin_sum_count());
		minkMap.setMin_sum_price(minkMap.getMin_sum_price() + mink.getMin_sum_price());
		return minkMap;
	}

	/**
	 * 比较分K最大最小值
	 * 
	 * @param trading
	 * @param mink
	 * @param lastMink
	 */
	private MinkBO matchMinkMin(TradingBO trading, MinkBO mink, MinkBO lastMink) {
		mink.setMin_start_price(lastMink.getMin_end_price());
		mink.setMin_sum_price(trading.getTrad_sum());
		mink.setMin_sum_count(trading.getTrad_count());
		if (trading.getTrad_price() > lastMink.getMin_end_price()) {
			mink.setMin_max_price(trading.getTrad_price());
			mink.setMin_min_price(lastMink.getMin_end_price());
			mink.setMin_type(trading.getTrad_type());
			mink.setMin_percentage(
					((trading.getTrad_price() - lastMink.getMin_end_price()) / lastMink.getMin_end_price()));
		} else if (trading.getTrad_price() < lastMink.getMin_end_price()) {
			mink.setMin_min_price(trading.getTrad_price());
			mink.setMin_max_price(lastMink.getMin_end_price());
			mink.setMin_type(trading.getTrad_type());
			mink.setMin_percentage(
					((trading.getTrad_price() - lastMink.getMin_end_price()) / lastMink.getMin_end_price()));
		} else {
			mink.setMin_type(trading.getTrad_type());
			mink.setMin_min_price(trading.getTrad_price());
			mink.setMin_max_price(lastMink.getMin_end_price());
			if (trading.getTrad_price() > mink.getMin_start_price()) {
				mink.setMin_percentage(0.1);
			} else {
				mink.setMin_percentage(-0.1);
			}
		}
		return mink;
	}

	private MinkBO matchMaxMin(TradingBO trading, MinkBO mink, DaykBO lastDay) {
		mink.setMin_start_price(lastDay.getDay_end_price());
		mink.setMin_sum_count(trading.getTrad_count());
		mink.setMin_sum_price(trading.getTrad_sum());
		if (trading.getTrad_price() > lastDay.getDay_end_price()) {
			mink.setMin_max_price(trading.getTrad_price());
			mink.setMin_min_price(lastDay.getDay_end_price());
			mink.setMin_percentage(
					((trading.getTrad_price() - lastDay.getDay_end_price()) / lastDay.getDay_end_price()));
		} else if (trading.getTrad_price() < lastDay.getDay_end_price()) {
			mink.setMin_min_price(trading.getTrad_price());
			mink.setMin_max_price(lastDay.getDay_end_price());
			mink.setMin_percentage(
					((trading.getTrad_price() - lastDay.getDay_end_price()) / lastDay.getDay_end_price()));
		} else {
			mink.setMin_min_price(trading.getTrad_price());
			mink.setMin_max_price(lastDay.getDay_end_price());
			if (lastDay.getDay_end_price() > trading.getTrad_price()) {
				mink.setMin_percentage(0.1);
			} else {
				mink.setMin_percentage(-0.1);
			}
		}
		return mink;
	}

}