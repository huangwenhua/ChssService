
package bussiness.bbb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "ReconciliationServiceSoap", targetNamespace = "http://tempuri.org/")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ReconciliationServiceSoap {


    @WebMethod(operationName = "Process", action = "http://tempuri.org/Process")
    @WebResult(name = "ProcessResult", targetNamespace = "http://tempuri.org/")
    public String process(
        @WebParam(name = "para", targetNamespace = "http://tempuri.org/")
        String para);

}
