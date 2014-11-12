package Client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <p>标题: ParseXmlUtil.java</p>
 * <p>业务描述:解析xml工具类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月15日
 * @version V1.0 
 */
public class ParseXmlUtil {
	
	private static Logger logger = Logger.getLogger(ParseXmlUtil.class);
	
	/**
	* 方法名:  parsexml
	* 方法功能描述: 解析返回的xml文件
	* @param xml
	* @Author:  张飞
	* @Create Date:  2014年4月15日 下午4:09:36
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> parsexml(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document docment = null;
		try {
			docment = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = docment.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("response"); // 获取根节点下的子节点response
			// 遍历response节点
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				String retCode = recordEle.elementTextTrim("ret_code"); // 拿到response节点下的子节点ret_code值
				map.put("retCode", retCode);
				String retInfo = recordEle.elementTextTrim("ret_info"); // 拿到response节点下的子节点ret_info值
				map.put("retInfo", retInfo);
			}
		} catch (Exception e) {
			logger.error("parse xml is error :" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
}
