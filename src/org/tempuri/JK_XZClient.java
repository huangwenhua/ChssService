
package org.tempuri;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class JK_XZClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service1;

    public JK_XZClient() {
        create1();
        Endpoint JK_XZSoapLocalEndpointEP = service1 .addEndpoint(new QName("http://tempuri.org/", "JK_XZSoapLocalEndpoint"), new QName("http://tempuri.org/", "JK_XZSoapLocalBinding"), "xfire.local://JK_XZ");
        endpoints.put(new QName("http://tempuri.org/", "JK_XZSoapLocalEndpoint"), JK_XZSoapLocalEndpointEP);
        Endpoint JK_XZSoapEP = service1 .addEndpoint(new QName("http://tempuri.org/", "JK_XZSoap"), new QName("http://tempuri.org/", "JK_XZSoap"), "http://csz.zjwst.gov.cn/WebSer/JK_XZ.asmx");
        endpoints.put(new QName("http://tempuri.org/", "JK_XZSoap"), JK_XZSoapEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create1() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service1 = asf.create((org.tempuri.JK_XZSoap.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service1, new QName("http://tempuri.org/", "JK_XZSoapLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service1, new QName("http://tempuri.org/", "JK_XZSoap"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public JK_XZSoap getJK_XZSoapLocalEndpoint() {
        return ((JK_XZSoap)(this).getEndpoint(new QName("http://tempuri.org/", "JK_XZSoapLocalEndpoint")));
    }

    public JK_XZSoap getJK_XZSoapLocalEndpoint(String url) {
        JK_XZSoap var = getJK_XZSoapLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public JK_XZSoap getJK_XZSoap() {
        return ((JK_XZSoap)(this).getEndpoint(new QName("http://tempuri.org/", "JK_XZSoap")));
    }

    public JK_XZSoap getJK_XZSoap(String url) {
        JK_XZSoap var = getJK_XZSoap();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
