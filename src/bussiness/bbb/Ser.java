package bussiness.bbb;

import org.apache.log4j.Logger;
import org.tempuri.JK_XZClient;
import org.tempuri.JK_XZSoap;

import util.DEncrypt;
import bussiness.bbb.*;

public class Ser {
	
	private static Logger logger = Logger.getLogger(Ser.class);
	
	/**
	 * 上传地址
	 */

	private static final String URL = "http://192.168.75.79:3031/ReconciliationService.asmx";	//卫生局


	
	private static String verifyXml  = "<body><head><userid>nb_fybj</userid><password>123</password><trans_no>Z00.00.00.09</trans_no></head><resquest><ORG_CODE>41952989</ORG_CODE><SMONTHDAY>20141201</SMONTHDAY><CHILD_BASEINFO_COUNT>46</CHILD_BASEINFO_COUNT><CHILD_NEWBORN_COUNT>43</CHILD_NEWBORN_COUNT><PG_NEWBORN_COUNT>248</PG_NEWBORN_COUNT><CHILD_CHECK_COUNT>269</CHILD_CHECK_COUNT><CHILD_YEARS_COUNT>23</CHILD_YEARS_COUNT></resquest></body>";
	
	private static ReconciliationServiceSoap jk = new ReconciliationServiceClient().getReconciliationServiceSoap(URL);
	
	public static String getInfo() {
		String ret = null;
		try {
			ret = jk.process(verifyXml);


		} catch (Exception e) {
			logger.error("getInfo error:" + e.getMessage());
			e.printStackTrace();
		}  
		return ret;
	}

	/**
	 * 
	 * @param cszh
	 * @return "s_ok"
	 */


	
	public static void main(String[] args) {
//		logger.info(getInfo());
		System.out.println(getInfo());
//		logger.info(setInfo("O330016020"));
	}

	
}
