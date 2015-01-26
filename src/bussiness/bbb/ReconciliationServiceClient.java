
package bussiness.bbb;

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

public class ReconciliationServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service1;

    public ReconciliationServiceClient() {
        create1();
        Endpoint ReconciliationServiceSoapEP = service1 .addEndpoint(new QName("http://tempuri.org/", "ReconciliationServiceSoap"), new QName("http://tempuri.org/", "ReconciliationServiceSoap"), "http://192.168.75.79:3031/ReconciliationService.asmx");
        endpoints.put(new QName("http://tempuri.org/", "ReconciliationServiceSoap"), ReconciliationServiceSoapEP);
        Endpoint ReconciliationServiceSoapLocalEndpointEP = service1 .addEndpoint(new QName("http://tempuri.org/", "ReconciliationServiceSoapLocalEndpoint"), new QName("http://tempuri.org/", "ReconciliationServiceSoapLocalBinding"), "xfire.local://ReconciliationService");
        endpoints.put(new QName("http://tempuri.org/", "ReconciliationServiceSoapLocalEndpoint"), ReconciliationServiceSoapLocalEndpointEP);
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
        service1 = asf.create((bussiness.bbb.ReconciliationServiceSoap.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service1, new QName("http://tempuri.org/", "ReconciliationServiceSoap"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service1, new QName("http://tempuri.org/", "ReconciliationServiceSoapLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public ReconciliationServiceSoap getReconciliationServiceSoap() {
        return ((ReconciliationServiceSoap)(this).getEndpoint(new QName("http://tempuri.org/", "ReconciliationServiceSoap")));
    }

    public ReconciliationServiceSoap getReconciliationServiceSoap(String url) {
        ReconciliationServiceSoap var = getReconciliationServiceSoap();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ReconciliationServiceSoap getReconciliationServiceSoapLocalEndpoint() {
        return ((ReconciliationServiceSoap)(this).getEndpoint(new QName("http://tempuri.org/", "ReconciliationServiceSoapLocalEndpoint")));
    }

    public ReconciliationServiceSoap getReconciliationServiceSoapLocalEndpoint(String url) {
        ReconciliationServiceSoap var = getReconciliationServiceSoapLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
