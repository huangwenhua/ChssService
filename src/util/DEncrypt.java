package util;

import java.net.URL;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;

public class DEncrypt {
	
	private static DEncrypt single = new DEncrypt();
	
	private static Client client;
	
	private DEncrypt(){
		try {
			 client = new Client(new  URL("http://192.168.60.3/MediinfoDEncrypt/WebService1.asmx?wsdl"));
		} catch (Exception e) {
			logger.error("DEncrypt error:" + e.getMessage());
			e.printStackTrace();
		}  
	}
	
	public synchronized static DEncrypt getInstance(){
		return single;
	}
	
	public String getEncrypt(String str) {
		Object[] results={};
		try {
			results = client.invoke("EncryptCode",  new String[]{str});
		} catch (Exception e) {
			logger.error("getEncrypt error:" + e.getMessage());
			e.printStackTrace();
		} 
		return results[0].toString();		
	}
	
	public String getDncrypt(String str) {
		Object[] results={};
		try {
			results = client.invoke("DecryptCode",  new String[]{str});
		} catch (Exception e) {
			logger.error("getDncrypt error:" + e.getMessage());
			e.printStackTrace();
		} 
		return results[0].toString();		
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(DEncrypt.getInstance().getEncrypt("1"));
		System.out.println(DEncrypt.getInstance().getDncrypt("6XEvYAk4jIM="));
	}
	
	private static Logger logger = Logger.getLogger(DEncrypt.class);
}
