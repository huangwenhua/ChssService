package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>标题: DateUtil.java</p>
 * <p>业务描述:时间工具类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月21日
 * @version V1.0 
 */
public class DateUtil {
	
	/**
	* 方法名:  now<br>
	* 方法功能描述: 获得当前时间
	* @return String
	* @Author:  张飞
	* @Create Date:  2014年4月21日 下午2:30:12
	 */
	public static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String nowDT15(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss"); 
		return sdf.format(new Date());
	}
	
	
}
