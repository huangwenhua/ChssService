package dictall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class GetXinZheng {

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
		String XmlString = "<body><head><userid>test</userid><password>123</password><trans_no>D07.00.00.00</trans_no></head><resquest><D07.00.00.00><BEG_TIME></BEG_TIME><END_TIME></END_TIME><PAGE_INDEX>0</PAGE_INDEX><PAGE_SIZE>10</PAGE_SIZE></D07.00.00.00></resquest></body>";
		String v = (String) call.invoke(new Object[] { XmlString });// 调用方法并传递参数
		// System.out.println(v);
		File file = new File("C:\\xinzheng.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
	
		out.write(v);
		out.newLine();
		out.close();
		out = null;
		file = null;
	}

}