package tw.gov.bli.ba.webservices;

public class SingleCheckMarkServicePortTypeProxy implements tw.gov.bli.ba.webservices.SingleCheckMarkServicePortType {
  private String _endpoint = null;
  private tw.gov.bli.ba.webservices.SingleCheckMarkServicePortType singleCheckMarkServicePortType = null;
  
  public SingleCheckMarkServicePortTypeProxy() {
    _initSingleCheckMarkServicePortTypeProxy();
  }
  
  public SingleCheckMarkServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initSingleCheckMarkServicePortTypeProxy();
  }
  
  private void _initSingleCheckMarkServicePortTypeProxy() {
    try {
      singleCheckMarkServicePortType = (new tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator()).getSingleCheckMarkServiceHttpPort();
      if (singleCheckMarkServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)singleCheckMarkServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)singleCheckMarkServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (singleCheckMarkServicePortType != null)
      ((javax.xml.rpc.Stub)singleCheckMarkServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public tw.gov.bli.ba.webservices.SingleCheckMarkServicePortType getSingleCheckMarkServicePortType() {
    if (singleCheckMarkServicePortType == null)
      _initSingleCheckMarkServicePortTypeProxy();
    return singleCheckMarkServicePortType;
  }
  
  public java.lang.String doCheckMark(java.lang.String in0,java.lang.String in1) throws java.rmi.RemoteException{
    if (singleCheckMarkServicePortType == null)
      _initSingleCheckMarkServicePortTypeProxy();
    return singleCheckMarkServicePortType.doCheckMark(in0,in1);
  }
  
  
}