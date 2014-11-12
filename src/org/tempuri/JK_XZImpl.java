
package org.tempuri;

import javax.jws.WebService;

@WebService(serviceName = "JK_XZ", targetNamespace = "http://tempuri.org/", endpointInterface = "org.tempuri.JK_XZSoap")
public class JK_XZImpl
    implements JK_XZSoap
{


    public String setInfo(String XmlText1, String XmlText2) {
        throw new UnsupportedOperationException();
    }

    public String getInfo(String XmlText) {
        throw new UnsupportedOperationException();
    }

}
