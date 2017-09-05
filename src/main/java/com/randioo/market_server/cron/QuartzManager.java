package com.randioo.market_server.cron;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.randioo.market_server.cache.file.TimeConfigCache;
import com.randioo.market_server.entity.file.TimeConfig;

/**
 * 功能:封装了 Quartz 2.2 动态添加、修改和删除定时任务时间的方法
 * 
 * @author dengyu
 * @date 2017-07-22
 */
public class QuartzManager {

	// private static SchedulerFactory schedulerFactory = new
	// StdSchedulerFactory();

	/**
	 * 功能： 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务的类类型 eg:TimedMassJob.class
	 * @param cron
	 *            时间设置 表达式，参考quartz说明文档
	 * @param objects
	 *            可变参数需要进行传参的值
	 */
	// public static void addJob(String jobName, String jobGroupName, String
	// triggerName, String triggerGroupName,
	// Class jobClass, String cron, Object... objects) {
	// try {
	// Scheduler scheduler = schedulerFactory.getScheduler();
	// // 任务名，任务组，任务执行类
	// JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName,
	// jobGroupName).build();
	// // 触发器
	// if (objects != null) {
	// for (int i = 0; i < objects.length; i++) {
	// // 该数据可以通过Job中的JobDataMap dataMap =
	// // context.getJobDetail().getJobDataMap();来进行参数传递值
	// jobDetail.getJobDataMap().put("data" + (i + 1), objects[i]);
	// }
	// }
	// TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	// // 触发器名,触发器组
	// triggerBuilder.withIdentity(triggerName, triggerGroupName);
	// triggerBuilder.startNow();
	// // 触发器时间设定
	// triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
	// // 创建Trigger对象
	// CronTrigger trigger = (CronTrigger) triggerBuilder.build();
	// // 调度容器设置JobDetail和Trigger
	// scheduler.scheduleJob(jobDetail, trigger);
	// // 启动
	// if (!scheduler.isShutdown()) {
	// scheduler.start();
	// }
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }
	//
	// /**
	// * 功能：修改一个任务的触发时间
	// *
	// * @param jobName
	// * @param jobGroupName
	// * @param triggerName
	// * 触发器名
	// * @param triggerGroupName
	// * 触发器组名
	// * @param cron
	// * 时间设置，参考quartz说明文档
	// */
	// public static void modifyJobTime(String jobName, String jobGroupName,
	// String triggerName, String triggerGroupName,
	// String cron) {
	// try {
	// Scheduler scheduler = schedulerFactory.getScheduler();
	// TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
	// triggerGroupName);
	// CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	// if (trigger == null) {
	// return;
	// }
	// String oldTime = trigger.getCronExpression();
	// if (!oldTime.equalsIgnoreCase(cron)) {
	// // 触发器
	// TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	// // 触发器名,触发器组
	// triggerBuilder.withIdentity(triggerName, triggerGroupName);
	// triggerBuilder.startNow();
	// // 触发器时间设定
	// triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
	// // 创建Trigger对象
	// trigger = (CronTrigger) triggerBuilder.build();
	// // 方式一 ：修改一个任务的触发时间
	// scheduler.rescheduleJob(triggerKey, trigger);
	// }
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }
	//
	// public static void openQuartz() {
	// SchedulerFactory schedulerfactory = new StdSchedulerFactory();
	// Scheduler scheduler = null;
	//
	// try {
	// scheduler = schedulerfactory.getScheduler();
	// JobDetail roleUpDateJob =
	// JobBuilder.newJob(TimedMassJob.class).withIdentity("timedMassJob",
	// "jgroup")
	// .build();
	// Trigger roleZeroTrigger =
	// TriggerBuilder.newTrigger().withIdentity("timedMassTimer",
	// "triggerGroup")
	// .withSchedule(CronScheduleBuilder.cronSchedule("0 22 12 * *
	// ?")).startNow().build();
	// scheduler.scheduleJob(roleUpDateJob, roleZeroTrigger);
	// scheduler.start();
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 功能: 移除一个任务
	// *
	// * @param jobName
	// * @param jobGroupName
	// * @param triggerName
	// * @param triggerGroupName
	// */
	// public static void removeJob(String jobName, String jobGroupName, String
	// triggerName, String triggerGroupName) {
	// try {
	// Scheduler scheduler = schedulerFactory.getScheduler();
	//
	// TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
	// triggerGroupName);
	// // 停止触发器
	// scheduler.pauseTrigger(triggerKey);
	// // 移除触发器
	// scheduler.unscheduleJob(triggerKey);
	// // 删除任务
	// scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }
	//
	// /**
	// *
	// * 功能：启动所有定时任务
	// */
	// public static void startJobs() {
	// try {
	// Scheduler scheduler = schedulerFactory.getScheduler();
	// scheduler.start();
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }
	//
	// /**
	// * 功能：关闭所有定时任务
	// */
	// public static void shutdownJobs() {
	// try {
	// Scheduler scheduler = schedulerFactory.getScheduler();
	// if (!scheduler.isShutdown()) {
	// scheduler.shutdown();
	// }
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	//
	// //
	// // public static Void job(){
	// // Scheduler scheduler = schedulerFactoryBean.getScheduler();
	// // TriggerKey triggerKey =
	// // TriggerKey.triggerKey(scheduleJob.getJobName(),
	// // scheduleJob.getJobGroup());
	// // //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
	// // CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	// // //表达式调度构建器
	// // CronScheduleBuilder scheduleBuilder =
	// // CronScheduleBuilder.cronSchedule(scheduleJob
	// // .getCronExpression());
	// // //按新的cronExpression表达式重新构建trigger
	// // trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
	// // .withSchedule(scheduleBuilder).build();
	// // //按新的trigger重新设置job执行
	// // scheduler.rescheduleJob(triggerKey, trigger);
	// // }
	// }
	//

	public static void openQuartz() {
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		TimeConfig timeConfig = TimeConfigCache.getConfig();
		System.out.println(timeConfig.seller+"1111111111111111111111111");
		try {
			scheduler = schedulerfactory.getScheduler();
			JobDetail roleUpDateJob = JobBuilder.newJob(TimedMassJob.class).withIdentity("timedMassJob", "jgroup")
					.build();
			Trigger roleZeroTrigger = TriggerBuilder.newTrigger().withIdentity("timedMassTimer", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule(timeConfig.seller)).startNow().build();
			scheduler.scheduleJob(roleUpDateJob, roleZeroTrigger);
			scheduler.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}