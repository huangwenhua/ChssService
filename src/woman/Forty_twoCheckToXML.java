/**
 * 
 */
package woman;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import coon.DBConnectionManager;

/**
/**
 * 四十二天检查 生成xml
 * 
 * @author 张军
 * @version 1.0
 * @since 2014-04-16
 */
 
public class Forty_twoCheckToXML {
	
	//按照接口规范定义元素map
	public Map<String, String> createElementMap(){
		Map<String, String> elementMap =new LinkedHashMap<String, String>();
		elementMap.put("userid", "test");
		elementMap.put("password","123456");
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
		return elementMap;
	}
	
	//转码
	public List<Map<String,String>>  convertCode(){

		
    	List<Map<String,String>> mapList =new ArrayList<Map<String,String>>();
		
        //连接数据库获取记录，并进行转换
		DBConnectionManager mang = new DBConnectionManager();
		Connection con = mang.getConnection();
		String sql = "select * from FB_YCF_CHJC where lsh='33020308701000120110916103708530'";
		PreparedStatement pspm;
		try {
			pspm = con.prepareStatement(sql);
			ResultSet rs = pspm.executeQuery();
			
			while (rs.next()) {
		        Map<String, String> elementMap = new HashMap<String, String>();
		        elementMap=createElementMap();
			//	System.out.print("添加个人保健号："+rs.getString("GRBJH"));
		        elementMap.put("RPTNO",rs.getString("LSH"));                 //检查流水号           
		        elementMap.put("HEALTH_ID",null);             //个人基本信息标识号   
		        elementMap.put("CHECK_DATE",rs.getString("JCRQ"));            //检查日期             
		        elementMap.put("MONITOR",getMonitor(rs.getString("SFJXJC")));               //是否继续监测         
		        elementMap.put("MONITOR_REASON",getMonitor_reason(rs.getString("TZJCYY")));        //终止监测原因         
		        elementMap.put("MONITOR_DATE",rs.getString("TZJCSJ"));          //终止监测时间         
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
		        elementMap.put("OTHER_DIAGNOSIS",getOther_diagnosis(rs.getString("JBZDDM")));       //疾病诊断代码         
		        elementMap.put("PUERPERAL_INFECTION",getPuerperal_infection(rs.getString("CRGR")));   //产褥感染代码         
		        elementMap.put("UTERINE_INVOLUTION_BAD",getUterine_involution_bad(rs.getString("ZGFJBL")));//子宫复旧不良代码     
		        elementMap.put("OTHER_ANOMALIES",getOther_anomalies(rs.getString("QTYCQK")));       //其它异常情况代码     
		        elementMap.put("REMARKS",getRemarks(rs.getString("BJJHSYZDDM")));               //保健及计划生育指导代 
		        //--------------------------------------------------
		        elementMap.put("CHECK_EMP_ID",getCheck_emp_id(rs.getString("JCZDM")));          //检查人员编码         
		        elementMap.put("CREATE_EMP_NAME",rs.getString("JCZMC"));       //检查人员姓名         
		        
		        //--------------------------------------------
		        elementMap.put("CHECK_ORG_CODE",getCheck_org_code(rs.getString("DJJGDM")));        //登记机构代码         
		        elementMap.put("CREATE_ORG_NAME",rs.getString("DJJGMC"));       //登记机构名称         
		                                                                                          
		                                                                                                                                                                 
		                                                                                                                                                                 
		        
				//将map加入List中
				mapList.add(elementMap);

			}
			pspm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		
		}
		
		return mapList;

	}
	
	
	public void bulidXML(){
		List<Map<String,String>> mapList = convertCode();
		for (int i = 0; i < mapList.size(); i++) {
			 Document document = DocumentHelper.createDocument();	
			 Element root = document.addElement("body"); 
			 Element head = root.addElement("head"); 
			 Map<String, String> strMap =mapList.get(i);
			 Element userid = head.addElement("userid"); 
			 userid.setText(strMap.remove("userid"));
			 
	           Element password = head.addElement("password");  
	           password.setText(strMap.remove("password"));  	                
	           //添加trans_no
	           Element trans_no = head.addElement("trans_no");  
	           trans_no.setText(strMap.remove("trans_no"));            
	           Element request = root.addElement("request");
	           Element trans_noChild = request.addElement("B04.02.08.00");
	           for (String key : strMap.keySet()) {
	        	 //  System.out.println("key= "+ key + " and value= " + strMap.get(key));
	        	 Element element =  trans_noChild.addElement(key);
	        	 if(strMap.get(key)!=null){
	        		 element.setText(strMap.get(key));
	        	 }else{
	        		 element.setText("");
	        	 }
	        	
	        	   
	        	  }
		        try {
	            OutputFormat  format = new OutputFormat("   ",true,"utf-8"); 
		           StringWriter out = new StringWriter();
		           XMLWriter writer = new XMLWriter(out, format);
		            writer.write(document);
		            writer.flush();
		            System.out.println(out.toString());
				
			} catch (IOException  e) {
			     throw new RuntimeException("IOException while generating textual "
		                    + "representation: " + e.getMessage());
			}
			
		}
		
	}
	
	//生成需要转啊的get方法
	/**
	 * @return the health_id
	 */
	public String getHealth_id(String str) {
		return null;
	}
	/**
	 * @return the monitor
	 */
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
	/**
	 * @return the monitor_reason
	 */
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
							result="99" ;
						}
		return result;
	}
	/**
	 * @return the general
	 */
	public String getGeneral(String str) {
		return str;
	}
	/**
	 * @return the kung
	 */
	public String getKung(String str) {
		return str;
	}
	/**
	 * @return the perineum
	 */
	public String getPerineum(String str) {
		return str;
	}
	/**
	 * @return the lochia
	 */
	public String getLochia(String str) {
		return str;
	}
	/**
	 * @return the vulva
	 */
	public String getVulva(String str) {
		return str;
	}
	/**
	 * @return the vagina
	 */
	public String getVagina(String str) {
		return str;
	}
	/**
	 * @return the cervical
	 */
	public String getCervical(String str) {
		return str;
	}
	/**
	 * @return the uterus
	 */
	public String getUterus(String str) {
		return str;
	}
	/**
	 * @return the accessories
	 */
	public String getAccessories(String str) {
		return str;
	}
	/**
	 * @return the diagnosis
	 */
	public String getDiagnosis(String str) {
		return str;
	}
	/**
	 * @return the other_diagnosis
	 */
	public String getOther_diagnosis(String str) {
		String result =null;
		if(str!=null){
			if(str.contains("/")){
				result=	str.replace("/", "|");
				
			}
			
		}
		return result;
	}
	/**
	 * @return the puerperal_infection
	 */
	public String getPuerperal_infection(String str) {
		return str;
	}
	/**
	 * @return the uterine_involution_bad
	 */
	public String getUterine_involution_bad(String str) {
		return str;
	}
	/**
	 * @return the other_anomalies
	 */
	public String getOther_anomalies(String str) {
		return str;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks(String str) {
		String result =null;
		if(str!=null){
			if(str.contains("/")){
				result=	str.replace("/", "|");
				
			}
			
		}
		return result;
	}
	/**
	 * @return the check_emp_id
	 */
	public String getCheck_emp_id(String str) {
		return str;
	}
	/**
	 * @return the check_org_code
	 */
	public String getCheck_org_code(String str) {
		return str;
	}
	
	
	 public static void main(String[] args) throws IOException{
		 Forty_twoCheckToXML testCheckToXML =new Forty_twoCheckToXML();
		 testCheckToXML.bulidXML();
		 
	 }

}
