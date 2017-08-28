package com.randioo.market_server.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.randioo.market_server.cache.file.TimingConfigCache;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.market_server.entity.file.TimingConfig;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Component
public class DateUtils {
	/**
	 * 获取时间戳(String)
	 * 
	 * @param time
	 * @return
	 */
	public static String getTime(String time) {
		long t = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(time);
			t = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(t);
	}

	/**
	 * 获取时间戳(Long)
	 * 
	 * @param time
	 * @return
	 */
	public static Long getLongTime(String time) {
		long t = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(time);
			t = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 控制小数位
	 * 
	 * @param count
	 * @return
	 */
	public static double changeSum(int count, double price) {
		String s = String.format("%.2f", count * price);
		double a = Double.parseDouble(s);
		return a;
	}

	/**
	 * 取价格最后一位
	 * 
	 * @param price
	 * @return
	 */
	public static String getEnd(String price) {
		double x = Double.parseDouble(price);
		// double s = (x % 10);// 取余
		String a = String.format("%.2f", x % 10);
		String d = a.substring(a.length() - 1);
		return d;
	}

	/**
	 * 判断本周
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isThisWeek(long time) {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(new Date(time));
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (paramWeek == currentWeek) {
			return true;
		}
		return false;
	}

	/**
	 * 判断时间是否是今天p
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(String time) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = fmt.parse(time);
			if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) // 格式化为相同格式
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 获取5分钟整的时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static String getNumTime(String time) {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = (now.get(Calendar.MONTH) + 1) + "";
		String day = now.get(Calendar.DAY_OF_MONTH) + "";
		String hour = now.get(Calendar.HOUR_OF_DAY) + "";
		String min = now.get(Calendar.MINUTE) + "";
		// String second = now.get(Calendar.SECOND) + "";
		int mins = Integer.parseInt(min);
		int minss = Integer.parseInt(min.substring(min.length() - 1, min.length()));
		int mi = 0;
		if (minss > 5) {
			mi = minss - 5;
			mins = mins - mi;
		} else if (minss < 5) {
			mins = mins - minss;
		}
		String m = String.valueOf(mins);
		time = year + "-" + month + "-" + day + " " + hour + ":" + m + ":" + "00";
		// return String.valueOf(DateUtils.getLongTime(time));
		return time;
	}

	/**
	 * 获取休市之后开市时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getStartTime(String time) {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = (now.get(Calendar.MONTH) + 1) + "";
		String day = now.get(Calendar.DAY_OF_MONTH) + "";
		String hour = now.get(Calendar.HOUR_OF_DAY) + "";
		String min = now.get(Calendar.MINUTE) + "";
		String second = now.get(Calendar.SECOND) + "";
		// int mins = Integer.parseInt(min);
		// int minss = Integer.parseInt(min.substring(min.length() - 1,
		// min.length()));
		// int mi = 0;
		// if (minss > 5) {
		// mi = minss - 5;
		// mins = mins - mi;
		// } else if (minss < 5) {
		// mins = mins - minss;
		// }

		// String m = String.valueOf(mins);
		hour = String.valueOf(Integer.parseInt(hour) + Integer.parseInt(time));
		time = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + second;
		// return String.valueOf(DateUtils.getLongTime(time));
		return time;
	}

	/**
	 * 获取分钟最后一位
	 * 
	 * @param time
	 * @return
	 */
	public static String getMinTime(String time) {
		Calendar now = Calendar.getInstance();
		String min = now.get(Calendar.MINUTE) + "";
		String minss = min.substring(min.length() - 1, min.length());
		return minss;
	}

	/**
	 * 四舍五入
	 * 
	 * @param f
	 * @return
	 */
	public static String getDouble(double f) {
		String s = DateUtils.getEnd(String.valueOf(f));
		if (!s.equals("5")) {
			if (!s.equals("0")) {
				if (Integer.parseInt(s) > 5) {
					BigDecimal b = new BigDecimal(f);
					s = String.format("%.2f", b.setScale(1, BigDecimal.ROUND_HALF_DOWN));
					System.out.println(s);
				} else {
					BigDecimal b = new BigDecimal(f);
					s = String.format("%.2f", b.setScale(1, BigDecimal.ROUND_DOWN));
					System.out.println(s);
				}
			} else {
				s = String.valueOf(f);
			}
		} else {
			s = String.valueOf(f);
		}
		return s;
	}

	/**
	 * 判断中间时间段缺失
	 * 
	 * @param time
	 * @param lastTime
	 * @return
	 */
	public static int getBetweenTime(String time, String lastTime) {
		int weekday = getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekday);
		int i = 0;
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
		Date lastTimes = null;
		try {
			nowTime = sdf.parse(time);
			mStartTime = sdf.parse(mStartTimeStr);
			mEndTime = sdf.parse(mEndTimeStr);
			aStartTime = sdf.parse(aStartTimeStr);
			aEndTime = sdf.parse(aEndTimeStr);
			lastTimes = sdf.parse(lastTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (nowTime.after(mStartTime) && nowTime.before(mEndTime)) {
			i = (int) ((nowTime.getTime() - (lastTimes.getTime())) / 1000 / 300);
		}
		if (nowTime.after(aStartTime) && nowTime.before(aEndTime)) {
			i = (int) ((nowTime.getTime() - (lastTimes.getTime())) / 1000 / 300);
		}
		return i;
	}

	/**
	 * 得到星期几
	 * 
	 * @return
	 */
	public static int getWeekday() {
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		return weekday;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param price
	 * @return
	 */
	public static double getTwoDouble(double price) {
		String s = String.format("%.2f", price);
		double b = Double.parseDouble(s);
		return b;
	}

	/**
	 * 获取涨幅率
	 * 
	 * @return
	 */
	public static int getPercentage(double startPrice, double endPrice) {
		int percentage = 0;
		if (endPrice > startPrice) {
			percentage = 1;
		} else if (endPrice < startPrice) {
			percentage = -1;
		} else {
			percentage = 0;
		}
		return percentage;
	}

	/**
	 * 根据时间类型判断之前几次没有数据
	 * 
	 * @param time
	 * @param type
	 * @return
	 */
	public static int getKTime(String time, String type) {
		int weekday = DateUtils.getWeekday();
		TimingConfig timing = TimingConfigCache.getConfigById(weekday);
		int i = 0;
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = ca.getTime();
		String nowDateStr = sdf.format(nowDate);
		String ymd = nowDateStr.split(" ")[0];
		String mStartTimeStr = ymd + " " + timing.mStartTime;
		String mEndTimeStr = ymd + " " + timing.mEndTime;
		String aStartTimeStr = ymd + " " + timing.aStartTime;

		Date nowTime = null;
		Date mStartTime = null;
		Date mEndTime = null;
		Date aStartTime = null;
		// Date aEndTime = null;
		try {
			nowTime = sdf.parse(time);
			mStartTime = sdf.parse(mStartTimeStr);
			mEndTime = sdf.parse(mEndTimeStr);
			aStartTime = sdf.parse(aStartTimeStr);
			// aEndTime = sdf.parse(aEndTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (panduanTime() == 1) {// 上午开市
			i = (int) ((nowTime.getTime() - (mStartTime.getTime())) / 1000 / 300);
		}
		if (panduanTime() == 3) {// 下午开市
			i = (int) ((nowTime.getTime() - (aStartTime.getTime())) / 1000 / 300) + 24 + 24;
		}
		if (panduanTime() == -1) {// 闭市之后
			i = (int) ((nowTime.getTime() - (aStartTime.getTime())) / 1000 / 300) + 24 + 24;

		}
		if (panduanTime() == 2) {// 休市
			i = (int) ((nowTime.getTime() - (mEndTime.getTime())) / 1000 / 300);
			i = 24 + i;
		}
		return i;
	}

	public static int panduanTime() {
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
		if (time >= mtime && time < meTime) {
			return 1;
		} else if (time >= meTime && time <= atime) {
			return 2;
		} else if (time > atime && time <= aetime) {
			return 3;
		} else if (time > aetime) {
			return -1;
		} else {
			return 0;
		}
	}

	public static long getNowTime(long time) {
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

	public static String getRounding() {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = (now.get(Calendar.MONTH) + 1) + "";
		String day = now.get(Calendar.DAY_OF_MONTH) + "";
		String hour = now.get(Calendar.HOUR_OF_DAY) + "";
		String min = now.get(Calendar.MINUTE) + "";
		String second = now.get(Calendar.SECOND) + "";
		int miao = Integer.parseInt(second);
		int fen = Integer.parseInt(min);
		int shi = Integer.parseInt(hour);
		if (miao != 0) {
			fen = fen + 1;
			if (fen < 10) {
				min = "0" + fen;
			} else {
				min = String.valueOf(fen);
			}
			if (fen == 60) {
				min = "00";
				shi = shi + 1;
				hour = String.valueOf(shi);
			}
		}
		second = "00";
		String time = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + second;
		return time;
	}

	/**
	 * 分页查询方法
	 * 
	 * @param startNum
	 * @param count
	 * @param sellerBOs
	 * @return
	 */
	public static List<SellerBO> getSeller(int startNum, int count, List<SellerBO> sellerBOs) {
		List<SellerBO> list = new ArrayList<>();
		int num = 0;
		for (int i = startNum; i < sellerBOs.size() - 1; i++) {
			num++;
			if (num > count) {
				break;
			}
			list.add(sellerBOs.get(startNum));
		}
		return list;
	}

	/**
	 * 分页查询方法
	 * 
	 * @param startNum
	 * @param count
	 * @param sellerBOs
	 * @return
	 */
	public static List<TradingBO> getTrading(int startNum, int count, List<TradingBO> tradingBOs) {
		List<TradingBO> list = new ArrayList<>();
		int num = 0;
		for (int i = startNum; i < tradingBOs.size() - 1; i++) {
			num++;
			if (num > count) {
				break;
			}
			list.add(tradingBOs.get(i));
		}
		return list;
	}

}
