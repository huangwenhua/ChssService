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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import Interface.BuildingXml;
import coon.DBConnectionManager;

/**
 * <p>标题: ChildCareToXML.java</p>
 * <p>业务描述:儿童保健卡 生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangjun_yl@dhcc.com.cn'>张军</a>
 * @date 2014年4月17日
 * @version V1.0 
 */

public class Eb_bj_ybqk implements  BuildingXml{

	//获取convertCode() 方法返回的list，循环遍历list
	//按照接口规范要求输出 儿童保健卡的xml字符串
	
	
	public Map<String, String> bulidXML(String beginTime,String endTime){
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
	           Element request = root.addElement("request");
	           Element trans_noChild = request.addElement("B04.01.01.01");
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
		       //     System.out.println(out.toString());
		            xmlResultMap.put(idFlag, out.toString());
				
			} catch (IOException  e) {
			     throw new RuntimeException("IOException while generating textual "
		                    + "representation: " + e.getMessage());
			}
			
		}
		return xmlResultMap;
		
	}
	
	


	
	//从数据库中取出样本数据，并按照接口规范进行转码
	//编码思路，从数据库中获取相关信息，匹配xml相关元素
	//查询儿童一般情况表 EB_BJ_YBQK
	//将获取的记录信息放入对应的map集合中，将map添加进list列表中，返回list	
	
	public List<Map<String,String>>  convertCode(String beginTime,String endTime){

		
    	List<Map<String,String>> mapList =new ArrayList<Map<String,String>>();
		
        //连接数据库获取记录，并进行转换
		DBConnectionManager mang = new DBConnectionManager();
		Connection con = mang.getConnection();
		String sql = "select a.*,b.hj from EB_BJ_YBQK a,DA_GR_HXDA b where a.GRBJH = b.GRBJH  and a.xgrq > ? and a.xgrq < ?";
		PreparedStatement pspm;
		try {
			
			pspm = con.prepareStatement(sql);
			pspm.setString(1, beginTime);
			pspm.setString(2, endTime);
			ResultSet rs = pspm.executeQuery();
			
			while (rs.next()) {
		        Map<String, String> elementMap = new HashMap<String, String>();
		        elementMap=createElementMap();
		        //加入GRBJH作为每个xml记录的标识符		       
		        elementMap.put("idFlag",  rs.getString("GRBJH"));
		        
			//	System.out.print("添加个人保健号："+rs.getString("GRBJH"));
				elementMap.put("RPTNO", rs.getString("GRBJH"));
				//母亲保健号 
				elementMap.put("MOTHER_RPTNO",rs.getString("MQGRBJH"));
				
				//
				elementMap.put("HEALTH_ID","");
				
				//户籍 ---------------------------------------
				elementMap.put("PGRESIDE",rs.getString("hj"));
				
//				System.out.println("户籍："+rs.getString("hj"));
				//儿童性质
				elementMap.put("CHILD_CHARACTER",rs.getString("ETXZ"));
				
				//父亲姓名
				elementMap.put("FATHER_NAME",rs.getString("FQXM"));
				
				//父亲工作单位 ，接口规范中要求隐藏，因此直接置为空
				elementMap.put("FATHER_UNIT","");
				
				//父亲身份证号,接口规范中要求隐藏，因此也直接置为空
				elementMap.put("FATHER_IDCARD","");
				
				//父亲电话
				elementMap.put("FATHER_PHONE",rs.getString("FQLXDH"));
				
				//父亲职业				
                elementMap.put("FATHER_OCCUPATION", getFather_occupation(rs.getString("FQZY")) );
                
                //父亲文化
				elementMap.put("FATHER_EDUCTION", getFather_eduction(rs.getString("FQWHCD")));
				
				//母亲姓名
				elementMap.put("MOTHER_NAME",rs.getString("MQXM"));
				
				//母亲工作单位
				elementMap.put("MOTHER_UNIT",rs.getString("MQGZDW"));
				
				//母亲身份证号，接口规范中要求隐藏母亲身份证号，因此直接置为空
				elementMap.put("MOTHER_IDCARD","");
				
				//母亲联系电话
				elementMap.put("MOTHER_PHONE",rs.getString("MQLXDH"));
				
				//母亲职业
				elementMap.put("MOTHER_OCCUPATION",getMother_occupation(rs.getString("MQZY")));
				
				//母亲文化
				elementMap.put("MOTHER_EDUCTION",getMother_eduction(rs.getString("MQWHCD")));
				
				//母亲出生日期
				elementMap.put("MOTHER_BIRTHDAY",dateFormat(rs.getString("MQCSRQ")));
				
				//母亲孕管地
				elementMap.put("PREGNANT_MOTHERS",rs.getString("MQYGD"));
				
				//疾病家族史
				elementMap.put("DISEASE_FAMILY",getDisease_family(rs.getString("JBJZS")));
				
				//疾病家族史q   ------------------------------- 待定
//				elementMap.put("DISEASE_FAMILY_OTHER",rs.getString("x"));
				
				//胎    数
				elementMap.put("LITTERS", getLitters(rs.getString("TS")));
				
				//胎次
				elementMap.put("LITTERS_TIMES",rs.getString("TC"));
				
				
				//产    次
				elementMap.put("CHILDBIRTH_TIMES",rs.getString("CC"));
				
				//核实孕周
				elementMap.put("GESTATIONAL_AGE",rs.getString("YZ"));
				
				//分娩方式
				elementMap.put("CHILDBIRTH",getChildbirth(rs.getString("FMFS")));
				
				//出生体重(g)
				elementMap.put("WEIGHT",rs.getString("CSTZ"));
				
				//出生身长(cm)
				elementMap.put("HEIGHT",rs.getString("CSSC"));
				
				//出生头围(cm)
				elementMap.put("HEAD_CIRCUMFERENCE",rs.getString("CSTW"));
				
				//阿氏评分(一分钟)
				elementMap.put("APGAR_SCORE1",getApgar_score1(rs.getString("ASPF1")));
				
				//阿氏评分(五分钟)
				elementMap.put("APGAR_SCORE5",getApgar_score5(rs.getString("ASPF5")));
				
				//母孕期高危情况
				elementMap.put("MOTHER_HIGH_RISK",rs.getString("MYQGWQKMC"));
				
				//新生儿出生缺陷
				elementMap.put("BIRTH_DEFECTS",getBirth_defects(rs.getString("CSQX")));
				
				//母亲产时并发症
				elementMap.put("COMPLICATIONS",getComplications(rs.getString("MQCSBFZDM")));
				
				//新生儿疾病筛查
				elementMap.put("NEONATAL_SCREENING",getNeonatal_screening(rs.getString("XSEJBSC")));
				
				//新生儿听力筛查
				elementMap.put("HEARING_SCREENING",rs.getString("XSETLSC"));
				
				//建册日期  ---------------------------------------
				elementMap.put("CREATE_DATE",dateFormat(rs.getString("DJRQ")));
				
				//建册时间  ----------------------------------------
				elementMap.put("CREATE_TIME_TYPE",getCreate_time_type(rs.getString("DJRQ")));
				
				//填表人
				elementMap.put("CREATE_EMP_ID",getCreate_emp_id(rs.getString("TBR")));
				
				//填表单位 -----------------------------------------
				elementMap.put("CREATE_ORG_CODE",getCreate_org_code(rs.getString("XGJGDM")));
				
				//监测地变更
				elementMap.put("MONITORING_CHANGE",getMonitoring_change(rs.getString("JCDBG")));
				
				//转往日期
				elementMap.put("CHANGETIME",dateFormat(rs.getString("ZWRQ")));
				
				//转往地
				elementMap.put("TRANSFERRED",rs.getString("ZWDMC"));
				
				//是否结案
				elementMap.put("CLOSECASE",getClosecase(rs.getString("JABZ")));
				
				//检查者姓名 ------------------------------------------
				elementMap.put("CREATE_EMP_NAME",rs.getString("XGRY"));
				
				//机构名称--------------------------------------------
				elementMap.put("CREATE_ORG_NAME",rs.getString("XGJGMC"));
				
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
	
	
	
	
	//按照接口规范创建元素map
	public Map<String, String> createElementMap(){
		Map<String, String> elementMap =new LinkedHashMap<String, String>();
		elementMap.put("userid", "test");
		elementMap.put("password","123456");
		elementMap.put("trans_no","B04.01.01.01");
		elementMap.put("RPTNO","");
		elementMap.put("MOTHER_RPTNO","");
		elementMap.put("HEALTH_ID","");
		elementMap.put("PGRESIDE","");
		elementMap.put("CHILD_CHARACTER","");
		elementMap.put("FATHER_NAME","");
		elementMap.put("FATHER_UNIT","");
		elementMap.put("FATHER_IDCARD","");
		elementMap.put("FATHER_PHONE","");
		elementMap.put("FATHER_OCCUPATION","");
		elementMap.put("FATHER_EDUCTION","");
		elementMap.put("MOTHER_NAME","");
		elementMap.put("MOTHER_UNIT","");
		elementMap.put("MOTHER_IDCARD","");
		elementMap.put("MOTHER_PHONE","");
		elementMap.put("MOTHER_OCCUPATION","");
		elementMap.put("MOTHER_EDUCTION","");
		elementMap.put("MOTHER_BIRTHDAY","");
		elementMap.put("PREGNANT_MOTHERS","");
		elementMap.put("DISEASE_FAMILY","");
		elementMap.put("DISEASE_FAMILY_OTHER","");
		elementMap.put("LITTERS","");
		elementMap.put("LITTERS_TIMES","");
		elementMap.put("CHILDBIRTH_TIMES","");
		elementMap.put("GESTATIONAL_AGE","");
		elementMap.put("CHILDBIRTH","");
		elementMap.put("WEIGHT","");
		elementMap.put("HEIGHT","");
		elementMap.put("HEAD_CIRCUMFERENCE","");
		elementMap.put("APGAR_SCORE1","");
		elementMap.put("APGAR_SCORE5","");
		elementMap.put("MOTHER_HIGH_RISK","");
		elementMap.put("BIRTH_DEFECTS","");
		elementMap.put("COMPLICATIONS","");
		elementMap.put("NEONATAL_SCREENING","");
		elementMap.put("HEARING_SCREENING","");
		elementMap.put("CREATE_DATE","");
		elementMap.put("CREATE_TIME_TYPE","");
		elementMap.put("CREATE_EMP_ID","");
		elementMap.put("CREATE_ORG_CODE","");
		elementMap.put("MONITORING_CHANGE","");
		elementMap.put("CHANGETIME","");
		elementMap.put("TRANSFERRED","");
		elementMap.put("CLOSECASE","");
		elementMap.put("CREATE_EMP_NAME","");
		elementMap.put("CREATE_ORG_NAME","");
		return elementMap;
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
        		System.out.println(str);
    			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/mm/dd");
    			Date dt = new SimpleDateFormat("yyyy-mm-dd").parse(str);
    			 time = sdf.format(dt);
    			System.out.println(time);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        }		
		return time;
	}
	

	/**
	 * @return the hEALTH_ID
	 */
	public String getHealth_id(String str) {
		
		return null;
	}
	/**
	 * @return the pGRESIDE
	 */
	public String getPgreside(String str) {
		return null;
	}
	/**
	 * @return the cHILD_CHARACTER
	 */
	public String getChild_character(String str) {
		return null;
	}
	/**
	 * @return the fATHER_OCCUPATION
	 */
	public String getFather_occupation(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
		if(str.equals("1")){
			result="3300" ;
		}else if(str.equals("2")){
			result="3100" ;
		}else if(str.equals("3")){
			result="2800" ;
		}else if(str.equals("4")){
			result="3600" ;
		}else if(str.equals("5")){
			result="1700" ;
		}else if(str.equals("6")){
			result="5000" ;
		}else if(str.equals("7")){
			result="5001" ;
		}else if(str.equals("8")){
			result="3900" ;
		}
		
		return result;
	}
	/**
	 * @return the fATHER_EDUCTION
	 */
	public String getFather_eduction(String str) {
		return null;
	}
	/**
	 * @return the mOTHER_OCCUPATION
	 */
	public String getMother_occupation(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
		if(str.equals("1")){
			result="3300" ;
		}else if(str.equals("2")){
			result="3100" ;
		}else if(str.equals("3")){
			result="2800" ;
		}else if(str.equals("4")){
			result="3600" ;
		}else if(str.equals("5")){
			result="1700" ;
		}else if(str.equals("6")){
			result="5000" ;
		}else if(str.equals("7")){
			result="5001" ;
		}else if(str.equals("8")){
			result="3900" ;
		}
		return result;
	}
	/**
	 * @return the mOTHER_EDUCTION
	 */
	public String getMother_eduction(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
		if(str.equals("1")){
			result="90" ;
		}else if(str.equals("2")){
			result="80" ;
		}else if(str.equals("3")){
			result="70" ;
		}else if(str.equals("4")){
			result="60" ;
		}else if(str.equals("5")){
			result="50" ;
		}else if(str.equals("6")){
			result="40" ;
		}else if(str.equals("7")){
			result="30" ;
		}else if(str.equals("8")){
			result="20" ;
		}else if(str.equals("9")){
			result="10" ;
		}
		
		return result;
	}
	/**
	 * @return the dISEASE_FAMILY
	 */
	public String getDisease_family(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
		if(str.equals("0")){
			result="0" ;
		}else if(str.equals("1")){
			result="1" ;
		}
		
		return result;
	}
	/**
	 * @return the lITTERS
	 */
	public String getLitters(String str) {
		String result =null;
		if(str==null){
			return null;
		}else
		if(str.equals("1")){
			result="1" ;
		}else if(str.equals("2")){
			result="2" ;
		}else if(str.equals("3")){
			result="3" ;
		}
		
		return result;
	}
	/**
	 * @return the cHILDBIRTH
	 */
	public String getChildbirth(String str) {
		if(str==null){
			return null;
		}else
		return str;
	}
	/**
	 * @return the aPGAR_SCORE1
	 */
	public String getApgar_score1(String str) {
		if(str==null){
			return null;
		}else
		return str;
	}
	/**
	 * @return the aPGAR_SCORE5
	 */
	public String getApgar_score5(String str) {
		if(str==null){
			return null;
		}else
		return str;
	}
	/**
	 * @return the bIRTH_DEFECTS
	 */
	public String getBirth_defects(String str) {
		if(str==null){
			return null;
		}else
		return str;
	}
	/**
	 * @return the cOMPLICATIONS
	 */
	public String getComplications(String str) {
		String result =null;
		if(str!=null){
			if(str.contains("/")){
				result=	str.replace("/", "|");
				
			}
			
		}
		return result;
	}
	/**
	 * @return the nEONATAL_SCREENING
	 */
	public String getNeonatal_screening(String str) {
		if(str==null){
			return null;
		}else
		return str;
	}
	/**
	 * @return the hEARING_SCREENING
	 */
	public String getHearing_screening(String str) {
		return null;
	}
	/**
	 * @return the cREATE_TIME_TYPE
	 */
	public String getCreate_time_type(String str) {
		return null;
	}
	/**
	 * @return the cREATE_EMP_ID
	 */
	public String getCreate_emp_id(String str) {
		return null;
	}
	/**
	 * @return the cREATE_ORG_CODE
	 */
	public String getCreate_org_code(String str) {
		return null;
	}
	/**
	 * @return the mONITORING_CHANGE
	 */
	public String getMonitoring_change(String str) {
		return str;
	}
	/**
	 * @return the cLOSECASE
	 */
	public String getClosecase(String str) {
		return str;
	}
	
	

	 public static void main(String[] args) throws IOException{
		 Eb_bj_ybqk childCare = new Eb_bj_ybqk();
		Map<String, String> result = childCare.createxml("2012-03-20 00:00:00.000","2012-03-31 00:00:00.000");
		  Iterator it = result.entrySet().iterator();
		   while (it.hasNext()) {
		    Map.Entry entry = (Map.Entry) it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key=" + key + " value=" + value);
		   }
		
	 }

}
