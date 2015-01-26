
package bussiness.bbb;

import javax.jws.WebService;

@WebService(serviceName = "ReconciliationService", targetNamespace = "http://tempuri.org/", endpointInterface = "bbb.ReconciliationServiceSoap")
public class ReconciliationServiceImpl
    implements ReconciliationServiceSoap
{


    public String process(String para) {
        throw new UnsupportedOperationException();
    }

}
