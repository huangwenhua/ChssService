package quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import util.EhcacheUtil;

/**
 * <p>标题: MainEntrance.java</p>
 * <p>业务描述:主方法入口</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月16日
 * @version V1.0 
 */
public class MainEntrance {

	private static Logger logger = Logger.getLogger(InvorkJob.class);
	/**
	 * 是否处理增量数据 处理 = true ，false = 处理历史数据
	 */
	private boolean isIncrement = true;
	
	public void run() throws Exception {
		// 通过SchedulerFactory获取一个调度器实例
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobDetail job = null;
		CronTrigger trigger = null;
		if (isIncrement) {
			job = JobBuilder.newJob(InvorkJob.class).withIdentity("job1", "group1").build();
			job.getJobDataMap().put("startTime", null);
			job.getJobDataMap().put("endTime", null);
			trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0 0 23 * * ?")).build();
			sched.scheduleJob(job, trigger);
		} else {
			/**
			for (int i = 2010; i < 2011; i++) {
				job = JobBuilder.newJob(InvorkJob.class).withIdentity("job" + i, "group" + i).build();
				job.getJobDataMap().put("startTime", i + "-01-01 00:00:00");
				job.getJobDataMap().put("endTime", i + "-12-24 00:00:00");
				trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + i, "group" + i).withSchedule(CronScheduleBuilder.cronSchedule("0 14 8 18 06 ? 2014")).build();
				sched.scheduleJob(job, trigger);
			}		
			**/	
			
			job = JobBuilder.newJob(InvorkJob.class).withIdentity("job2", "group2").build();
			job.getJobDataMap().put("startTime", "2009-01-01 00:00:00");
			job.getJobDataMap().put("endTime",  "2013-12-31 23:59:59");
			trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2").withSchedule(CronScheduleBuilder.cronSchedule("0 17 08 20 05 ? 2014")).build();
			sched.scheduleJob(job, trigger);
		}
		// 启动调度器
		logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "start server......");
		sched.start();
		// 调度器停止运行
		// sched.shutdown(true);
		// logger.error("结束运行。。。。");

	}  
  
    public static void main(String[] args) throws Exception {
    	EhcacheUtil.getInstance().initCache(); // 初始化ehcache缓存
    	MainEntrance me = new MainEntrance();
    	//me.isIncrement = false;
    	me.run(); 
    }  
}
