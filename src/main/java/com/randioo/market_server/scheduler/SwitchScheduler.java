package com.randioo.market_server.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.randioo.market_server.cache.file.TimingConfigCache;
import com.randioo.market_server.cache.local.DayKCache;
import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.TradingCache;
import com.randioo.market_server.cache.local.TypeCache;
import com.randioo.market_server.cache.local.mink.FifMinkCache;
import com.randioo.market_server.cache.local.mink.MinKCache;
import com.randioo.market_server.cache.local.mink.OneMinkCache;
import com.randioo.market_server.cache.local.mink.SixtyMinkCache;
import com.randioo.market_server.cache.local.mink.ThirtyMinkCache;
import com.randioo.market_server.comparator.TradingComparator;
import com.randioo.market_server.dao.DaykDAO;
import com.randioo.market_server.dao.FifMinkDAO;
import com.randioo.market_server.dao.MinkDAO;
import com.randioo.market_server.dao.OneMinkDAO;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.dao.SixtyMinkDAO;
import com.randioo.market_server.dao.ThirtyMinkDAO;
import com.randioo.market_server.dao.TradingDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.file.TimingConfig;
import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.module.seller.service.SellerDeleteService;
import com.randioo.market_server.module.seller.service.SellerSelectService;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.SCRefushMink;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.db.IdClassCreator;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Component
public class SwitchScheduler {

	@Autowired
	private MinkDAO minKDao;
	@Autowired
	private DaykDAO dayKDao;
	@Autowired
	private GameDB gameDB;
	@Autowired
	private IdClassCreator idClassCreator;
	@Autowired
	private TradingComparator tradingComparator;
	@Autowired
	private TradingDAO tradingDao;
	@Autowired
	private SellerDAO sellerDAO;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumComponent;
	@Autowired
	private SellerSelectService sellerSelectService;
	@Autowired
	private SellerDeleteService sellerDeleteService;
	@Autowired
	private OneMinkDAO oneMinkDAO;
	@Autowired
	private FifMinkDAO fifMinkDAO;
	@Autowired
	private ThirtyMinkDAO thirtyMinkDAO;
	@Autowired
	private SixtyMinkDAO sixtyMinkDAO;

	private Logger logger = LoggerFactory.getLogger(SwitchScheduler.class);

	@Scheduled(cron = "0 0/5 * * * ?")
	public void getMink() {

		if (panduanTime() != 0) {
			return;
		}
		int weekDay = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekDay);
		if (timing.panduan == 1) {
			return;
		}
		StateCache.setPanduan(false);
		Map<String, List<TradingBO>> map = MinKCache.getTradListFive();
		// 5分钟缓存
		if (map == null || map.isEmpty()) {
			List<String> typeList = addTypeList();
			if (typeList != null) {
				for (String s : typeList) {
					List<TradingBO> tradLists = MinKCache.getTradListFiveType(s);
					if (tradLists == null) {
						tradLists = new ArrayList<>();
						MinKCache.getTradListFive().put(s, tradLists);
					}
				}
			}
		}
		Set<TradingBO> set = new HashSet<>();
		MinkBO minkBO = new MinkBO();
		for (Map.Entry<String, List<TradingBO>> typeMink : map.entrySet()) {
			// 清空缓存的集合
			String type = typeMink.getKey();
			MinkBO lastMink = minKDao.getLastMink(type);
			List<TradingBO> tradingList = map.get(type);
			Set<TradingBO> delTrading = new HashSet<>();
			delTrading.addAll(tradingList);
			minkBO = getMatchMink(map, set, typeMink, type, lastMink);
			typeMink.getValue().removeAll(delTrading);
			Map<String, MinkBO> getTimeMink = MinKCache.getMapTime();
			if (getTimeMink != null) {
				getTimeMink.clear();
			}
		}

		Map<String, List<MinkBO>> oneMap = MinKCache.getMapType();
		List<MinkBO> minkBOs = minKDao.getNowDay(minkBO.getMin_type());
		List<MinkBO> minkList = addMinkByToday(minkBO.getMin_type(), oneMap, minkBOs);
		minkList.add(minkBO);
		getScKmessage(minkBO, 5);
		gameDB.getInsertPool().submit(new EntityRunnable<MinkBO>(minkBO) {
			@Override
			public void run(MinkBO entity) {
				minKDao.insert(entity);
			}
		});
		StateCache.setPanduan(true);
	}

	@Scheduled(cron = "0 0/1 * * * ? ")
	public void getOneMink() {

		if (panduanTime() != 0) {
			return;
		}
		int weekDay = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekDay);
		if (timing.panduan == 1) {
			return;
		}
		StateCache.setOnePanduan(false);
		Map<String, List<TradingBO>> map = OneMinkCache.getTradMap();
		// 1分钟缓存
		List<String> typeList = addTypeList();
		if (!typeList.isEmpty()) {
			for (String s : typeList) {
				List<TradingBO> tradLists = OneMinkCache.getTradList(s);
				if (tradLists == null) {
					tradLists = new ArrayList<>();
					OneMinkCache.getTradMap().put(s, tradLists);
				}
			}
		}
		Set<TradingBO> set = new HashSet<>();
		MinkBO minkBO = new MinkBO();
		for (Map.Entry<String, List<TradingBO>> typeMink : map.entrySet()) {
			// 清空缓存的集合
			String type = typeMink.getKey();
			MinkBO lastMink = oneMinkDAO.getLastMink(type);
			List<TradingBO> tradingList = map.get(type);
			Set<TradingBO> delTrading = new HashSet<>();
			delTrading.addAll(tradingList);
			minkBO = getMatchMink(map, set, typeMink, type, lastMink);
			typeMink.getValue().removeAll(delTrading);
			Map<String, MinkBO> getTimeMink = OneMinkCache.getMapTime();
			if (getTimeMink != null) {
				getTimeMink.clear();
			}
		}
		// map.remove(set);
		Map<String, List<MinkBO>> oneMap = OneMinkCache.getMinkList();
		List<MinkBO> minkBOs = oneMinkDAO.getNowDay(minkBO.getMin_type());
		List<MinkBO> minkList = addMinkByToday(minkBO.getMin_type(), oneMap, minkBOs);
		minkList.add(minkBO);
		getScKmessage(minkBO, 1);
		gameDB.getInsertPool().submit(new EntityRunnable<MinkBO>(minkBO) {
			@Override
			public void run(MinkBO entity) {
				oneMinkDAO.insert(entity);
			}
		});
		StateCache.setOnePanduan(true);
	}

	private List<String> addTypeList() {
		List<String> typeList = TypeCache.getTypeList();
		if (typeList.isEmpty()) {
			List<DaykBO> list = dayKDao.getTypeDayk();
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

	@Scheduled(cron = "0 0/15 * * * ? ")
	public void getFifMink() {

		if (panduanTime() != 0) {
			return;
		}
		int weekDay = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekDay);
		if (timing.panduan == 1) {
			return;
		}
		StateCache.setFifPanduan(false);
		Map<String, List<TradingBO>> map = FifMinkCache.getTradMap();
		// 1分钟缓存
		List<String> typeList = addTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				List<TradingBO> tradLists = FifMinkCache.getTradList(s);
				if (tradLists == null) {
					tradLists = new ArrayList<>();
					FifMinkCache.getTradMap().put(s, tradLists);
				}
			}
		}
		Set<TradingBO> set = new HashSet<>();
		MinkBO minkBO = new MinkBO();
		for (Map.Entry<String, List<TradingBO>> typeMink : map.entrySet()) {
			// 清空缓存的集合
			String type = typeMink.getKey();
			MinkBO lastMink = fifMinkDAO.getLastMink(type);
			List<TradingBO> tradingList = map.get(type);
			Set<TradingBO> delTrading = new HashSet<>();
			delTrading.addAll(tradingList);
			minkBO = getMatchMink(map, set, typeMink, type, lastMink);
			typeMink.getValue().removeAll(delTrading);
			Map<String, MinkBO> getTimeMink = FifMinkCache.getMapTime();
			if (getTimeMink != null) {
				getTimeMink.clear();
			}
		}
		Map<String, List<MinkBO>> oneMap = FifMinkCache.getMinkList();
		List<MinkBO> minkBOs = fifMinkDAO.getNowDay(minkBO.getMin_type());
		List<MinkBO> minkList = addMinkByToday(minkBO.getMin_type(), oneMap, minkBOs);
		minkList.add(minkBO);
		getScKmessage(minkBO, 15);
		gameDB.getInsertPool().submit(new EntityRunnable<MinkBO>(minkBO) {
			@Override
			public void run(MinkBO entity) {
				fifMinkDAO.insert(entity);
			}
		});
		StateCache.setFifPanduan(true);
	}

	@Scheduled(cron = "0 0/30 * * * ? ")
	public void getThirtyMink() {

		if (panduanTime() != 0) {
			return;
		}
		int weekDay = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekDay);
		if (timing.panduan == 1) {
			return;
		}
		StateCache.setThirtyPanduan(false);
		Map<String, List<TradingBO>> map = ThirtyMinkCache.getTradMap();
		// 1分钟缓存
		List<String> typeList = addTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				List<TradingBO> tradLists = ThirtyMinkCache.getTradList(s);
				if (tradLists == null) {
					tradLists = new ArrayList<>();
					ThirtyMinkCache.getTradMap().put(s, tradLists);
				}
			}
		}
		Set<TradingBO> set = new HashSet<>();
		MinkBO minkBO = new MinkBO();
		for (Map.Entry<String, List<TradingBO>> typeMink : map.entrySet()) {
			// 清空缓存的集合
			String type = typeMink.getKey();
			MinkBO lastMink = thirtyMinkDAO.getLastMink(type);
			List<TradingBO> tradingList = map.get(type);
			Set<TradingBO> delTrading = new HashSet<>();
			delTrading.addAll(tradingList);
			minkBO = getMatchMink(map, set, typeMink, type, lastMink);
			typeMink.getValue().removeAll(delTrading);
			Map<String, MinkBO> getTimeMink = ThirtyMinkCache.getMapTime();
			if (getTimeMink != null) {
				getTimeMink.clear();
			}
		}
		// map.remove(set);
		Map<String, List<MinkBO>> oneMap = ThirtyMinkCache.getMinkList();
		List<MinkBO> minkBOs = thirtyMinkDAO.getNowDay(minkBO.getMin_type());
		List<MinkBO> minkList = addMinkByToday(minkBO.getMin_type(), oneMap, minkBOs);
		minkList.add(minkBO);
		getScKmessage(minkBO, 30);
		gameDB.getInsertPool().submit(new EntityRunnable<MinkBO>(minkBO) {
			@Override
			public void run(MinkBO entity) {
				thirtyMinkDAO.insert(entity);
			}
		});
		StateCache.setThirtyPanduan(true);
	}

	@Scheduled(cron = "0 30/60 * * * ?")
	public void getSixMink() {

		if (panduanTime() != 0) {
			return;
		}
		int weekDay = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekDay);
		if (timing.panduan == 1) {
			return;
		}
		System.out.println(TimeUtils.getDetailTimeStr());
		StateCache.setSixtyPanduan(false);
		Map<String, List<TradingBO>> map = SixtyMinkCache.getTradMap();
		// 1分钟缓存
		List<String> typeList = addTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				List<TradingBO> tradLists = SixtyMinkCache.getTradList(s);
				if (tradLists == null) {
					tradLists = new ArrayList<>();
					SixtyMinkCache.getTradMap().put(s, tradLists);
				}
			}
		}
		Set<TradingBO> set = new HashSet<>();
		MinkBO minkBO = new MinkBO();
		for (Map.Entry<String, List<TradingBO>> typeMink : map.entrySet()) {
			// 清空缓存的集合
			String type = typeMink.getKey();
			MinkBO lastMink = sixtyMinkDAO.getLastMink(type);
			List<TradingBO> tradingList = map.get(type);
			Set<TradingBO> delTrading = new HashSet<>();
			delTrading.addAll(tradingList);
			minkBO = getMatchMink(map, set, typeMink, type, lastMink);
			typeMink.getValue().removeAll(delTrading);
			Map<String, MinkBO> getTimeMink = SixtyMinkCache.getMapTime();
			if (getTimeMink != null) {
				getTimeMink.clear();
			}
		}

		Map<String, List<MinkBO>> oneMap = SixtyMinkCache.getMinkList();
		List<MinkBO> minkBOs = sixtyMinkDAO.getNowDay(minkBO.getMin_type());
		List<MinkBO> minkList = addMinkByToday(minkBO.getMin_type(), oneMap, minkBOs);
		minkList.add(minkBO);
		getScKmessage(minkBO, 60);
		gameDB.getInsertPool().submit(new EntityRunnable<MinkBO>(minkBO) {
			@Override
			public void run(MinkBO entity) {
				sixtyMinkDAO.insert(entity);
			}
		});
		StateCache.setSixtyPanduan(true);
	}

	private MinkBO getMatchMink(Map<String, List<TradingBO>> map, Set<TradingBO> set,
			Map.Entry<String, List<TradingBO>> typeMink, String type, MinkBO lastMink) {
		MinkBO mink = new MinkBO();
		List<TradingBO> tradingList = map.get(type);
		// 没有交易信息
		if (tradingList == null || tradingList.size() == 0) {
			if (lastMink == null) {
				// 获取昨日的K线图
				DaykBO dayk = dayKDao.getLastDayk(type);
				if (dayk != null) {
					mink = setMink(type, mink, dayk);
				} else {
					mink = setMinkNo(type, mink);
				}
			} else {
				mink = setMinkByLast(type, mink, lastMink);
			}
		} else {
			double minPrice = 0.00;
			double maxPrice = 0.00;
			double startPrice = 0.00;
			double sumPrice = 0.00;
			int sumCount = 0;
			double endPrice = 0.00;
			double min_percentage = 0.00;
			if (lastMink == null) {
				DaykBO dayk = dayKDao.getLastDayk(type);
				if (dayk != null) {
					startPrice = dayk.getDay_end_price();
					minPrice = dayk.getDay_end_price();
					maxPrice = dayk.getDay_end_price();
				}
			} else {
				startPrice = lastMink.getMin_end_price();
				minPrice = lastMink.getMin_end_price();
				maxPrice = lastMink.getMin_end_price();
			}
			// 计算总价和总数量
			for (TradingBO trad : tradingList) {
				set.add(trad);
				sumPrice = sumPrice + trad.getTrad_sum();// 总成交额
				sumCount = sumCount + trad.getTrad_count();// 总成交数量
			}
			if (sumPrice == 0 || sumCount == 0) {
				endPrice = startPrice;
			} else {
				endPrice = sumPrice / sumCount;
				String s = String.format("%.2f", endPrice);
				endPrice = Double.parseDouble(s);
			}

			Collections.sort(tradingList, tradingComparator);
			if (tradingList.size() > 0) {
				minPrice = tradingList.get(0).getTrad_price();// 最低价格
				maxPrice = tradingList.get(tradingList.size() - 1).getTrad_price();// 最高价格
			}
			if (minPrice >= endPrice && endPrice <= startPrice) {
				minPrice = endPrice;
			}
			if (maxPrice <= endPrice && endPrice >= startPrice) {
				maxPrice = endPrice;
			}
			if (startPrice <= minPrice && startPrice <= endPrice) {
				minPrice = startPrice;
			}
			if (startPrice >= maxPrice && startPrice >= endPrice) {
				maxPrice = startPrice;
			}
			min_percentage = (endPrice - startPrice) / startPrice;
			String e = String.format("%.6f", min_percentage);
			mink.setMin_percentage(Double.parseDouble(e));
			mink.setMin_max_price(maxPrice);// 最大值
			mink.setMin_min_price(minPrice);// 最小值
			mink.setMin_sum_count(sumCount);// 总数
			mink.setMin_sum_price(sumPrice);// 总价格
			mink.setMin_time(DateUtils.getRounding());// 时间
			mink.setMin_start_price(startPrice);// 开盘价格
			mink.setMin_end_price(endPrice);// 收盘价格
			mink.setMin_type(type);// 类型
		}
		typeMink.getValue().removeAll(set);
		return mink;
	}

	public void getScKmessage(MinkBO minkBO, int num) {
		SCRefushMink.Builder builders = SCRefushMink.newBuilder();
		int percentage = 0;
		if (minkBO.getMin_end_price() > minkBO.getMin_start_price()) {
			percentage = 1;
		} else if (minkBO.getMin_end_price() < minkBO.getMin_start_price()) {
			percentage = -1;
		} else {
			percentage = 0;
		}
		builders.addKData(KData.newBuilder().setDayTime(DateUtils.getTime(minkBO.getMin_time()))
				.setEndPrice((int) (minkBO.getMin_end_price() * 100))
				.setMinPrice((int) (minkBO.getMin_min_price() * 100)).setPercentage(percentage)
				.setStartPrice((int) (minkBO.getMin_start_price() * 100)).setSumCount(minkBO.getMin_sum_count() * 2)
				.setSumPrice((int) (DateUtils.changeSum(2, minkBO.getMin_sum_price() * 100)))
				.setMaxPrice((int) (minkBO.getMin_max_price() * 100)).setPanduan(false).setNumber(num));
		SC sc = SC.newBuilder().setSCRefushMink(builders).build();
		for (IoSession session : SessionCache.getAllSession()) {
			SessionUtils.sc(session, sc);
		}

	}

	private List<MinkBO> addMinkByToday(String type, Map<String, List<MinkBO>> map, List<MinkBO> minks) {
		List<MinkBO> minkList = map.get(type);
		if (minkList == null) {
			minkList = new ArrayList<>();
			if (minks != null) {
				minkList.addAll(minks);
			}
			map.put(type, minkList);
		}
		return minkList;
	}

	private MinkBO setMinkByLast(String type, MinkBO mink, MinkBO lastMink) {
		mink.setMin_end_price(lastMink.getMin_end_price());
		mink.setMin_max_price(lastMink.getMin_end_price());
		mink.setMin_min_price(lastMink.getMin_end_price());
		mink.setMin_start_price(lastMink.getMin_end_price());
		mink.setMin_type(type);
		mink.setMin_percentage(0);
		mink.setMin_sum_count(0);
		mink.setMin_time(TimeUtils.getDetailTimeStr());
		mink.setMin_sum_price(0);
		return mink;
	}

	private MinkBO setMinkNo(String type, MinkBO mink) {
		mink.setMin_end_price(0);
		mink.setMin_max_price(0);
		mink.setMin_min_price(0);
		mink.setMin_start_price(0);
		mink.setMin_type(type);
		mink.setMin_percentage(0);
		mink.setMin_sum_count(0);
		mink.setMin_time(TimeUtils.getDetailTimeStr());
		mink.setMin_sum_price(0);
		return mink;
	}

	private MinkBO setMink(String type, MinkBO mink, DaykBO dayk) {
		mink.setMin_end_price(dayk.getDay_end_price());
		mink.setMin_max_price(dayk.getDay_end_price());
		mink.setMin_min_price(dayk.getDay_end_price());
		mink.setMin_start_price(dayk.getDay_end_price());
		mink.setMin_type(type);
		mink.setMin_percentage(0);
		mink.setMin_sum_count(0);
		mink.setMin_time(TimeUtils.getDetailTimeStr());
		mink.setMin_sum_price(0);
		return mink;
	}

	// 获得星期几的编号
	public int getWeekday() {
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		return weekday;
	}

	@Scheduled(cron = "0 00 16 * * ?")
	public void deleteMessage() {

		Map<String, List<TradingBO>> tradingList = TradingCache.getTradingMap();
		Iterator<Entry<String, List<TradingBO>>> it3 = tradingList.entrySet().iterator();
		while (it3.hasNext()) {
			it3.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it3.remove();
		}
		if (tradingList != null) {
			tradingList.clear();
		}
		List<SellerBO> list = sellerDAO.selectSellerByState(Constant.YES);
		List<String> strings = new ArrayList<>();
		for (SellerBO seller : list) {
			Role role = sellerLogicNumComponent.getRoleByAccount(seller.getSell_account());
			NumbersBO num = sellerLogicNumComponent.getNumbers(seller.getSell_account(), seller.getSell_type());
			// logger.info("撤销之前用户帐号" + role.getAccount() + "用户金额" +
			// role.getRole_rmbA() + "撤销用户帐号"
			// + seller.getSell_account() + "撤销的价格" + seller.getSell_price() +
			// "撤销类型" + seller.getSell_type()
			// + "用户撤销之前数据" + num.getNum_count());
			seller.setSell_active(Constant.STOP_SELL);
			sellerLogicNumComponent.updateRevokeNumbers(seller, seller.getSell_count() - seller.getSell_overCount());
			sellerDeleteService.updateGoodsRevoke(seller);

			sellerDeleteService.updateSeller(seller);
			strings.add(seller.getSell_account());
			gameDB.getUpdatePool().submit(new EntityRunnable<SellerBO>(seller) {

				@Override
				public void run(SellerBO entity) {
					sellerDAO.update(entity);
				}
			});
			sellerSelectService.scMySellerRequesr(seller.getSell_account());
			
			logger.info("撤销之后用户帐号" + role.getAccount() + "用户金额" + role.getRole_rmbA() + "撤销用户帐号"
					+ seller.getSell_account() + "撤销的价格" + seller.getSell_price() + "撤销类型" + seller.getSell_type()
					+ "用户撤销之hou数据" + num.getNum_count());
		}
		Map<String, List<SellerBO>> sellerList = SellerCache.getSellerByAll();
		Iterator<Entry<String, List<SellerBO>>> it = sellerList.entrySet().iterator();
		while (it.hasNext()) {
			it.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it.remove();
		}
		Map<String, List<SellerBO>> sellerListByType = SellerCache.getSellerByType();
		Iterator<Entry<String, List<SellerBO>>> it1 = sellerListByType.entrySet().iterator();
		while (it.hasNext()) {
			it1.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it1.remove();
		}
		Map<String, List<SellerBO>> sellerListByAccount = SellerCache.getSellerMapByAccount();
		Iterator<Entry<String, List<SellerBO>>> it2 = sellerListByAccount.entrySet().iterator();
		while (it2.hasNext()) {
			it2.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it2.remove();
		}
		for (String s : strings) {
			sellerSelectService.scMySellerRequesr(s);
		}

		Map<String, List<TradingBO>> tradingLists = TradingCache.getTradingMap();
		if (tradingLists != null) {
			tradingLists.clear();
		}
	}

	@Scheduled(cron = "0 00 16 * * ?")
	public void deleteCache() {
		Map<String, List<SellerBO>> sellerList = SellerCache.getSellerByAll();
		if (sellerList != null) {
			sellerList.clear();
		}
		Map<String, List<SellerBO>> sellerListByType = SellerCache.getSellerByType();
		if (sellerListByType != null) {
			sellerListByType.clear();
		}
		Map<String, List<SellerBO>> sellerListByAccount = SellerCache.getSellerMapByAccount();
		if (sellerListByAccount != null) {
			sellerListByAccount.clear();
		}
		Map<String, List<TradingBO>> tradingList = TradingCache.getTradingMap();
		if (tradingList != null) {
			tradingList.clear();
		}
	}

	@Scheduled(cron = "0 00 00 * * ?")
	private void deleteTrading() {
		Map<String, List<TradingBO>> tradingList = TradingCache.getTradingMap();
		Iterator<Entry<String, List<TradingBO>>> it3 = tradingList.entrySet().iterator();
		while (it3.hasNext()) {
			it3.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it3.remove();
		}
		if (tradingList != null) {
			tradingList.clear();
		}
		Map<String, List<SellerBO>> sellerList = SellerCache.getSellerByAll();
		if (sellerList != null) {
			sellerList.clear();
		}
		Map<String, List<SellerBO>> sellerListByType = SellerCache.getSellerByType();
		if (sellerListByType != null) {
			sellerListByType.clear();
		}
		Map<String, List<SellerBO>> sellerListByAccount = SellerCache.getSellerMapByAccount();
		if (sellerListByAccount != null) {
			sellerListByAccount.clear();
		}
		Map<String, List<TradingBO>> tradingLists = TradingCache.getTradingMap();
		if (tradingLists != null) {
			tradingLists.clear();
		}
	}

	@Scheduled(cron = "0 01 00 * * ?")
	private void deleteTrad() {
		Map<String, List<TradingBO>> tradingList = TradingCache.getTradingMap();
		Iterator<Entry<String, List<TradingBO>>> it3 = tradingList.entrySet().iterator();
		while (it3.hasNext()) {
			it3.next();
			// 此处可以做额外的判断，相应符合条件的元素
			it3.remove();
		}
		if (tradingList != null) {
			tradingList.clear();
		}
		Map<String, List<SellerBO>> sellerList = SellerCache.getSellerByAll();
		if (sellerList != null) {
			sellerList.clear();
		}
		Map<String, List<SellerBO>> sellerListByType = SellerCache.getSellerByType();
		if (sellerListByType != null) {
			sellerListByType.clear();
		}
		Map<String, List<SellerBO>> sellerListByAccount = SellerCache.getSellerMapByAccount();
		if (sellerListByAccount != null) {
			sellerListByAccount.clear();
		}
		Map<String, List<TradingBO>> tradingLists = TradingCache.getTradingMap();
		if (tradingLists != null) {
			tradingLists.clear();
		}
	}
	
	@Scheduled(cron = "0 30 15 * * ?")
	public void getDayK() {

		int weekDay = getWeekday();
		if (weekDay == 1 || weekDay == 7) {
			return;
		}
		TimingConfig timing = TimingConfigCache.getConfigById(weekDay);
		if (timing.panduan == 1) {
			return;
		}
		Map<String, List<TradingBO>> tradingMap = DayKCache.getTradMapToDay();
		List<String> typeList = TypeCache.getTypeList();
		if (typeList != null) {
			for (String s : typeList) {
				List<TradingBO> tradLists = DayKCache.getTradingByTypeTo(s);
				if (tradLists == null) {
					tradLists = new ArrayList<>();
					DayKCache.getTradMapToDay().put(s, tradLists);
				}
			}
		}
		Set<TradingBO> set = new HashSet<>();
		if (tradingMap != null) {
			for (Map.Entry<String, List<TradingBO>> typeDayk : DayKCache.getTradMapToDay().entrySet()) {
				Set<TradingBO> delTrading = new HashSet<>();
				String type = typeDayk.getKey();
				List<TradingBO> tradList = tradingDao.selectListByTypeToday(typeDayk.getKey());
				if (tradList == null || tradList.size() == 0) {
					tradList = new ArrayList<>();
					List<TradingBO> trad = tradingDao.selectListByTypeToday(typeDayk.getKey());
					if (trad != null) {
						tradList.addAll(trad);
					}
					DayKCache.getTradMapToDay().put(typeDayk.getKey(), tradList);
				}

				delTrading.addAll(tradList);
				double sumPrice = 0;
				int sumCount = 0;
				double minPrice = 0;
				double maxPrice = 0;
				double endPrice = 0;
				double percentage = 0;
				double startPrice = 0;
				DaykBO lastDayk = dayKDao.getLastDayk(type);
				if (lastDayk != null) {
					startPrice = lastDayk.getDay_end_price();
					minPrice = lastDayk.getDay_end_price();
					maxPrice = lastDayk.getDay_end_price();
					endPrice = lastDayk.getDay_end_price();
					percentage = (endPrice - lastDayk.getDay_end_price()) / lastDayk.getDay_end_price();
					percentage = Double.parseDouble(String.format("%.6f", percentage));
				}
				for (TradingBO tr : tradList) {
					sumPrice = tr.getTrad_sum() + sumPrice;// 总成交额
					sumCount = tr.getTrad_count() + sumCount;// 总成交数量
					set.add(tr);
				}
				if (sumPrice > 0 && sumCount > 0) {
					endPrice = sumPrice / sumCount;// 收盘价格
					String s = String.format("%.2f", endPrice);
					endPrice = Double.parseDouble(s);
				}
				Collections.sort(tradList, tradingComparator);
				if (tradList.size() > 0) {
					minPrice = tradList.get(0).getTrad_price();// 最低价格
					maxPrice = tradList.get(tradList.size() - 1).getTrad_price();// 最高价格
				}
				// 最高价最低价和收盘价开盘价比较
				if (startPrice >= maxPrice && startPrice >= endPrice) {
					maxPrice = startPrice;
				}
				if (startPrice < minPrice && startPrice < endPrice) {
					minPrice = startPrice;
				}
				if (endPrice >= maxPrice && endPrice >= startPrice) {
					maxPrice = endPrice;
				}
				if (endPrice <= minPrice && endPrice <= startPrice) {
					minPrice = endPrice;
				}
				DaykBO dayk = new DaykBO();
				int day_id = idClassCreator.getId(DaykBO.class);
				percentage = (endPrice - startPrice) / startPrice;
				percentage = Double.parseDouble(String.format("%.6f", percentage));
				dayk.setDay_start_price(startPrice);
				dayk.setDay_end_price(endPrice);
				dayk.setDay_id(day_id);
				dayk.setDay_min_price(minPrice);
				dayk.setDay_max_price(maxPrice);
				dayk.setDay_percentage(percentage);
				dayk.setDay_sum_count(sumCount);
				dayk.setDay_sum_price(sumPrice);
				dayk.setDay_type(typeDayk.getKey());
				dayk.setDay_time(TimeUtils.getCurrentTimeStr());
				getDinkListType(type, dayk);
				typeDayk.getValue().removeAll(delTrading);
				gameDB.getInsertPool().submit(new EntityRunnable<DaykBO>(dayk) {

					@Override
					public void run(DaykBO entity) {
						dayKDao.insert(entity);
					}
				});
			}
		}
		tradingMap.remove(set);
	}

	public int panduanTime() {
		int weekday = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekday);
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = ca.getTime();
		String nowDateStr = sdf.format(nowDate);
		String ymd = nowDateStr.split(" ")[0];

		String mStartTimeStr = ymd + " " + timing.mStartTime;
		String mEndTimeStr = ymd + " " + timing.mEndTime;
		String aStartTimeStr = ymd + " " + timing.aStartTime;
		String aEndTimeStr = ymd + " " + timing.aEndTime;
		long time = DateUtils.getLongTime(TimeUtils.getDetailTimeStr());
		long mtime = DateUtils.getLongTime(mStartTimeStr);
		long meTime = DateUtils.getLongTime(mEndTimeStr);
		long atime = DateUtils.getLongTime(aStartTimeStr);
		long aetime = DateUtils.getLongTime(aEndTimeStr);
		if (time > mtime && time <= meTime) {
			return 0;
		} else if (time > meTime && time < atime) {
			return 1;
		} else if (time > atime && time <= aetime) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 根据类型获得日K
	 * 
	 * @param type
	 * @return
	 */
	private void getDinkListType(String type, DaykBO dayk) {
		List<DaykBO> dayList = DayKCache.getDaykList(type);
		if (dayList == null) {
			dayList = new ArrayList<>();
			List<DaykBO> list = dayKDao.selectAll(type);
			if (list != null) {
				dayList.addAll(list);
			}
			DayKCache.getDaykType().put(type, dayList);
		}
		dayList.add(dayk);
	}
}