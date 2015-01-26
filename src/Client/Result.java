package Client;

import java.util.Map;

import org.apache.log4j.Logger;

import util.ErrorUtil;

/**
 * <p>标题: Result.java</p>
 * <p>业务描述:处理返回的结果</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月15日
 * @version V1.0 
 */
public class Result {

	private static Logger logger = Logger.getLogger(Result.class);

	public void doResult(Map<String, String> map, String tbName, String key) {
		String retCode = map.get("retCode");
		if ("0".equals(retCode)) {
			logger.info("表名：" + tbName + ",主键：" + key + "处理成功！");
		} else {
			logger.error("表名：" + tbName + ",主键：" + key + "处理失败！" + "失败信息："
					+ map.get("retInfo"));
		}
	}
	
	/**
	* 方法名:  doResult
	* 方法功能描述: 执行并处理结果
	* @param xml 
	* @param tbName 表名
	* @param key 主键
	* @Author:  张飞
	* @Create Date:  2014年4月16日 下午2:42:56
	 */
	public void doResult(String xml, String tbName, String key) {
		String retxml = WebServiceUtil.invork(xml,tbName,key);
		if (null == retxml)
			return;
		Map<String, String> map = ParseXmlUtil.parsexml(retxml);
		String retCode = map.get("retCode");
		if ("0".equals(retCode)) {
			ErrorUtil.saveSucc(tbName);
			logger.info("表名：" + tbName + ",主键：" + key + "处理成功！");
		} else {
			ErrorUtil.saveFault(xml, key, tbName);
			ErrorUtil.saveFault(tbName);
			logger.error("表名：" + tbName + ",主键：" + key + ",处理失败！" + "失败信息：" + map.get("retInfo"));
		}
	}
	
	public void doResultBill(String xml, String tbName, String key) {
		logger.info("bil xml:" + xml);
		String retxml = WebServiceUtil.invorkBill(xml,tbName,key);
		if (null == retxml)
			return;
		logger.info("bill return xml:" + retxml);
		Map<String, String> map = ParseXmlUtil.parsexml(retxml);
		String retCode = map.get("retCode");
		if ("0".equals(retCode)) {
			ErrorUtil.saveSucc(tbName);
			logger.info("表名：" + tbName + ",主键：" + key + "处理成功！");
		} else {
			ErrorUtil.saveFault(xml, key, tbName);
			ErrorUtil.saveFault(tbName);
			logger.error("表名：" + tbName + ",主键：" + key + ",处理失败！" + "失败信息：" + map.get("retInfo"));
		}
	}
	

	
}
