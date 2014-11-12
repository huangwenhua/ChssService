package Interface;

import java.util.Map;

/**
 * <p>标题: BuildingXml.java</p>
 * <p>业务描述:将对应的数据组装成xml文件</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月15日
 * @version V1.0 
 */
public interface BuildingXml {
	/**
	 * 方法名: createxml 
	 * 方法功能描述: 将取到的数据封装成Xml
	 * @param startTime 开始时间
	 * @param endTime 介绍时间
	 * @return Map<String,String> key = 主键，value = 组装的xml
	 * @Author: 张飞
	 * @Create Date: 2014年4月15日 下午3:28:01
	 */
	Map<String, String> createxml(String startTime,String endTime);
}
