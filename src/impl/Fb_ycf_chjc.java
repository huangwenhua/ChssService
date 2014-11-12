/**
 * 
 */
package impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.DateUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
/**
 * 四十二天检查 生成xml
 * 
 * @author 张军
 * @version 1.0
 * @since 2014-04-16
 */
 
public class Fb_ycf_chjc  implements  BuildingXml{
	
	private Connection con;
	private PreparedStatement pspm;
	private ResultSet rs;
	private static Logger logger = Logger.getLogger(Fb_ycf_chjc.class);
	//按照接口规范定义元素map
	public Map<String, String> createElementMap(){
		Map<String, String> elementMap =new LinkedHashMap<String, String>();
		elementMap.put("userid", "nb_fybj");
		elementMap.put("password","123");
		elementMap.put("trans_no","B04.02.08.00");
		elementMap.put("RPTNO","");
		elementMap.put("HEALTH_ID","");
		elementMap.put("CHECK_DATE","");
		elementMap.put("MONITOR","");
		elementMap.put("MONITOR_REASON","");
		elementMap.put("MONITOR_DATE","");
		elementMap.put("SPB","");
		elementMap.put("DPB","");
		elementMap.put("TEMPERATURE","");
		elementMap.put("GENERAL","");
		elementMap.put("KUNG","");
		elementMap.put("KUNG_OTHER","");
		elementMap.put("PERINEUM","");
		elementMap.put("LOCHIA","");
		elementMap.put("VULVA","");
		elementMap.put("VAGINA","");
		elementMap.put("CERVICAL","");
		elementMap.put("UTERUS","");
		elementMap.put("ACCESSORIES","");
		elementMap.put("HGB","");
		elementMap.put("DIAGNOSIS","");
		elementMap.put("OTHER_DIAGNOSIS","");
		elementMap.put("PUERPERAL_INFECTION","");
		elementMap.put("UTERINE_INVOLUTION_BAD","");
		elementMap.put("OTHER_ANOMALIES","");
		elementMap.put("REMARKS","");
		elementMap.put("CHECK_EMP_ID","");
		elementMap.put("CREATE_EMP_NAME","");
		elementMap.put("CHECK_ORG_CODE","");
		elementMap.put("CREATE_ORG_NAME","");
		elementMap.put("UPLOAD_TIME", DateUtil.nowDT15());
		return elementMap;
	}
	
	//转码
	public List<Map<String,String>>  convertCode(String beginTime,String endTime){

		
    	List<Map<String,String>> mapList =new ArrayList<Map<String,String>>();
    	try {
        //连接数据库获取记录，并进行转换
    		 con = JdbcPool.getConnection();
		String sql = "select GRBJH,LSH,JCRQ,SFJXJC,TZJCYY,TZJCSJ,SSY,SZY,TW,YBQK,GG,GGSZ,HY,EL,YY,YD,GJ,ZG,FJ,XHDB,YWJBZD,JBZDDM,CRGR,ZGFJBL,QTYCQK,BJJHSYZDDM,JCZDM,JCZMC,DJJGDM,DJJGMC from FB_YCF_CHJC where xgrq > ? and xgrq <= ?";		
		
			pspm = con.prepareStatement(sql);
			pspm.setString(1, beginTime);
			pspm.setString(2, endTime);
		    rs = pspm.executeQuery();
			
			while (rs.next()) {
		        Map<String, String> elementMap = new HashMap<String, String>();
		        elementMap=createElementMap();
		        elementMap.put("idFlag",  rs.getString("GRBJH"));
		        elementMap.put("RPTNO",rs.getString("GRBJH"));                 //检查流水号           
		        elementMap.put("HEALTH_ID","");             //个人基本信息标识号   
		        elementMap.put("CHECK_DATE",dateFormat(rs.getString("JCRQ")));            //检查日期             
		        elementMap.put("MONITOR",getMonitor(rs.getString("SFJXJC")));               //是否继续监测         
		        elementMap.put("MONITOR_REASON",getMonitor_reason(rs.getString("TZJCYY")));        //终止监测原因         
		        elementMap.put("MONITOR_DATE",dateFormat(rs.getString("TZJCSJ")));          //终止监测时间         
		        elementMap.put("SPB",rs.getString("SSY"));                   //收缩压               
		        elementMap.put("DPB",rs.getString("SZY"));                   //舒张压               
		        elementMap.put("TEMPERATURE",rs.getString("TW"));           //体温                 
		        elementMap.put("GENERAL",getGeneral(rs.getString("YBQK")));               //产妇一般情况代码     
		        elementMap.put("KUNG",getKung(rs.getString("GG")));                  //宫高代码             
		        elementMap.put("KUNG_OTHER",rs.getString("GGSZ"));            //宫高数值             
		        elementMap.put("PERINEUM",getPerineum(rs.getString("HY")));              //会阴代码             
		        elementMap.put("LOCHIA",getLochia(rs.getString("EL")));                //恶露代码             
		        elementMap.put("VULVA",getVulva(rs.getString("YY")));                 //外阴代码             
		        elementMap.put("VAGINA",getVagina(rs.getString("YD")));                //阴道代码             
		        elementMap.put("CERVICAL",getCervical(rs.getString("GJ")));              //宫颈代码             
		        elementMap.put("UTERUS",getUterus(rs.getString("ZG")));                //子宫代码             
		        elementMap.put("ACCESSORIES",getAccessories(rs.getString("FJ")));           //附件代码             
		        elementMap.put("HGB",rs.getString("XHDB"));                   //血红蛋白值           
		        elementMap.put("DIAGNOSIS",getDiagnosis(rs.getString("YWJBZD")));             //有无疾病诊断代码     
		        elementMap.put("OTHER_DIAGNOSIS",limitLength30(getOther_diagnosis(rs.getString("JBZDDM"))));       //疾病诊断代码         
		        elementMap.put("PUERPERAL_INFECTION",getPuerperal_infection(rs.getString("CRGR")));   //产褥感染代码         
		        elementMap.put("UTERINE_INVOLUTION_BAD",getUterine_involution_bad(rs.getString("ZGFJBL")));//子宫复旧不良代码     
		        elementMap.put("OTHER_ANOMALIES",getOther_anomalies(rs.getString("QTYCQK")));       //其它异常情况代码     
		        elementMap.put("REMARKS",limitLength30(getRemarks(rs.getString("BJJHSYZDDM"))));               //保健及计划生育指导代 
		        //--------------------------------------------------
		        elementMap.put("CHECK_EMP_ID",getCheck_emp_id(rs.getString("JCZDM")));          //检查人员编码         
		        elementMap.put("CREATE_EMP_NAME",limitLength30(rs.getString("JCZMC")));       //检查人员姓名         
		        
		        //--------------------------------------------
		        elementMap.put("CHECK_ORG_CODE",DictMap.getZcjg(rs.getString("DJJGDM")));        //登记机构代码         
		        elementMap.put("CREATE_ORG_NAME",limitLength100(rs.getString("DJJGMC")));       //登记机构名称         
				//将map加入List中
				mapList.add(elementMap);

			}
		} catch (SQLException e) {
			logger.error("error :" + e.getMessage());
			e.printStackTrace();
		}finally{
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
		
		return mapList;

	}
	
	
	public  Map<String, String> bulidXML(String beginTime,String endTime){
		List<Map<String,String>> mapList = convertCode(beginTime,endTime);
		Map<String, String> xmlResultMap =new LinkedHashMap<String, String>();
		for (int i = 0; i < mapList.size(); i++) {
			 Document document = DocumentHelper.createDocument();	
			 Element root = document.addElement("body"); 
			 Element head = root.addElement("head"); 
			 Map<String, String> strMap =mapList.get(i);
			 String idFlag =strMap.remove("idFlag");
			 Element userid = head.addElement("userid"); 
			 userid.setText(strMap.remove("userid"));
			 
	           Element password = head.addElement("password");  
	           password.setText(strMap.remove("password"));  	                
	           //添加trans_no
	           Element trans_no = head.addElement("trans_no");  
	           trans_no.setText(strMap.remove("trans_no"));            
	           Element resquest = root.addElement("resquest");
	           Element trans_noChild = resquest.addElement("B04.02.08.00");
	           for (String key : strMap.keySet()) {
	        	 //  System.out.println("key= "+ key + " and value= " + strMap.get(key));
	        	 Element element =  trans_noChild.addElement(key);
	        	 if(strMap.get(key)!=null){
	        		 element.setText(strMap.get(key));
	        	 }else{
	        		 element.setText("");
	        	 }
	        	
	        	   
	        	  }
	           xmlResultMap.put(idFlag, document.getRootElement().asXML());
		}
		return xmlResultMap;
		
	}
	
	public Map<String, String> createxml(String beginTime,String endTime){
		 Map<String, String> xmlresultMap =	 bulidXML(beginTime,endTime);	
			return  xmlresultMap;
			
		}
	
	//转换时间格式
	public String dateFormat(String str){
		String time=null;  
        if(str!=null){
        	try {
        		//System.out.println(str);
    			SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
    			Date dt = new SimpleDateFormat("yyyy-mm-dd").parse(str);
    			 time = sdf.format(dt);
    		//	System.out.println(time);
    		} catch (ParseException e) {
    			logger.error("error :" + e.getMessage());
    			e.printStackTrace();
    		}
        	
        }		
		return time;
	}
	
	//截取字符串长度
	//限定50个字符
	public  String  limitLength50(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>50) {
				result=str.substring(0, 50/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	
	//限定30个字符
	public  String  limitLength30(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>30) {
				result=str.substring(0, 30/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	
	//限定100个字符
	public  String  limitLength100(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>100) {
				result=str.substring(0, 100/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	
	//限定10个字符
	public  String  limitLength10(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>10) {
				result=str.substring(0, 10/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	//生成需要的get方法

	public String getHealth_id(String str) {
		return null;
	}

	public String getMonitor(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
		if(str.equals("1")){
			result="1" ;
		}else
			if(str.equals("2")){
				result="2" ;
			}else
				{
					result="9" ;
				}
		return result;
	}

	public String getMonitor_reason(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
			if(str.equals("1")){
				result="1" ;
			}else
				if(str.equals("2")){
					result="2" ;
				}else
					if(str.equals("3")){
						result="3" ;
					}else
						if(str.equals("4")){
							result=null ;
						}
		return result;
	}

	public String getGeneral(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getKung(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getPerineum(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getLochia(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getVulva(String str) {
		String result =null;
		if (str!=null) {
			if(str.equals("9")){
				result="3";
			}else {
				result =str;
			}
		}
		return result;
	}

	public String getVagina(String str) {
		String result =null;
		if (str!=null) {
			if(str.equals("9")){
				result="3";
			}else {
				result =str;
			}
		}
		return result;
	}
	
	public String getCervical(String str) {
		String result =null;
		if (str!=null) {
			if(str.equals("9")){
				result="3";
			}else {
				result =str;
			}
		}
		return result;
	}

	public String getUterus(String str) {
		String result =null;
		if (str!=null) {
			if(str.equals("9")){
				result="3";
			}else {
				result =str;
			}
		}
		return result;
	}

	public String getAccessories(String str) {
		String result =null;
		if (str!=null) {
			if(str.equals("9")){
				result="3";
			}else {
				result =str;
			}
		}
		return result;
	}

	public String getDiagnosis(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}else  if (str==null) {
			result="9";
		}
		return result;
	}

	public String getOther_diagnosis(String str) {
		String result = null;
		if (str != null) {
			if (str.contains("/")) {
				result = str.replace("/", "|");
				if (result.charAt(result.length()-1)=='|') {
					result=result.substring(0, result.length()-1);
				}

			}

		}
		return result;

	}

	public String getPuerperal_infection(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getUterine_involution_bad(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getOther_anomalies(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getRemarks(String str) {
		String result = null;
		if (str != null) {
			if (str.contains("/")) {
				result = str.replace("/", "|");
				if (result.charAt(result.length()-1)=='|') {
					result=result.substring(0, result.length()-1);
				}

			}

		}
		return result;

	}

	public String getCheck_emp_id(String str) {
		String resultString =null;
		if(str!=null){
			try {
				resultString=DictMap.getCzry(str);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public String getCheck_org_code(String str) {
		String resultString =null;
		if(str!=null){
			try {
				resultString=DictMap.getZcjg(str);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	 public static void main(String[] args) throws IOException{
			Fb_ycf_chjc nb_fybj =new Fb_ycf_chjc();
			Map<String, String> result = nb_fybj.createxml("2012-03-30 00:00:00.000","2012-03-31 08:00:00.000");
			  Iterator it = result.entrySet().iterator();
			   while (it.hasNext()) {
			    Map.Entry entry = (Map.Entry) it.next();
			    Object key = entry.getKey();
			    Object value = entry.getValue();
			    System.out.println("key=" + key + " value=" + value);
			   }
		 
	 }

}
