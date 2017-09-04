package com.randioo.market_server.module.systemInfo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.market_server.cache.file.TimingConfigCache;
import com.randioo.market_server.dao_remote.CurrencyDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.file.TimingConfig;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.protocol.Gm.SCFight;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.protocol.SystemInfo.SystemInfoResponse;
import com.randioo.market_server.util.DateUtils;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Service("systemService")
public class SystemInfoServiceImpl extends ObserveBaseService implements SystemInfoService {
	@Autowired
	private Role roleChange;
//	@Autowired
//	private CurrencyDAO currencyDAO;
	

	@Override
	public void initService() {
		 ServiceJudge();
//		 currencyDAO.getType(29);
	}

	@Override
	public GeneratedMessage getTime() {
		int weekday = DateUtils.getWeekday();
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

		Date nowTime = null;
		Date mStartTime = null;
		Date mEndTime = null;
		Date aStartTime = null;
		Date aEndTime = null;
		try {
			nowTime = sdf.parse(TimeUtils.getDetailTimeStr());
			mStartTime = sdf.parse(mStartTimeStr);
			mEndTime = sdf.parse(mEndTimeStr);
			aStartTime = sdf.parse(aStartTimeStr);
			aEndTime = sdf.parse(aEndTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		if (weekday == 1 || weekday == 7) {
//			roleChange.setChange(false);
//			roleChange.setJudge(false);
//			return SC.newBuilder().setSystemInfoResponse(SystemInfoResponse.newBuilder().setState(Constant.CLOSE_EVER)
//					.setSerTime(DateUtils.getTime(TimeUtils.getDetailTimeStr()))).build();
//		}
		if (timing.panduan == 1) {
			return SC.newBuilder().setSystemInfoResponse(SystemInfoResponse.newBuilder().setState(Constant.CLOSE_EVER)
					.setSerTime(DateUtils.getTime(TimeUtils.getDetailTimeStr()))).build();
		}
		if (roleChange.isJudge()) {
			if (nowTime.after(mStartTime) && nowTime.before(mEndTime)) {
				return SC.newBuilder()
						.setSystemInfoResponse(SystemInfoResponse.newBuilder()
								.setEndTime(DateUtils.getTime(mEndTimeStr)).setSerTime(DateUtils.getTime(nowDateStr))
								.setState(Constant.START).setStartTime(DateUtils.getTime(mStartTimeStr)))
						.build();
			}
			if (nowTime.after(aStartTime) && nowTime.before(aEndTime)) {
				return SC.newBuilder()
						.setSystemInfoResponse(SystemInfoResponse.newBuilder()
								.setEndTime(DateUtils.getTime(String.valueOf(mEndTimeStr)))
								.setSerTime(DateUtils.getTime(String.valueOf(nowDateStr))).setState(Constant.START)
								.setStartTime(DateUtils.getTime(mStartTimeStr)))
						.build();
			}
		}
		if (nowTime.after(mEndTime) && nowTime.before(aStartTime)) {
			return SC.newBuilder()
					.setSystemInfoResponse(SystemInfoResponse.newBuilder().setEndTime(DateUtils.getTime(aEndTimeStr))
							.setSerTime(DateUtils.getTime(nowDateStr)).setState(Constant.FREE_TIME)
							.setStartTime(DateUtils.getTime(aStartTimeStr)))
					.build();
		}
		return SC.newBuilder()
				.setSystemInfoResponse(SystemInfoResponse.newBuilder().setEndTime(DateUtils.getTime(mEndTimeStr))
						.setSerTime(DateUtils.getTime(nowDateStr)).setState(Constant.CLOSE)
						.setStartTime(DateUtils.getTime(mStartTimeStr)))
				.build();
	}

	@Scheduled(cron = "0/5 * * * * ? ")
	public void SCService() {

		// ServiceJudge();
		int weekday = DateUtils.getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekday);
		if (timing.panduan == 1) {
			roleChange.setJudge(false);
			return;
		}
//		if (weekday == 1 || weekday == 7) {
//			roleChange.setJudge(false);
//			roleChange.setChange(false);
//		} else {
			if (roleChange.isJudge()) {
				if (panduanTime() != 0) {
					roleChange.setJudge(false);
					roleChange.setChange(true);
				}
				if (roleChange.isChange()) {
					GeneratedMessage sc = null;
					if (panduanTime() == 1) {
						sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.FREE_TIME)).build();
						for (IoSession session : SessionCache.getAllSession()) {
							SessionUtils.sc(session, sc);
						}
					} else if (panduanTime() == -1) {
						sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.CLOSE)).build();
						for (IoSession session : SessionCache.getAllSession()) {
							SessionUtils.sc(session, sc);
						}
					}
				}
				roleChange.setChange(false);
			} else if (!roleChange.isJudge()) {
				if (panduanTime() == 0) {
					roleChange.setJudge(true);
					roleChange.setChange(true);
					if (roleChange.isChange()) {
						GeneratedMessage sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.START))
								.build();
						for (IoSession session : SessionCache.getAllSession()) {
							SessionUtils.sc(session, sc);
						}
					}
					roleChange.setChange(false);
				}
			}
		}
//	}

	/**
	 * 判断服务器开关
	 */
	@Override
	public void ServiceJudge() {
		int weekday = DateUtils.getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekday);
		if (timing.panduan == 1) {
			GeneratedMessage sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.START)).build();
			for (IoSession session : SessionCache.getAllSession()) {
				SessionUtils.sc(session, sc);
			}
			return;
		}
//		if (weekday == 1 || weekday == 7) {
//			roleChange.setJudge(false);
//			roleChange.setChange(false);
//		} else {
			if (panduanTime() == 0) {
				roleChange.setJudge(true);
				GeneratedMessage sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.START))
						.build();
				for (IoSession session : SessionCache.getAllSession()) {
					SessionUtils.sc(session, sc);
				}
			} else {
				roleChange.setJudge(false);
				GeneratedMessage sc = null;
				if (panduanTime() == 1) {
					sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.FREE_TIME)).build();
					for (IoSession session : SessionCache.getAllSession()) {
						SessionUtils.sc(session, sc);
					}
				} else if (panduanTime() == -1) {
					sc = SC.newBuilder().setSCFight(SCFight.newBuilder().setNotice(Constant.CLOSE)).build();
					for (IoSession session : SessionCache.getAllSession()) {
						SessionUtils.sc(session, sc);
					}
				}
			}
		}

	/**
	 * 时间状态判断(0为开市，1为休市，-1为闭市)
	 * 
	 * @return
	 */
	private int panduanTime() {
		int weekday = DateUtils.getWeekday();
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
		if (time >= mtime && time < meTime) {// 大于等于上午开市时间并且小于上午闭市时间
			System.out.println("上午开市");
			return 0;
		} else if (time >= meTime && time < atime) {// 大于等于上午闭市时间并且小于下午开市时间
			System.out.println("休市");
			return 1;
		} else if (time >= atime && time < aetime) {// 大于等于下午开市时间并且小于下午闭市时间
			System.out.println("下午开市");
			return 0;
		} else {// 下午闭市时间之后
			System.out.println("闭市 ");
			return -1;
		}
	}
}
