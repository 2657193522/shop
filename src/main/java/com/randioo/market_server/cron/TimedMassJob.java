package com.randioo.market_server.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.randioo.market_server.scheduler.TimerScheduler;
import com.randioo.randioo_server_base.utils.SpringContext;

/**
 * 功能:定时群发工作任务
 * 
 * @author wss
 * @date 2017-07-22
 */
public class TimedMassJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		TimerScheduler s = ((TimerScheduler) SpringContext.getBean("timerScheduler"));
		s.deleteMessage();
		s.deleteCache();
	}
}