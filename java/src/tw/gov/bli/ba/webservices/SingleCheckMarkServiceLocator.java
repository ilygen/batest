/**
 * SingleCheckMarkServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package tw.gov.bli.ba.webservices;

import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.helper.PropertyHelper;

public class SingleCheckMarkServiceLocator extends org.apache.axis.client.Service implements tw.gov.bli.ba.webservices.SingleCheckMarkService {

    public SingleCheckMarkServiceLocator() {
    }

    public SingleCheckMarkServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SingleCheckMarkServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SingleCheckMarkServiceHttpPort
    // ResourceBundle res = ResourceBundle.getBundle("webServiceUrl");
    // private java.lang.String SingleCheckMarkServiceHttpPort_address = StringUtils.trimToEmpty(res.getString("checkMarkWebServicesUrl"));
    private java.lang.String SingleCheckMarkServiceHttpPort_address = StringUtils.trimToEmpty(PropertyHelper.getProperty("checkMarkWebServicesUrl"));

    public java.lang.String getSingleCheckMarkServiceHttpPortAddress() {
        return SingleCheckMarkServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SingleCheckMarkServiceHttpPortWSDDServiceName = "SingleCheckMarkServiceHttpPort";

    public java.lang.String getSingleCheckMarkServiceHttpPortWSDDServiceName() {
        return SingleCheckMarkServiceHttpPortWSDDServiceName;
    }

    public void setSingleCheckMarkServiceHttpPortWSDDServiceName(java.lang.String name) {
        SingleCheckMarkServiceHttpPortWSDDServiceName = name;
    }

    public tw.gov.bli.ba.webservices.SingleCheckMarkServicePortType getSingleCheckMarkServiceHttpPort() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SingleCheckMarkServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSingleCheckMarkServiceHttpPort(endpoint);
    }

    public tw.gov.bli.ba.webservices.SingleCheckMarkServicePortType getSingleCheckMarkServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub _stub = new tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getSingleCheckMarkServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSingleCheckMarkServiceHttpPortEndpointAddress(java.lang.String address) {
        SingleCheckMarkServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (tw.gov.bli.ba.webservices.SingleCheckMarkServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub _stub = new tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub(new java.net.URL(SingleCheckMarkServiceHttpPort_address), this);
                _stub.setPortName(getSingleCheckMarkServiceHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SingleCheckMarkServiceHttpPort".equals(inputPortName)) {
            return getSingleCheckMarkServiceHttpPort();
        }
        else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.cm.ba.bli.gov.tw", "SingleCheckMarkService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.cm.ba.bli.gov.tw", "SingleCheckMarkServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("SingleCheckMarkServiceHttpPort".equals(portName)) {
            setSingleCheckMarkServiceHttpPortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
