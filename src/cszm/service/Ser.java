package cszm.service;

import org.apache.log4j.Logger;
import org.tempuri.JK_XZClient;
import org.tempuri.JK_XZSoap;

import util.DEncrypt;


public class Ser {
	
	private static Logger logger = Logger.getLogger(Ser.class);
	
	/**
	 * 上传地址
	 */
	//private static final String URL = "http://csz.zjwst.gov.cn:8574/WebSer/JK_XZ.asmx"; //公网
	private static final String URL = "http://fb.nbws.gov.cn:8574/WebSer/JK_XZ.asmx";	//卫生局
	private static final String ID = "NB_Admin";
	private static final String CODE = "NB_Admin";
	
	private static String verifyXml  = "<DATAPACKET><ROWDATA><row s_uid='" + DEncrypt.getInstance().getEncrypt(ID) + "' s_code='" + DEncrypt.getInstance().getEncrypt(CODE) + "' /></ROWDATA></DATAPACKET>";
	private static JK_XZSoap jk = new JK_XZClient().getJK_XZSoap(URL);
	
	public static String getInfo() {
		String ret = null;
		try {
			ret = jk.getInfo(verifyXml);
			ret = DEncrypt.getInstance().getDncrypt(ret);
			ret=java.net.URLDecoder.decode(ret,"utf-8");
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
	public static String setInfo(String cszh) {
		String cszhXml = "<DATAPACKET><ROWDATA><row v_cszh='" +  DEncrypt.getInstance().getEncrypt(cszh) + "' /></ROWDATA></DATAPACKET>"; 
		String ret = null;
		try{
			ret = jk.setInfo(verifyXml, cszhXml);
		} catch (Exception e) {
			logger.error("setInfo error:" + e.getMessage());
			e.printStackTrace();
		}  
		return ret; 
	}
	
	
	public static void main(String[] args) {
//		logger.info(getInfo());
		System.out.println(getInfo());
//		logger.info(setInfo("O330016020"));
		System.out.println(verifyXml);
	}

	
}
