package cszm.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import Client.Result;
import coon.JdbcPool;
import coon.JdbcUtil;
import cszm.service.Ser;

public class Client {
	
	private final static String DONE = "<?xml version='1.0' standalone='yes'?><NewDataSet></NewDataSet>";
	
	public void doInfo(){
		down();
		upload("", "");
	}

	public void down() {
		logger.info("download xml start...");
		String downXml = Ser.getInfo();
		if (downXml == null || downXml.equals("-1") ) {
			logger.error("error to download cszm");
			return;
		}
		while ( downXml != null && !downXml.equals(DONE) ) {
			String cszh = "";
			Map<String, String> map = paser(downXml);
			Set<String> key = map.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String k = (String) it.next();
				if (cszh.equals(""))
					cszh = k;
				else
					cszh = cszh + "," + k;
				String cszm = map.get(k);
				String sql = "INSERT INTO T_CSZM VALUES('" + k + "','" + cszm + "','" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "','0')";
				JdbcUtil.execute(sql);
			}
			logger.info("down cszh:" + cszh);
			
			String ret = Ser.setInfo(cszh);
			if (ret != null && ret.equals("s_ok")){
				logger.info("check ok:" + cszh);
			}
			else{
				logger.error("check error:" + cszh);
			}
				
			downXml = Ser.getInfo();
		}
		logger.info("download xml done...");
	}
	
	public void upload(String startTime,String endTime){
		logger.info("upload xml start...");
		Map<String, String> map = createxml(startTime, endTime);
		String key = "";
		String value = "";
		Result result = new Result();
		for (Entry<String, String> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			result.doResult(value, "T_CSZM", key);
			logger.debug("doIncrement [ key =" + key + " ], tbName = [ " + "T_CSZM" + " ]");
			
			String update = "UPDATE T_CSZM SET ZT='9' WHERE CSBH='" + key + "'" ;
			JdbcUtil.execute(update);
		}
		logger.info("upload xml done...");
	}
	
	private Map<String, String> createxml(String startTime, String endTime) {
		logger.info("createxml cszm xml start...");
		Map<String, String> map = new HashMap<String, String>();
		Connection con = JdbcPool.getConnection();
		//String sql = "select  *  from T_CSZM  where date>? and date<=? and zt='0'";
		String sql = "select top 9759 * from T_CSZM  where zt='0'";
		PreparedStatement pspm = null;
		ResultSet rs = null;
		
		try {
			pspm = con.prepareStatement(sql);
			//pspm.setString(1, startTime);
			//pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				String csbh = rs.getString("CSBH");
				String cszmXml = rs.getString("CSZM");
				try {
					map.put(csbh, Transfer.paser(csbh, cszmXml));
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("createxml cszm error:[" + csbh + "] " + e.getMessage());
				}
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("createxml all cszm error:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			try {
				if (pspm != null) {
					pspm.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		}
		logger.info("createxml cszm xml end...");
		return map;
	}

	private Map<String, String> paser(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document docment = null;
		try {
			docment = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = docment.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("Table"); // 获取根节点下的子节点
			// 遍历response节点
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				String csbh = recordEle.elementTextTrim("CSBH");
				map.put(csbh, recordEle.asXML());
			}
		} catch (Exception e) {
			logger.error("parse xml is error :[" + xml + "]" +  e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

//	private String test() {
//		// 创建saxReader对象
//		SAXReader reader = new SAXReader();
//		// 通过read方法读取一个文件 转换成Document对象
//		Document document;
//		try {
//			document = reader.read(new File("src/xml.xml"));
//			// 获取根节点元素对象
//			Element node = document.getRootElement();
//			// 当前节点下面子节点迭代器
//			return node.asXML();
//		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
//		} catch (DocumentException e1) {
//			e1.printStackTrace();
//		}
//		return null;
//	}

//	private void test(){
//		JK_XZClient jk = new JK_XZClient();
//		System.out.println("1");
//		String a = jk.getJK_XZSoap("http://csz.zjwst.gov.cn:8574/WebSer/JK_XZ.asmx").getInfo("<DATAPACKET><ROWDATA><row s_uid='Vhm1IV/uhLwlfjtnke/rfQ==' s_code='Vhm1IV/uhLwlfjtnke/rfQ==' /></ROWDATA></DATAPACKET>");
//		String b = DEncrypt.getDncrypt(a);
//		System.out.println(b);
//		String dd="";
//		String c="";
//		try {
//			 dd=java.net.URLDecoder.decode(b,"utf-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}  
//		System.out.println(dd);
//	}
//	
	public static void main(String[] args) {
//		EhcacheUtil.getInstance().initCache(); // 初始化ehcache缓存
		
		Client client = new Client();
		
		int i = 0 ;
		while(true){
			i++;
			System.out.println(i);
			try{
				//client.down();
				client.upload("", "");
			}catch (Exception e) {
				logger.error("getInfo error:" + e.getMessage());
				e.printStackTrace();
			} 
		}	
	}

	private static Logger logger = Logger.getLogger(Client.class);
}
