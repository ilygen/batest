package tw.gov.bli.ba.payment.webservice;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import tw.gov.bli.ba.payment.cases.PaymentDetailCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceProcessCase;
import tw.gov.bli.ba.services.PaymentService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.HttpHelper;

/**
 * webService for 繳款單回寫狀態
 * 
 * @author Zehua
 */
public class PaymentStatusWebserviceImpl implements PaymentStatusWebservice {

	private static Log log = LogFactory.getLog(PaymentCashWebserviceImpl.class);

	private PaymentService paymentService;

	/**
	 * doApply webservice進入點
	 * 
	 * @return
	 */
	public String doApply(String xmlString) {
		try {
			HttpServletRequest request = HttpHelper.getHttpRequest();
			PaymentWebserviceProcessCase caseData = new PaymentWebserviceProcessCase();
			if (StringUtils.isBlank(xmlString)) {
				return null;
			} else {
				caseData = parseRequest(xmlString);
			}
			if (caseData == null) {
				return null;
			}
			paymentService.updatePaymentRemoteData(caseData);// update
																// 繳款單到pf系統後的回傳狀態
			String resXml = generateResXml();

			return resXml;

		} catch (Exception e) {
			log.error("PaymentCashWebserviceImpl 發生錯誤 - " + ExceptionUtility.getStackTrace(e));
			return null;
		}

	}

	/**
	 * 組合回傳xml
	 * 
	 * @return
	 */
	public String generateResXml() {
		try {
			SAXBuilder bSAX = new SAXBuilder(false);
			Element ElmtRoot = new Element("Root");

			Document returnDocJDOM = new Document(ElmtRoot);
			Element returnElmtRoot = new Element("Data");
			// ----------Header----------
			// Element data = new Element("Data");
			returnElmtRoot.addContent(new Element("MSG").addContent(""));
			returnElmtRoot.addContent(new Element("STATUS").addContent("SUCCESS"));

			// returnElmtRoot.addContent(data);
			ElmtRoot.addContent(returnElmtRoot);
			XMLOutputter fmt = new XMLOutputter(Format.getCompactFormat());
			StringWriter fwXML = new StringWriter();
			fmt.output(returnDocJDOM, fwXML);
			fwXML.close();
			String resXml = fwXML.toString().replace("\r", "").replace("\n", "");

			log.info("PaymentStatusWebservice 回傳的 XML 字串為: " + resXml);
			return resXml;
		} catch (Exception e) {
			log.error("PaymentStatusWebservice 設定回傳XML時發生錯誤：" + ExceptionUtility.getStackTrace(e));
			return null;
		}
	}

	/**
	 * parse 傳入的xml
	 * 
	 * @return
	 */
	public PaymentWebserviceProcessCase parseRequest(String xmlString) {
		try {
			HttpServletRequest request = HttpHelper.getHttpRequest();
			PaymentWebserviceProcessCase reqCase = new PaymentWebserviceProcessCase();

			SAXBuilder bSAX = new SAXBuilder(false);
			StringReader xmlReader = new StringReader(xmlString);

			log.debug(xmlReader.toString());
			Document docJDOM = bSAX.build(xmlReader);
			Element elmtRoot = docJDOM.getRootElement();

			Element qryData = elmtRoot.getChild("QRYDATA");

			String barCode1 = qryData.getChildText("BARCODE1");
			String barCode2 = qryData.getChildText("BARCODE2");
			String barCode3 = qryData.getChildText("BARCODE3");
			String inskd = qryData.getChildText("INSKD");
			String bliAccountCode = qryData.getChildText("BLI_ACCOUNT_CODE");
			String bookEdBook = qryData.getChildText("BOOKEDBOOK");
			String edate = qryData.getChildText("EDATE"); // 交易序號
			String bkAccountDt = qryData.getChildText("BKACCOUNTDT");
			String batchNo = qryData.getChildText("BATCHNO");
			String serialNo = qryData.getChildText("SERIALNO");
			String amt = qryData.getChildText("CASH_AMT");
			String sitDte = qryData.getChildText("SITDTE");
			String indexNo = qryData.getChildText("INDEX_NO");
			String msg = qryData.getChildText("MSG");
			String status = qryData.getChildText("STATUS");

			if (StringUtils.isNotBlank(amt)) {
				reqCase.setAmt(BigDecimal.valueOf(Long.parseLong(amt)));
			} else {
				reqCase.setAmt(BigDecimal.ZERO);
			}

			reqCase.setInsKd(inskd);
			reqCase.setBliAccountCode(bliAccountCode);
			reqCase.setBookEdBook(bookEdBook);
			reqCase.setBkAccountDt(bkAccountDt);
			reqCase.setBatchNo(batchNo);
			reqCase.setSerialNo(serialNo);
			reqCase.setIndexNo(indexNo);
			reqCase.setMsg(msg);
			reqCase.setStatus(status);
			reqCase.setEdate(edate);
			reqCase.setSitDte(sitDte);
			reqCase.setBarCode1(barCode1);
			reqCase.setBarCode2(barCode2);
			reqCase.setBarCode3(barCode3);
			return reqCase;

		} catch (Exception e) {
			log.error("PaymentCashWebServiceImpl 發生錯誤  - " + ExceptionUtility.getStackTrace(e));
			return null;
		}
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
}
