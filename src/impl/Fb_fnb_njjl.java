package impl;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import sys.CommonPara;
import sys.Content;
import util.DateUtil;
import util.UnifieUtil;
import coon.JdbcPool;
import dictall.DictMap;
import Interface.BuildingXml;

/**
 * <p>标题: Fb_fnb_njjl.java</p>
 * <p>业务描述:普查记录</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author huangwh
 * @date 2014年4月16日
 * @version V1.0 
 */

public class Fb_fnb_njjl implements BuildingXml {

	private static Log logger = LogFactory.getLog(Fb_fnb_njjl.class);

	private String tbName;
	private static Map<String, Content> mapCon_PERSON_INFOR = new HashMap<String, Content>();
	private static Map<String, Content> mapCon_Women_Census = new HashMap<String, Content>();

	static {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(CommonPara.FB_FNB_NJJL_CONTENT));
			Element node = document.getRootElement();
			Iterator<Element> ite = node.elementIterator();
			while (ite.hasNext()) {
				Element secE = ite.next();
				if(secE.getName().equals("PERSON_INFOR")){
					Iterator<Element> it = secE.elementIterator();
					while (it.hasNext()) {
						Content con = new Content();
						Element e = it.next();
						String name = e.getName();
						
						String idname = "";
						String fun = "";
	
						List<Attribute> list = e.attributes();
						for (Attribute attr : list) {
							if (attr.getName().equals("id")) {
								idname = attr.getValue();
							}
							if (attr.getName().equals("fun")) {
								fun = attr.getValue();
							}
						}
						con.setName(name);
						con.setId(idname);
						con.setFun(fun);
						mapCon_PERSON_INFOR.put(name, con);
					}
				}
				else if(secE.getName().equals("Women_Census")){
					Iterator<Element> it = secE.elementIterator();
					while (it.hasNext()) {
						Content con = new Content();
						Element e = it.next();
						String name = e.getName();
						
						String idname = "";
						String fun = "";
	
						List<Attribute> list = e.attributes();
						for (Attribute attr : list) {
							if (attr.getName().equals("id")) {
								idname = attr.getValue();
							}
							if (attr.getName().equals("fun")) {
								fun = attr.getValue();
							}
						}
						con.setName(name);
						con.setId(idname);
						con.setFun(fun);
						mapCon_Women_Census.put(name, con);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public Fb_fnb_njjl() {
		tbName = "FB_FNB_NJJL";
	}

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {
		Connection con = JdbcPool.getConnection();
		String sql = "select * from " + tbName + " where jlzt != 9 AND XGRQ > ? AND XGRQ <= ?";
		//String sql = "select top 1 * from " + tbName ;
		PreparedStatement pspm = null;
		ResultSet rs = null;
		Map<String, String> xmlMap = new HashMap<String, String>();
		String xml = null;
		Map<String,String> localmap = null;
		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				localmap = new HashMap<String,String>();
				UnifieUtil.perInfo(rs.getString("GRBJH"),localmap);
				ResultSetMetaData rsmd = rs.getMetaData();   
			    int columnCount = rsmd.getColumnCount();   
			    for (int i=1; i<=columnCount; i++){   
			    	String rsmdname = rsmd.getColumnName(i);
			    	String rsmdvalue = rs.getString(i);
			        //System.out.println(rsmdname + ":" + rsmdvalue);   
			        localmap.put(rsmdname, rsmdvalue);
			    }    
			    try {
					xml = rs2str(localmap);
				} catch (MalformedURLException e) {
					logger.error("createxml Fb_fnb_njjl error:[" + rs.getString("LSH") + "] " + e.getMessage());
				} catch (DocumentException e) {
					logger.error("createxml Fb_fnb_njjl error2:[" + rs.getString("LSH") + "] " + e.getMessage());
				}
			    logger.debug(xml);
		        xmlMap.put(rs.getString("LSH"), xml);
			}
		} catch (SQLException e) {
			logger.error("fail to connect db：" + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
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

		}

		return xmlMap;
	}
	
	private String rs2str(Map<String,String> localmap) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.FB_FNB_NJJL_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();
        Element PERSON_INFOR = trans_noChild.addElement( "PERSON_INFOR" );
        Element Women_Census = trans_noChild.addElement( "Women_Census" );
	
        for (String key : mapCon_PERSON_INFOR.keySet()) {
        	String name = key;
        	String idname = mapCon_PERSON_INFOR.get(key).getId();
        	String fun = mapCon_PERSON_INFOR.get(key).getFun();
        	
        	Element elem = PERSON_INFOR.addElement( name );
        	String text = idname.equals("")?"":localmap.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "D8":
            	text = UnifieUtil.convertDateToD8(text);
            	break;
            case "married":
            	text = UnifieUtil.married(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }
        for (String key : mapCon_Women_Census.keySet()) {
        	String name = key;
        	String idname = mapCon_Women_Census.get(key).getId();
        	String fun = mapCon_Women_Census.get(key).getFun();
        	
        	Element elem = Women_Census.addElement( name );
        	String text = idname.equals("")?"":localmap.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "D8":
            	text = UnifieUtil.convertDateToD8(text);
            	break;
            case "tj":
            	text = UnifieUtil.tj(text);
            	break;
            case "ry":
            	text = UnifieUtil.ry(text);
            	break;
            case "jg":
            	text = UnifieUtil.jg(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }
        
        return doc.getRootElement().asXML();
		
	}
	
	public static void main(String[] args) {
		Fb_fnb_njjl fb = new Fb_fnb_njjl();
//		for (Entry<String, Content> entry : mapCon_Women_Census.entrySet()) {
//			String key = entry.getKey();
//			// String value = entry.getValue();
//			//System.out.println(key);
//			System.out.println(((Content)entry.getValue()).getName());
//		}
		fb.createxml("2013-01-01", "2013-01-05");
	}

}
