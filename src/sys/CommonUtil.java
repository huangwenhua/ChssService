package sys;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 标题: CommonUtil.java
 * </p>
 * <p>
 * 业务描述:宁波妇幼公共类
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2013
 * </p>
 * 
 * @author 金铭
 * @date 2014年4月16日
 * @version V1.0
 */
public class CommonUtil {

	/**
	 * 
	 * 方法名: isNull 方法功能描述: 判断对象是否为null，如果为null，返回空值
	 * 
	 * @param: 是包含汉字的字符串
	 * @return: 其他非简体汉字返回 '0';
	 * @Author: 金铭
	 * @Create Date: 2014年4月16日 下午2:36:53
	 */
	public static String isNull(String obj) {
		if (obj == null) {
			return "";
		}
		return obj;
	}

	/**
	 * 
	 * 方法名: getDate 方法功能描述: 返回日期格式
	 * 
	 * @param: 日期，格式（例yyyyMMdd）
	 * @return: 其他非简体汉字返回 '0';
	 * @Author: 金铭
	 * @Create Date: 2014年4月16日 下午2:36:53
	 */
	public static String getDate(Date date, String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		String dateString = "";
		if (!"".equals(date) && date != null) {
			dateString = f.format(date);
		}
		return dateString;
	}

	/**
	 * 
	 * 方法名: getComplications 方法功能描述: /替换|符号，并且去掉最后符号
	 * 
	 * @param: 是包含汉字的字符串
	 * @return: 包含|符号的多选代码
	 * @Author: 金铭
	 * @Create Date: 2014年4月29日 上午10:21:12
	 */
	public static String getComplications(String str) {
		String result = "";
		if (str != null && "".equals(str)) {
			if (str.contains("/")) {
				result = str.replace("/", "|");
				if (result.charAt(result.length() - 1) == '|') {
					result = result.substring(0, result.length() - 1);
				}
			}
		}
		return result;
	}

	public static int getLength(String str) {
		int len = 0;
		if (str != null && "".equals(str)) {
			try {
				String iso = new String(str.getBytes("GB2312"), "ISO8859-1");
				len = iso.length();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return len;
	}

	public static String substringZh(String str, int len) {
		String ss = "";
		if (str != null && "".equals(str)) {
			try {
				String iso = new String(str.getBytes("GB2312"), "ISO8859-1");
				if (iso.length() > len) {
					ss = new String(
							iso.substring(0, len).getBytes("ISO8859-1"),
							"gb2312");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ss;
	}
	
	public static Integer toInteger(String str){
		if (str != null && "".equals(str)) {
			float f = Float.parseFloat(str);
			return (int)f;
		}else {
			return 0;
		}
	}
}