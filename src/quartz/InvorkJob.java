package quartz;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;




import cszm.client.Client;
import Client.Entrance;

/**
 * <p>标题: InvorkJob.java</p>
 * <p>业务描述:定时执行的任务</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月16日
 * @version V1.0 
 */
public class InvorkJob implements Job {

	private static Logger logger = Logger.getLogger(InvorkJob.class);
	
	private Entrance entran = null;
	public InvorkJob() {

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " InvorkJob start .....");
		Object startTime = arg0.getJobDetail().getJobDataMap().get("startTime");
		Object endTime = arg0.getJobDetail().getJobDataMap().get("endTime");
		if (null == startTime && null == endTime) { // 增量数据
			Date date = getBeforeDay();
			startTime = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";
			//endTime = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
			endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
			
			logger.info("==================startTime = " + startTime + ", endTime = " + endTime + "====================");
			entran = new Entrance(startTime.toString(), endTime.toString());
			entran.doInfo();
			logger.info("==================upload end====================");
			
			Client client = new Client();
			client.doInfo();
			
		}else{
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	java.util.Date startDD = null;
	    	java.util.Date endDD = null;
			try {
				startDD = sdf.parse((String)startTime);
				endDD = sdf.parse((String)endTime);  
				
		        Calendar cal = Calendar.getInstance();   
		        while(startDD.before(endDD)){
		            cal.setTime(startDD);   
		            String _startTime = sdf.format(cal.getTime());
		            
		            cal.add(Calendar.MONTH, 1); 
		            startDD = cal.getTime();
		            String _endTime;
		            if(startDD.before(endDD))
		            	_endTime = sdf.format(startDD);
		            else 
		            	_endTime = sdf.format(endDD);
		            
		            logger.info("==================startTime = " + _startTime + ", endTime = " + _endTime + "==========start==========");
					entran = new Entrance(_startTime, _endTime);
					entran.doInfo();
					logger.info("==================startTime = " + _startTime + ", endTime = " + _endTime + "==========end==========");
		        }
		        logger.info("-------------------------" + startDD + "******************至*****************" + endDD + "的数据处理完成--------------------------");
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("execute error:" + e.getMessage());
			}  
		}
	}
	
	private Date getBeforeDay() {
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		return dBefore;
	}
}
