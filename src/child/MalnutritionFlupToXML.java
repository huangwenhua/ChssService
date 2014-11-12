/**
 * 
 */
package child;

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
 * <p>标题: MalnutritionFlupToXML.java</p>
 * <p>业务描述:儿童营养不良随访 生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangjun_yl@dhcc.com.cn'>张军</a>
 * @date 2014年4月17日
 * @version V1.0 
 */
public class MalnutritionFlupToXML {
	//按照接口规范拼装element map
	public Map<String, String> createElementMap(){
		Map<String, String> elementMap =new LinkedHashMap<String, String>();
		elementMap.put("userid", "test");
		elementMap.put("password","123456");
		elementMap.put("trans_no","B04.01.02.06");
		elementMap.put("RPTNO","");
		elementMap.put("VISITS_SN","");
		elementMap.put("HEALTH_ID","");
		elementMap.put("VISITS_DATE","");
		elementMap.put("VISITS_EMP_ID","");
		elementMap.put("VISITS_ORG_CODE","");
		elementMap.put("ASSISTANT_EXAMINATION","");
		elementMap.put("DIAGNOSTIC_ADVICE","");
		elementMap.put("TREATMENT_GUIDELINES","");
		elementMap.put("CONCLUSION","");
		elementMap.put("REVIEW_DATE","");
		elementMap.put("CREATE_EMP_NAME","");
		elementMap.put("CREATE_ORG_NAME","");
		return elementMap;
	}
	
	//转码
		
		public List<Map<String,String>>  convertCode(){

			
	    	List<Map<String,String>> mapList =new ArrayList<Map<String,String>>();
			
	        //连接数据库获取记录，并进行转换
			DBConnectionManager mang = new DBConnectionManager();
			Connection con = mang.getConnection();
			String sql = "select top 20 * from EB_TRE_YYBLSF ";
			PreparedStatement pspm;
			try {
				pspm = con.prepareStatement(sql);
				ResultSet rs = pspm.executeQuery();
				
				while (rs.next()) {
			        Map<String, String> elementMap = new HashMap<String, String>();
			        elementMap=createElementMap();			      
			        elementMap.put("RPTNO",rs.getString("GRBJH"));
					elementMap.put("VISITS_SN",rs.getString("ZALSH"));
					elementMap.put("HEALTH_ID","");
					elementMap.put("VISITS_DATE",rs.getString("JCRQ"));
					//--------------------------------
					elementMap.put("VISITS_EMP_ID","");
					elementMap.put("VISITS_ORG_CODE","");
					elementMap.put("ASSISTANT_EXAMINATION",rs.getString("SHJC"));
					elementMap.put("DIAGNOSTIC_ADVICE",rs.getString("ZDYJ"));
					elementMap.put("TREATMENT_GUIDELINES",rs.getString("ZLZD"));
					elementMap.put("CONCLUSION",rs.getString("SFJL"));
					elementMap.put("REVIEW_DATE",rs.getString("DJRQ"));
					elementMap.put("CREATE_EMP_NAME",rs.getString("DJRY"));
					elementMap.put("CREATE_ORG_NAME",rs.getString("DJJGMC"));
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
		           Element trans_noChild = request.addElement("B04.01.02.06");
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
	
	//生成转码方法
	/**
	 * @return the visits_emp_id
	 */
	public String getVisits_emp_id(String str)  {
		return null;
	}
	/**
	 * @return the visits_org_code
	 */
	public String getVisits_org_code(String str) {
		return null;
	}
	
	
	//测试方法
	 public static void main(String[] args){
		 MalnutritionCaseToXML test =new MalnutritionCaseToXML();
		 test.bulidXML();
	 }

}
