package com.randioo.market_server.module.sc.state;

import org.apache.mina.core.session.IoSession;

import com.randioo.market_server.cache.local.StateCache;
import com.randioo.market_server.cache.local.mink.FifMinkCache;
import com.randioo.market_server.cache.local.mink.MinKCache;
import com.randioo.market_server.cache.local.mink.OneMinkCache;
import com.randioo.market_server.cache.local.mink.SixtyMinkCache;
import com.randioo.market_server.cache.local.mink.ThirtyMinkCache;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.protocol.Entity.KData;
import com.randioo.market_server.protocol.K.SCRefushMink;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.DateUtils;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.utils.TimeUtils;

public class TrueStateImpl implements TrueState {

	@Override
	public void refushMessage(String type) {
		// TODO Auto-generated method stub
		SCRefushMink.Builder builders = SCRefushMink.newBuilder();

		if (StateCache.isPanduan()) {
			MinkBO minks = MinKCache.getMapByTime(type);
			if (minks != null) {
				builders.addKData(getMinkData(minks, 5));
			}
		}
		if (StateCache.isOnePanduan()) {
			MinkBO oneMink = OneMinkCache.getMapByTime(type);
			if (oneMink != null) {
				builders.addKData(getMinkData(oneMink, 1));
			}
		}
		if (StateCache.isFifPanduan()) {
			MinkBO fifMink = FifMinkCache.getMapByTime(type);
			if (fifMink != null) {
				builders.addKData(getMinkData(fifMink, 15));
			}
		}
		if (StateCache.isThirtyPanduan()) {
			MinkBO thirtyMink = ThirtyMinkCache.getMapByTime(type);
			if (thirtyMink != null) {
				builders.addKData(getMinkData(thirtyMink, 30));
			}
		}
		if (StateCache.isSixtyPanduan()) {
			MinkBO sixtyMink = SixtyMinkCache.getMapByTime(type);
			if (sixtyMink != null) {
				builders.addKData(getMinkData(sixtyMink, 60));
			}
		}
		SC sc = SC.newBuilder().setSCRefushMink(builders).build();
		for (IoSession session : SessionCache.getAllSession()) {
			SessionUtils.sc(session, sc);
		}
	}

	public KData getMinkData(MinkBO minks, int num) {
		int percentage = 0;
		if (minks.getMin_end_price() > minks.getMin_start_price()) {
			percentage = 1;
		} else if (minks.getMin_end_price() < minks.getMin_start_price()) {
			percentage = -1;
		} else {
			percentage = 0;
		}
		return KData.newBuilder().setMinPrice((int)(minks.getMin_min_price()*100))
				.setPercentage(percentage)
				.setStartPrice((int)(minks.getMin_start_price()*100))
				.setDayTime(DateUtils.getTime(TimeUtils.getDetailTimeStr()))
				.setMaxPrice((int)(minks.getMin_max_price()*100)).setNumber(num).setPanduan(StateCache.isPanduan())
				.setSumCount(minks.getMin_sum_count() * 2)
				.setSumPrice((int)(DateUtils.changeSum(2, minks.getMin_sum_price())*100)).build();
	}
}
