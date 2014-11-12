package impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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

import coon.JdbcPool;
import sys.CommonPara;
import sys.Content;
import util.DateUtil;
import util.UnifieUtil;
import Interface.BuildingXml;

public class Fb_fnb_rxaoffline implements BuildingXml {
	private static Log logger = LogFactory.getLog(Fb_fnb_rxaoffline.class);

	private String tbName;
	/**乳腺癌筛查基本信息**/
	private static Map<String, Content> mapCon_PERSON_INFOR = new HashMap<String, Content>();
	private static Map<String, Content> mapCon_PERSON_Screening = new HashMap<String, Content>();
	/**乳腺癌筛查乳腺临床**/
	private static Map<String, Content> mapCon_LC = new HashMap<String, Content>();
	/**乳腺癌筛查乳腺彩超**/
	private static Map<String, Content> mapCon_CC = new HashMap<String, Content>();
	/**乳腺癌筛查钼耙X线**/
	private static Map<String, Content> mapCon_X = new HashMap<String, Content>();
	/**乳腺癌筛查病理检查**/
	private static Map<String, Content> mapCon_BL = new HashMap<String, Content>();
	
	static {
		/**乳腺癌筛查基本信息**/
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_PERSON_INFOR_CONTENT));
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
				else if(secE.getName().equals("PERSON_Screening")){
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
						mapCon_PERSON_Screening.put(name, con);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		/**乳腺癌筛查乳腺临床**/
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_LC_CONTENT));
			Element node = document.getRootElement();
			Iterator<Element> it = node.elementIterator();
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
				mapCon_LC.put(name, con);
			}
				

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		
		/**乳腺癌筛查乳腺彩超**/
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_CC_CONTENT));
			Element node = document.getRootElement();
			Iterator<Element> it = node.elementIterator();
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
				mapCon_CC.put(name, con);
			}
				

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		/**乳腺癌筛查钼耙X线**/
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_X_CONTENT));
			Element node = document.getRootElement();
			Iterator<Element> it = node.elementIterator();
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
				mapCon_X.put(name, con);
			}
				

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		/**乳腺癌筛查病理检查**/
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_BL_CONTENT));
			Element node = document.getRootElement();
			Iterator<Element> it = node.elementIterator();
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
				mapCon_BL.put(name, con);
			}
				

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public Fb_fnb_rxaoffline() {
		tbName = "FB_FNB_RXAOffline";
	}

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {
		Connection con = JdbcPool.getConnection();
		String sql = "select * from " + tbName + " where jlzt != 9 AND XGRQ > ? AND XGRQ <= ?";
		//String sql = "select top 1 * from " + tbName ;
		PreparedStatement pspm = null;
		ResultSet rs = null;
		Map<String, String> xmlMap = new HashMap<String, String>();
		String xmlPersonInfo = null;
		String xmlLc = null;
		String xmlCc = null;
		String xmlX = null;
		String xmlBl = null;
		
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
			    	xmlPersonInfo = rs2strPersonInfo(localmap);
				    logger.debug(xmlPersonInfo);
			        xmlMap.put(rs.getString("LSH")+"xmlPersonInfo", xmlPersonInfo);
			        
			        xmlLc = rs2strLc(localmap);
			        logger.debug(xmlLc);
			        xmlMap.put(rs.getString("LSH")+"Lc", xmlLc);
			        
			        xmlCc = rs2strCc(localmap);
			        logger.debug(xmlCc);
			        xmlMap.put(rs.getString("LSH")+"Cc", xmlCc);
			        
			        xmlX = rs2strX(localmap);
			        logger.debug(xmlX);
			        xmlMap.put(rs.getString("LSH")+"X", xmlX);
			        
			        xmlBl = rs2strBl(localmap);
			        logger.debug(xmlBl);
			        xmlMap.put(rs.getString("LSH")+"Bl", xmlBl);
			        
				} catch (MalformedURLException e) {
					logger.error("createxml Fb_fnb_rxaoffline error:[" + rs.getString("LSH") + "] " + e.getMessage());
				} catch (DocumentException e) {
					logger.error("createxml Fb_fnb_rxaoffline error2:[" + rs.getString("LSH") + "] " + e.getMessage());
				}
			
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
	
	private String rs2strPersonInfo(Map<String,String> localmap) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_PERSON_INFOR_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();
        Element PERSON_INFOR = trans_noChild.addElement( "PERSON_INFOR" );
        Element Women_Census = trans_noChild.addElement( "PERSON_Screening" );
	
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
        for (String key : mapCon_PERSON_Screening.keySet()) {
        	String name = key;
        	String idname = mapCon_PERSON_Screening.get(key).getId();
        	String fun = mapCon_PERSON_Screening.get(key).getFun();
        	
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
            case "jg":
            	text = UnifieUtil.jg(text);
            	break;
            case "ry":
            	text = UnifieUtil.ry(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }
        
        return doc.getRootElement().asXML();
		
	}
	
	private String rs2strLc(Map<String,String> localmap) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_LC_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();

        for (String key : mapCon_LC.keySet()) {
        	String name = key;
        	String idname = mapCon_LC.get(key).getId();
        	String fun = mapCon_LC.get(key).getFun();
        	
        	Element elem = trans_noChild.addElement( name );
        	String text = idname.equals("")?"":localmap.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "D8":
            	text = UnifieUtil.convertDateToD8(text);
            	break;
            case "jg":
            	text = UnifieUtil.jg(text);
            	break;
            case "ry":
            	text = UnifieUtil.ry(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }

        return doc.getRootElement().asXML();
		
	}
	
	private String rs2strCc(Map<String,String> localmap) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_CC_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();

        for (String key : mapCon_CC.keySet()) {
        	String name = key;
        	String idname = mapCon_CC.get(key).getId();
        	String fun = mapCon_CC.get(key).getFun();
        	
        	Element elem = trans_noChild.addElement( name );
        	String text = idname.equals("")?"":localmap.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "D8":
            	text = UnifieUtil.convertDateToD8(text);
            	break;
            case "jg":
            	text = UnifieUtil.jg(text);
            	break;
            case "ry":
            	text = UnifieUtil.ry(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }

        return doc.getRootElement().asXML();
		
	}
	
	private String rs2strX(Map<String,String> localmap) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_X_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();

        for (String key : mapCon_X.keySet()) {
        	String name = key;
        	String idname = mapCon_X.get(key).getId();
        	String fun = mapCon_X.get(key).getFun();
        	
        	Element elem = trans_noChild.addElement( name );
        	String text = idname.equals("")?"":localmap.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "D8":
            	text = UnifieUtil.convertDateToD8(text);
            	break;
            case "jg":
            	text = UnifieUtil.jg(text);
            	break;
            case "ry":
            	text = UnifieUtil.ry(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }

        return doc.getRootElement().asXML();
		
	}
	
	private String rs2strBl(Map<String,String> localmap) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.FB_FNB_RXAOFFLINE_BL_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();

        for (String key : mapCon_BL.keySet()) {
        	String name = key;
        	String idname = mapCon_BL.get(key).getId();
        	String fun = mapCon_BL.get(key).getFun();
        	
        	Element elem = trans_noChild.addElement( name );
        	String text = idname.equals("")?"":localmap.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "D8":
            	text = UnifieUtil.convertDateToD8(text);
            	break;
            case "jg":
            	text = UnifieUtil.jg(text);
            	break;
            case "ry":
            	text = UnifieUtil.ry(text);
            	break;
            default:
            	break;
            }
            elem.setText( UnifieUtil.null2(text));
        }

        return doc.getRootElement().asXML();
		
	}
	
	public static void main(String[] args) {
		Fb_fnb_rxaoffline fb = new Fb_fnb_rxaoffline();
//		for (Entry<String, Content> entry : mapCon_BL.entrySet()) {
//			String key = entry.getKey();
//			//String value = entry.getValue();
//			System.out.println(key);
//			System.out.println(((Content)entry.getValue()).getName());
//		}
		fb.createxml("2013-01-01", "2013-01-05");
		
//		URL a = new Fb_fnb_rxaoffline().getClass().getClassLoader().getResource("Fb_fnb_njjl_body.xml");	
//		System.out.println(a);
//		File file = new File(a.getPath());
//		System.out.println(file.getAbsolutePath());
	}

}
