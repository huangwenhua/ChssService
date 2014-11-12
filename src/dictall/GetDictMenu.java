package dictall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import util.XMLDocDictMenu;

public class GetDictMenu {

	public static void main(String[] args) throws Exception {
		Service service = new Service();
		String url = "http://192.168.75.79:701/csbBusService.asmx";
		// 在浏览器中打开url，可以找到SOAPAction: "http://www.chinsoft.com.cn/SendMQ"
		String namespace = "http://tempuri.org/";
		String actionUri = "Process"; // Action路径
		String op = "Process"; // 要调用的方法名
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(namespace + actionUri); // action uri
		call.setOperationName(new QName(namespace, op));// 设置要调用哪个方法

		call.addParameter(
				new QName(namespace, "XmlString"), // 设置要传递的参数
				org.apache.axis.encoding.XMLType.XSD_STRING,
				javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); // 要返回的数据类型

		// 设置参数名称，具体参照从浏览器中看到的
		String XmlString = "<body><head><userid>test</userid><password>123</password><trans_no>D00.00.00.02</trans_no></head><resquest><D00.00.00.02><BEG_TIME></BEG_TIME><END_TIME></END_TIME><PAGE_INDEX></PAGE_INDEX><PAGE_SIZE></PAGE_SIZE></D00.00.00.02></resquest></body>";
		String v = (String) call.invoke(new Object[] { XmlString });// 调用方法并传递参数
		// System.out.println(v);
		XMLDocDictMenu doc = new XMLDocDictMenu();
		String StrAll = doc.readStringXml(v);
	//	System.out.println(StrAll);
        String[] aaa = StrAll.split("\\|");
        File file = new File("C:\\test1.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
      //System.out.println(aaa.length);//662
		for (int i = 403; i <=662; i++) {
			System.out.println(i);
			String[] bb=aaa[i].split(",");
			for (int j=0;j<bb.length;j++){
				if(j==0){
			//	System.out.println();
				String cc="<body><head><userid>test</userid><password>123</password><trans_no>D00.00.00.03</trans_no></head><resquest><DIC_TYPE>"+bb[j]+"</DIC_TYPE></resquest></body>";
				String vv = (String) call.invoke(new Object[] { cc });// 调用方法并传递参数
			    out.write(vv);
	            out.newLine();
				}	
			}
		}
		 out.close();
	     out = null;
	     file = null;
	}

}