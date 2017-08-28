package com.randioo.market_server.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.scheduler.SchedulerInterface;

@Component
public class RoleDataScheduler implements SchedulerInterface {

	@Autowired
	private GameDB gameDB;

	private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);

	@Override
	public void start() {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 玩家信息定期保存
		scheduledPool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (!gameDB.isUpdatePoolClose()) {
					try {
						gameDB.getUpdatePool().submit(new Runnable() {
							@Override
							public void run() {
								try {
									// scService.scGoodsTy();
								} catch (Exception e) {
									Date date = new Date();
									System.out.println(format.format(date) + " clock save error");
									e.printStackTrace();
								}
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 1, 5, TimeUnit.MINUTES);
	}

	@Override
	public void shutdown(long timeout, TimeUnit unit) throws Exception {
		scheduledPool.shutdown();
		System.out.println("RoleDataScheduler wait shutdown");
		while (!scheduledPool.awaitTermination(timeout, unit)) {

		}
		System.out.println("RoleDataScheduler success shutdown");
	}

}
