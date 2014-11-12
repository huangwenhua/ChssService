package util;

import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @description 解析xml字符串
 */
public class XMLDocDoctor {

	private static Log logger = LogFactory.getLog(XMLDocDoctor.class);

	public String readStringXml(String xml) {
		Document doc = null;
		String ZXDM = "";
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("response"); // 获取根节点下的子节点response
			// 遍历response节点
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				String ret_code = recordEle.elementTextTrim("ret_code"); // 拿到response节点下的子节点ret_code值
				String ret_info = recordEle.elementTextTrim("ret_info"); // 拿到response节点下的子节点ret_info值
				String EMP_ID = recordEle.elementTextTrim("EMP_ID"); // 拿到response节点下的子节点ret_info值
				if ("0".equals(ret_code)) {
					ZXDM = EMP_ID;
				} else {
					logger.error("return error code:" + ret_code);
				}
			}

		} catch (DocumentException e) {
			logger.error("read xml error");
		} catch (Exception e) {
			logger.error("throw error");
		}
		return ZXDM;
	}

}