
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "JK_XZSoap", targetNamespace = "http://tempuri.org/")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface JK_XZSoap {


    @WebMethod(operationName = "SetInfo", action = "http://tempuri.org/SetInfo")
    @WebResult(name = "SetInfoResult", targetNamespace = "http://tempuri.org/")
    public String setInfo(
        @WebParam(name = "XmlText1", targetNamespace = "http://tempuri.org/")
        String XmlText1,
        @WebParam(name = "XmlText2", targetNamespace = "http://tempuri.org/")
        String XmlText2);

    @WebMethod(operationName = "GetInfo", action = "http://tempuri.org/GetInfo")
    @WebResult(name = "GetInfoResult", targetNamespace = "http://tempuri.org/")
    public String getInfo(
        @WebParam(name = "XmlText", targetNamespace = "http://tempuri.org/")
        String XmlText);

}
