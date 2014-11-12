package util;

import java.text.SimpleDateFormat;
import java.util.Date;

import coon.JdbcUtil;

/**
 * <p>标题: ErrorUtil.java</p>
 * <p>业务描述:错误处理</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月18日
 * @version V1.0 
 */
public class ErrorUtil {

	public static void saveFault(String xml, String key, String tbName) {
		String sql = "insert into t_errordetail (xml,keyword,tbName,date) values('" + xml + "','" + key + "','" + tbName + "','" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "')";
		JdbcUtil.execute(sql);
	}
	
	public static void saveFault(String tbName) {
		Date date = new Date();
		String format = new SimpleDateFormat("yyyyMMdd").format(date);
		String sql = "if exists (select 1 from t_error where tbName='" + tbName + "' and date='" + format + "') update t_error set count=count+1 where tbName='" + tbName + "' and date='" + format + "'" + " else if not exists(select 1 from t_error where tbName='" + tbName + "' and date='" + format  + "') insert into t_error(tbname,date) values('" + tbName + "','" + format + "')";
		JdbcUtil.execute(sql);
	}

	
	public static void saveSucc(String tbName) {
		Date date = new Date();
		String format = new SimpleDateFormat("yyyyMMdd").format(date);
		String sql = "if exists (select 1 from t_succ where tbName='" + tbName + "' and date='" + format + "') update t_succ set count=count+1 where tbName='" + tbName + "' and date='" + format + "'" + " else if not exists(select 1 from t_succ where tbName='" + tbName + "' and date='" + format  + "') insert into t_succ(tbname,date) values('" + tbName + "','" + format + "')";
		JdbcUtil.execute(sql);
	}

	public static void delete(String keyword) {
		String sql = "delete from t_errordetail where keyword = '" + keyword + "'";
		JdbcUtil.execute(sql);
	}
}
