package quartz;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.quartz.JobExecutionException;

import bill.Tretgl;
import Client.Entrance;
import Client.ParseXmlUtil;
import Client.Result;
import Interface.BuildingXml;
import sys.CommonUtil;
import util.DateUtil;
import util.EhcacheUtil;
import coon.JdbcPool;
import dictall.DictMap;

public class Test {

	public static void main(String[] args) throws Exception {
//		Test.readxml();
//		Test.test();
//		Test.cszmCache();
//		Test.cszm();
//		Test.Bill();
//		Test.bill();
//		Test.soap();
//		Test.time();
//		Test.count();
//		Test.con();
//		Test.webservice();
		Test.spltime("2012-01-01 00:00:00", "2013-2-15 00:00:00");
//		Test.daysBetween("2012-01-01 00:00:00", "2013-2-15 00:00:00");
//		Test.com("2009-01-01 00:00:00","2011-12-5 23:59:59");
//		Test.nowDate();
//		Test.getBeforeDay();
	}
	
	public static void readxml(){
		SAXReader reader = new SAXReader();         
		try {
//			Document doc = reader.read(new File("D:/workspace/ChssService/src/cszm_body.xml"));
			Document doc = reader.read(new File("src/cszm_body.xml"));
			System.out.println("aa----");
			System.out.println(System.getProperty("user.dir"));
			System.out.println(Thread.currentThread().getContextClassLoader().getResource(".").getPath());
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test(){
		String strDate = "2003/1/4";
		strDate = strDate.replace("/", "-");
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			String newDate = new SimpleDateFormat("yyyyMMdd").format(date);
			System.out.print(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void count() throws SQLException{
		String[] list = { "Da_gr_hxda"  ,
                "Eb_bj_tjjl"  ,
               "Eb_bj_xsefs" ,
                "Eb_bj_ybqk"  ,
                "Eb_bj_zsjkxj" , 
                "Eb_tre_fpsf"  ,
                "Eb_tre_fpza"  ,
                "Eb_tre_glbsf"  ,
                "Eb_tre_glbza"  ,
                "Eb_tre_pxsf"  ,
                "Eb_tre_pxza"  ,
                "Eb_tre_qtza"  ,
                "Eb_tre_xtxxzbza" , 
                "Eb_tre_xzbsf"  ,
                "Eb_tre_yyblsf"  ,
                "Eb_tre_yyblza"  ,
                "Fb_ycf_cccj"  ,
                "Fb_ycf_chfs"  ,
                "Fb_Ycf_Chjc"  ,
                "Fb_ycf_cqjc"  ,
               "Fb_ycf_cqsc"  ,
                "Fb_ycf_csqk"  ,
                "Fb_ycf_xsecsqk" , 
                "Fb_ycf_xsefs"  ,
                "Fb_ycf_ykjc"  };


		Connection con = JdbcPool.getConnection();
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		for(String table:list){
			sql = "select count(*) from " + table + " where XGRQ>'2012-01-01 00:00:00' AND XGRQ<'2012-12-15 00:00:00'";
			pspm = con.prepareStatement(sql);
			rs = pspm.executeQuery();
			while(rs.next()){
				System.out.println("[" + table + "]:" + rs.getString(""));
				count += Integer.valueOf(rs.getString(""));
			}	
		}
		System.out.println("count:" + count);
		rs.close();
		pspm.close();
		con.close();

	}
	
	public static Connection con(){
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url="jdbc:sqlserver://localhost:1433;databaseName=Chss_New";
		String user = "sa";//数据库登录用户名
		String password = "lei";//数据库登录密码
		String message = "恭喜，数据库连接正常！";
		try {
			Class.forName(driverName);
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println(message);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
            message = "数据库连接失败！";
            System.out.println(message);
            return null;
        }

	}
	
	public static void webservice() throws JobExecutionException{
		EhcacheUtil.getInstance().initCache(); // 初始化ehcache缓存
		Entrance entran = new Entrance("2014-09-14 00:00:00", "2014-9-15 00:00:00");
		entran.doInfo();
	}
	
	public static void spltime(String startTime, String endTime) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
		Calendar calendar = Calendar.getInstance(); // 得到日历
		
		java.util.Date startDD = sdf.parse(startTime);  
		calendar.setTime(startDD);// 把当前时间赋给日历
		int startYY = calendar.get(Calendar.YEAR);
		int startMM = calendar.get(Calendar.MONTH);
		
		java.util.Date endDD = sdf.parse(endTime);  
		calendar.setTime(endDD);
		int endYY = calendar.get(Calendar.YEAR);
		int endMM = calendar.get(Calendar.MONTH);

		if(startYY == endYY){
			for(int m=startMM; m<endMM; m++){
				
			}
		}else if(startYY < endYY){

		}
		
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param date1  
     * @param date2  
     * @return  
	 * @throws ParseException 
     */  
    public static int daysBetween(String startTime, String endTime) throws ParseException   
    {   
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date startDD = sdf.parse(startTime);  
    	java.util.Date endDD = sdf.parse(endTime);  
    	
        Calendar cal = Calendar.getInstance();   
        cal.setTime(startDD);   
        long time1 = cal.getTimeInMillis();                
        cal.setTime(endDD);   
        long time2 = cal.getTimeInMillis();        
        long between_days=(time2-time1)/(1000*3600*24);   
         
        int res = Integer.parseInt(String.valueOf(between_days));
        System.out.println(res);
        return res;
    }  
    
    public static void com(String startTime, String endTime) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	java.util.Date startDD = sdf.parse(startTime);  
    	java.util.Date endDD = sdf.parse(endTime);  
    	
        Calendar cal = Calendar.getInstance();   
        while(startDD.before(endDD)){
            cal.setTime(startDD);   
            String _startTime = sdf.format(cal.getTime());
            
            cal.add(Calendar.MONTH, 1); 
            startDD = cal.getTime();
            String _endTime;
            if(startDD.before(endDD))
            	_endTime = sdf.format(startDD);
            else 
            	_endTime = sdf.format(endDD);

        	System.out.println(_startTime + ", " +_endTime);
        }
    }

    public static void time(){
//		Date dNow = new Date(); // 当前时间
//		Calendar calendar = Calendar.getInstance(); // 得到日历
//		calendar.setTime(dNow);// 把当前时间赋给日历
//
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
//		System.out.println(new SimpleDateFormat("yyyyMMdd").format(new Date()));
//		
		System.out.println(DateUtil.nowDT15());
		
		
		System.out.println(CommonUtil.getDate(new Date(), "yyyyMMdd'T'HHmmss"));
    }
    
    public static void soap(){	
		Service service = new Service();
		String url = "http://192.168.75.79:3031/ReconciliationService.asmx?wsdl";//对账测试
		// 在浏览器中打开url，可以找到SOAPAction: "http://www.chinsoft.com.cn/SendMQ"
		String namespace = "http://tempuri.org/";
		String actionUri = "Process"; // Action路径
		String op = "Process"; // 要调用的方法名
		Call call;
		try{
			call= (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + actionUri); // action uri
			call.setOperationName(new QName(namespace, op));// 设置要调用哪个方法
	
			call.addParameter(
					new QName(namespace, "XmlString"), // 设置要传递的参数
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); // 要返回的数据类型
			
			String xmlString = bill();
			System.out.println(xmlString);
			String v = (String) call.invoke(new Object[] { xmlString });// 调用方法并传递参数
			
			System.out.println(v);
			String retxml = v;
			if (null == retxml)
				return;
			Map<String, String> map = ParseXmlUtil.parsexml(retxml);
			String retCode = map.get("retCode");
			if ("0".equals(retCode)) {
				System.out.println("OK");
			} else {
				System.out.println("ERROR");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void Bill(){
    	Entrance en = new Entrance("2014-05-22 00:00:00", "2014-05-22 23:59:59");
    	en.doBill();
    }
    
    public static String bill(){
    	String xml = new Tretgl().createxml("20140515");
    	System.out.println(xml);
    	return xml;
    }
    
    
    /**
     * 单独上传表
     */
	public static void doIncrement() {
		logger.info("bgeing......................................");
		EhcacheUtil.getInstance().initCache(); // 初始化ehcache缓存
		
		Result result = new Result();
		
		List<String> list = new ArrayList();
		list.add("Eb_bj_tjjl");
		list.add("Fb_ycf_chfs");
		list.add("Fb_ycf_chjc");
		list.add("Fb_ycf_cqsc");
		list.add("Fb_ycf_csqk");
		
//		List<String> list = findTable();
		
		BuildingXml bx = null;
		String key = "";
		String value = "";
		Map<String, String> map = null;
		for (String tbName : list) {
			try {
				bx = (BuildingXml) Class.forName("impl." + tbName).newInstance();
				map = bx.createxml("2014-6-18 00:00:00","2014-6-18 23:59:59");

				for (Entry<String, String> entry : map.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					result.doResult(value, tbName, key);
				}

			} catch (Exception e) {
				logger.error("translate class error tbname = " + tbName);
				e.printStackTrace();
			}
		}
		logger.info("end......................................");
	}
	
	// 取得所有的表名
	private static  List<String> findTable() {
		List<String> list = new ArrayList<String>();
		String sql = "select tbName from t_table";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		conn = JdbcPool.getConnection();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("tbName"));
			}
		} catch (SQLException e) {
			logger.error("query t_table error :" + e.getMessage());
			e.printStackTrace();
		} finally {
			conn = null;
			pstm = null;
			rs = null;
		}
		return list;
	}
	
	private static void cszm() throws Exception{
		Connection con = getProxoolDataSource();
		PreparedStatement pspm = null;
		PreparedStatement pspm2 = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		/**
			select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name
			union 
			select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'居委会'  
			union 
			select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'委会' 
			union 
			select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'民委员会' 
			union 
			select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'村民委员会' 
			union 
			select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'社区居委会' 
		 */
		//sql = "select  lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name";
		//sql = "select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'居委会'";
		//sql = "select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'委会'";
		//sql = "select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'民委员会'";
		//sql = "select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'村民委员会'";
		sql = "select   lz.code code,lz.name name,jt.code code1,jt.name name1　　from  lz,jt where jt.name=lz.name+'社区居委会'";
		
		pspm = con.prepareStatement(sql);
		rs = pspm.executeQuery();
		while(rs.next()){
			String lzcode = rs.getString("code");
			String lzname = rs.getString("name");
			String jtcode = rs.getString("code1");
			String jtname = rs.getString("name1");
			
			String sql2 = "insert into T_CSZM_ZDBA values('" + lzcode + "','" + lzname + "','"+jtcode + "','" + jtname + "')";
//			pspm2 = con.prepareStatement(sql2);
//			pspm2.execute();
			execute(sql2);
		}	
System.out.println("------");
		rs.close();
		pspm.close();
		con.close();
	}
	
	private static Connection getProxoolDataSource() {
        ProxoolDataSource pds = new ProxoolDataSource();  
        pds.setAlias("sqlserver");
        pds.setMaximumActiveTime(36000000);
        pds.setMaximumConnectionCount(200);
        pds.setMinimumConnectionCount(5);
        pds.setUser("sa");
        pds.setPassword("lei"); 
        pds.setDriverUrl("jdbc:sqlserver://127.0.0.1:1433;databaseName=Chss_New");  
        //pds.setDriverUrl("jdbc:sqlserver://192.168.60.3\\\feyy:1081;databaseName=Chss_New");   // sa/3395
        //pds.setDriverUrl("jdbc:sqlserver://192.168.60.3\\\feyy:1081;databaseName=History_ChssDb");  //正式上传历史数据:  sa/3395
        //pds.setDriverUrl("jdbc:sqlserver://192.168.60.5\\feyy;databaseName=Chss_New");   //正式上传增量数据:   sa/admin3395@fby
        pds.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
        
    	try {
			return pds.getConnection();
		} catch (SQLException e) {
			logger.error("get connection error :" + e.getMessage());
			e.printStackTrace();
		}
    	return null;
 
    }
	
	public static void execute(String sql) {
		Connection conn = getProxoolDataSource();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error("execute sql error:" + e.getMessage());
			logger.error("error sql is :" + sql);
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}
	
	public static void close(Connection conn) {
		if (conn == null)
			return;
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Clost Connection error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void cszmCache(){
		EhcacheUtil.getInstance().initCache();
		String ret = "";
		try {
			 ret = DictMap.getCszm("654325100004");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		System.out.println(ret);
	}
	
	public static String nowDate(){
		 String date =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		 System.out.println(date);
		 return date;
	}
	
	public static String  getBeforeDay() {
		Date dNow = new Date(); // 当前时间
//		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		Date dBefore = calendar.getTime(); // 得到前一天的时间
		String s = new SimpleDateFormat("yyyy-MM-dd").format(dBefore);
		System.out.println(s);
		return s;
	}
	
	private static Logger logger = Logger.getLogger(Test.class);
}
