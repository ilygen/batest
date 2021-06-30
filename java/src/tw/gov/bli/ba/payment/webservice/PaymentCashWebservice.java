package tw.gov.bli.ba.payment.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface PaymentCashWebservice {
	public String doApply(@WebParam(name="xmlString") String xmlString);
}
