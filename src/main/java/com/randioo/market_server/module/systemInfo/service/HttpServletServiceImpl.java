package com.randioo.market_server.module.systemInfo.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.dao.RoleDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.protocol.Gm.SCFight;
import com.randioo.market_server.protocol.Gm.SCNumberNotice;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;

@Service("httpServletService")
public class HttpServletServiceImpl extends ObserveBaseService implements HttpServletService {
	@Autowired
	private Role roleChange;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumComponent;

	@Override
	public void repair(int lockString, int time) {
		// TODO Auto-generated method stub
		System.out.println(lockString + "dddddddd");
		if ("1".equals(String.valueOf(lockString))) {
			SC sc = SC.newBuilder()
					.setSCFight(SCFight.newBuilder().setNotice(Constant.REPQIR_TIME).setTime(String.valueOf(time)))
					.build();
			roleChange.setJudge(false);
			for (IoSession session : SessionCache.getAllSession()) {
				SessionUtils.sc(session, sc);
			}
			timer(time);
		} else {
			roleChange.setJudge(true);
			SC sc = SC.newBuilder()
					.setSCFight(SCFight.newBuilder().setNotice(Constant.START).setTime(String.valueOf(time))).build();
			for (IoSession session : SessionCache.getAllSession()) {
				SessionUtils.sc(session, sc);
			}
		}
	}

	public void timer(final int time) {

		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		EntityRunnable<ScheduledExecutorService> runnable = new EntityRunnable<ScheduledExecutorService>(service) {

			@Override
			public void run(ScheduledExecutorService entity) {
				roleChange.setJudge(true);
				SC sc = SC.newBuilder()
						.setSCFight(SCFight.newBuilder().setNotice(Constant.START).setTime(String.valueOf(time)))
						.build();
				for (IoSession session : SessionCache.getAllSession()) {
					SessionUtils.sc(session, sc);
				}
				entity.shutdown();
			}
		};
		service.schedule(runnable, time, TimeUnit.HOURS);
	}

	public static void main(String[] args) {
		// ScheduledExecutorService service =
		// Executors.newSingleThreadScheduledExecutor();
		// service.schedule(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// System.out.println("h");
		// }
		// }, 1, TimeUnit.SECONDS);
		// service.schedule(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// System.out.println("22");
		// }
		// }, 3, TimeUnit.SECONDS);

	}
	@Override
	public void scrmb(String account,String type) {
		// TODO Auto-generated method stub
		Role role=roleDAO.getRole(account);
		if(role==null){
			return;
		}
		NumbersBO number=sellerLogicNumComponent.getNumbers(account, type);
		if(number==null){
			return;
		}
		SC sc=SC.newBuilder().setSCNumberNotice(SCNumberNotice.newBuilder().setAccount(account).setRmbA(String.format("%.2f", role.getRole_rmbA())).setCount(number.getNum_count())).build();
		SessionUtils.sc(role.getRoleId(), sc);
	}
}
