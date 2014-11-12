package util;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class TestClient {

	public static void main(String[] args) throws Exception {
		Service service = new Service();
		String url = "http://192.168.75.79:701/csbBusService.asmx";
		//在浏览器中打开url，可以找到SOAPAction: "http://www.chinsoft.com.cn/SendMQ"
		String namespace = "http://tempuri.org/";
		String actionUri = "Process"; //Action路径
		String op = "Process"; //要调用的方法名
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(namespace + actionUri); // action uri
		call.setOperationName(new QName(namespace, op));// 设置要调用哪个方法
		
		call.addParameter(new QName(namespace,"XmlString"), //设置要传递的参数  
                org.apache.axis.encoding.XMLType.XSD_STRING,  
               javax.xml.rpc.ParameterMode.IN);  
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); // 要返回的数据类型
		   
		// 设置参数名称，具体参照从浏览器中看到的
		String XmlString="<body><head><userid>test</userid><password>123</password><trans_no>D00.00.00.03</trans_no></head><resquest><DIC_TYPE>NBB02.00.600</DIC_TYPE></resquest></body>";
		String v=(String)call.invoke(new Object[]{XmlString});//调用方法并传递参数    
	    DuXMLDoc doc=new DuXMLDoc();
	    doc.readStringXml(v);
                   
	}

}