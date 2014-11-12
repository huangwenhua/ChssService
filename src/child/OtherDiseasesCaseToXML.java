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
 * <p>标题: OtherDiseasesCaseToXML.java</p>
 * <p>业务描述:其他疾病 生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangjun_yl@dhcc.com.cn'>张军</a>
 * @date 2014年4月17日
 * @version V1.0 
 */
public class OtherDiseasesCaseToXML {
	//根据接口规范拼装元素map
	//按照接口规范拼装element map
	public Map<String, String> createElementMap(){
		Map<String, String> elementMap =new LinkedHashMap<String, String>();
		elementMap.put("userid", "test");
		elementMap.put("password","123456");
		elementMap.put("trans_no","B04.01.02.11");
		elementMap.put("RPTNO","");
		elementMap.put("HEALTH_ID","");
		elementMap.put("CREATE_TIME","");
		elementMap.put("CREATE_EMP_ID","");
		elementMap.put("CREATE_ORG_CODE","");
		elementMap.put("START_DATE","");
		elementMap.put("HEALTH_FAIL","");
		elementMap.put("REASON","");
		elementMap.put("CLOSED","");
		elementMap.put("CLOSED_DATE","");
		elementMap.put("CLOSED_EMP_ID","");
		elementMap.put("CLOSED_CONCLUSIONS","");
		elementMap.put("CLOSE_ORG_CODE","");
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
			String sql = "select top 20 * from EB_TRE_QTZA ";
			PreparedStatement pspm;
			try {
				pspm = con.prepareStatement(sql);
				ResultSet rs = pspm.executeQuery();
				
				while (rs.next()) {
			        Map<String, String> elementMap = new HashMap<String, String>();
			        elementMap=createElementMap();			      
			        elementMap.put("RPTNO",rs.getString("GRBJH"));
			        elementMap.put("HEALTH_ID","");
			        elementMap.put("CREATE_TIME",rs.getString("JZARQ"));
			        
			        //----------------------------------
			        elementMap.put("CREATE_EMP_ID",rs.getString("JZAYS"));
			        
			        //-------------------------------------
			        elementMap.put("CREATE_ORG_CODE","");
			        
			        
			        elementMap.put("START_DATE",rs.getString("FSRQ"));
			        elementMap.put("HEALTH_FAIL",rs.getString("TRYS"));
			        elementMap.put("REASON",getReason(rs.getString("TRYYDM")));
			        
			        //--------------------------------
			        elementMap.put("CLOSED",getClosed(rs.getString("JAZT")));
			        elementMap.put("CLOSED_DATE",rs.getString("JARQ"));
			        
			        //--------------------------------
			        elementMap.put("CLOSED_EMP_ID","");
			        elementMap.put("CLOSED_CONCLUSIONS",getClosed_conclusions(rs.getString("JAJL")));
			        elementMap.put("CLOSE_ORG_CODE","");
			        
			        //----------------------------------
			        elementMap.put("CREATE_EMP_NAME",rs.getString("XGRY"));
			        
			        //--------------------------
			        elementMap.put("CREATE_ORG_NAME",rs.getString("XGJGDM"));
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
		           Element trans_noChild = request.addElement("B04.01.02.11");
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
		
		
		
		
		//转码方法
		/**
		 * @return the create_emp_id
		 */
		public String getCreate_emp_id(String str) {
			return null;
		}
		/**
		 * @return the create_org_code
		 */
		public String getCreate_org_code(String str) {
			return null;
		}
		/**
		 * @return the reason
		 */
		public String getReason(String str) {
			
			String result =null;
			if(str!=null){
				if(str.contains("/")){
					result=	str.replace("/", "|");
					
				}
				
			}
			return result;
		}
		/**
		 * @return the closed
		 */
		public String getClosed(String str) {
			return str;
		}
		/**
		 * @return the closed_emp_id
		 */
		public String getClosed_emp_id(String str) {
			return null;
		}
		/**
		 * @return the closed_conclusions
		 */
		public String getClosed_conclusions(String str) {
			return str;
		}
		/**
		 * @return the close_org_code
		 */
		public String getClose_org_code(String str) {
			return null;
		}
		
		//测试方法
		 public static void main(String[] args){
			 OtherDiseasesCaseToXML test =new OtherDiseasesCaseToXML();
			 test.bulidXML();
		 }

}
