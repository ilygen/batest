package tw.gov.bli.ba.payment.webservice;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.payment.cases.PaymentDetailCase;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentReprintCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentUpdateCase;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceProcessCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.payment.rpt.PaymentReport;
import tw.gov.bli.ba.services.PaymentService;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * webservice for 繳款單
 * 
 * @author Zehua
 */
public class PaymentPrintWebserviceImpl implements PaymentPrintWebservice {
	private static Log log = LogFactory.getLog(PaymentPrintWebserviceImpl.class);
	private static String QRY_EMPTY = "查無資料";
	
	private static String STATUS_SUCCESS="0";
	private static String STATUS_FAIL="9";
	private PaymentService paymentService;
	private QueryService queryService;
	/**
	 * doApply 進入點
	 * 
	 * @return
	 */
	public String doApply(String xmlString) {
		try {
			log.info("PaymentCashWebservice 收到 XML 字串為: " + xmlString);
			PaymentReprintCase caseData = new PaymentReprintCase();
			if (StringUtils.isBlank(xmlString)) {
				return generateResXml(null, caseData, STATUS_FAIL, "傳入資料不得為空");
			} else {
				caseData = parseRequest(xmlString);// parse傳入參數
			}
			if (caseData == null) {
				return generateResXml(null, caseData, STATUS_FAIL, "傳入資料有誤");
			}
			String processData = processRequest(caseData);// 處裡邏輯
			if (processData == null) {
				return generateResXml(null, caseData, STATUS_FAIL, "產生報表發生錯誤");
			}else if(StringUtils.equals(processData, QRY_EMPTY)) {
				return generateResXml(null, caseData, STATUS_FAIL, QRY_EMPTY);
			}else {
				
			}
			String resXml = generateResXml(processData,caseData,STATUS_SUCCESS, "列印成功");
			return resXml;

		} catch (Exception e) {
			log.error("PaymentCashWebserviceImpl 發生錯誤 - " + ExceptionUtility.getStackTrace(e));
			return generateResXml(null, null, STATUS_FAIL, "產生報表發生錯誤");
		}
	}

	/**
	 * 取得回傳xml字串
	 * 
	 * @param statusCode
	 * @param statusInfo
	 * @param fcadvinspreId
	 * @param accMk
	 * @return
	 */
	public String generateResXml(String processData, PaymentReprintCase caseData,String statusCode, String statusInfo) {
		try {
			log.info("PaymentCashWebservice generateResXml");
			Element returnElmtRoot = new Element("BLIXML");

			Document returnDocJDOM = new Document(returnElmtRoot);
			Element secondElmtRoot = new Element("Header");
			Element trxRs = new Element("TrxRs");;
			Element status= new Element("Status");
			Element fileName = new Element("filename");
			Element prtPdf= new Element("prtPdf");
			
			if(caseData == null) {
				secondElmtRoot.addContent(new Element("CltAppSeq"));//現金金額
				secondElmtRoot.addContent(new Element("MsgId"));//待確認
				status.addContent(new Element("StatusCode").addContent(String.valueOf(statusCode)));
				status.addContent(new Element("StatusInfo").addContent(String.valueOf(statusInfo)));
				
			}else {
				// ----------Header----------
				secondElmtRoot.addContent(new Element("CltAppSeq").addContent(String.valueOf(caseData.getCltAppSeq())));//現金金額
				secondElmtRoot.addContent(new Element("MsgId").addContent(caseData.getMsgId()));//待確認
				status.addContent(new Element("StatusCode").addContent(String.valueOf(statusCode)));
				if(StringUtils.equals(statusCode, STATUS_SUCCESS)) {
					status.addContent(new Element("StatusInfo").addContent(String.valueOf("列印成功")));
					//PaymentReport_printDate.pdf
					fileName.addContent(String.valueOf("PaymentReport_"+caseData.getDte()));
					prtPdf.addContent(String.valueOf(processData));
					
				}else {
					status.addContent(new Element("StatusInfo").addContent(String.valueOf(statusInfo)));
				}
				
			}
			
			trxRs.addContent(status);
			trxRs.addContent(fileName);
			trxRs.addContent(prtPdf);
			returnElmtRoot.addContent(secondElmtRoot);
			returnElmtRoot.addContent(trxRs);
			
			XMLOutputter fmt = new XMLOutputter(Format.getCompactFormat());
			StringWriter fwXML = new StringWriter();
			fmt.output(returnDocJDOM, fwXML);
			fwXML.close();
			String resXml = fwXML.toString().replace("\r", "").replace("\n", "");

			log.info("PaymentPrintWebserviceImpl 回傳的 XML 字串為: " + resXml);
			return resXml;	
		} catch (Exception e) {
			log.error("PaymentPrintWebserviceImpl 設定回傳XML時發生錯誤：" + ExceptionUtility.getStackTrace(e));
			return null;
		}
	}

	/**
	 * parse XML
	 * 
	 * @return
	 */
	public PaymentReprintCase parseRequest(String xmlString) {
		try {
			log.info("PaymentCashWebservice parseRequest");
			PaymentReprintCase reqCase = new PaymentReprintCase();

			SAXBuilder bSAX = new SAXBuilder(false);
			StringReader xmlReader = new StringReader(xmlString);

			log.debug(xmlReader.toString());
			Document docJDOM = bSAX.build(xmlReader);
			Element elmtRoot = docJDOM.getRootElement();
			//Element blixml = elmtRoot.getChild("BLIXML");
			Element header = elmtRoot.getChild("Header");
	        String cltAppSeq = header.getChildText("CltAppSeq"); // 交易序號
	        String msgId = header.getChildText("MsgId"); // 交易代號(MEFCRWOAPPWS)
	        reqCase.setCltAppSeq(cltAppSeq);
	        reqCase.setMsgId(msgId);
			
	        Element trxReq = elmtRoot.getChild("TrxReq");
	        reqCase.setDte(DateUtility.changeDateType(StringUtils.trimToEmpty(trxReq.getChildText("Dte"))));// 開單日期
	        String seq = StringUtils.trimToEmpty(trxReq.getChildText("Seq"));// 流水號
	        if(seq.length() == 5) {
	        	reqCase.setSeq(seq);// 流水號
	        }else if(seq.length() < 5){
	        	reqCase.setSeq(StringUtils.leftPad(seq, 5, "0"));
	        }else {
	        	reqCase.setSeq("");
	        }
	        
	        reqCase.setDeptId(StringUtils.trimToEmpty(trxReq.getChildText("Deptid")));//部門代號
	        reqCase.setModifyMan(StringUtils.trimToEmpty(trxReq.getChildText("Modifyman")));//列印人員編號
			return reqCase;

		} catch (Exception e) {
			log.error("PaymentCashWebServiceImpl 發生錯誤  - " + ExceptionUtility.getStackTrace(e));
			return null;
		}

	}

	/**
	 * 處理資料邏輯
	 * 
	 * @return
	 */
	public String processRequest(PaymentReprintCase caseData) {
		try {
			log.info("PaymentPrintWebservice processRequest");
			PaymentReprintCase returnCase = new PaymentReprintCase();
			String prtDt = "show";//show:表示補印，已經有日期;空:表示列印，未有日期
        	String sequenceNo = caseData.getSeq();  
        	String paymentDate = caseData.getDte();
        	List<PaymentReprintCase> queryList = paymentService.getPaymentReprintList("","","",prtDt,  sequenceNo, paymentDate);
        	if(queryList==null || queryList.size()<=0){
        		return QRY_EMPTY;
        	}
        	
	      	PaymentReprintCase paymentCase = queryList.get(0);//單選，因此取第0個即可(選擇某張繳款單)
	      	List<PaymentReprintCase> dtlList = paymentService.getPaymentReprintdtlList(paymentCase);
	      	if(dtlList==null || dtlList.size()<=0){
	      		return QRY_EMPTY;
        	}
	      	
	     	List<String> paymentNoDtlList = new ArrayList<String>();
	     	//0~13
	  	 	String paymentNo="";
	     	for(int i=0; i<dtlList.size(); i++){//補印作業webservice全印
	     		String paymentNoDtl = dtlList.get(i).getPaymentNo();
	     		paymentDate = dtlList.get(i).getPrtDate();//以列印的日期為主
	  	 		paymentNoDtlList.add(paymentNoDtl);//補印的單號(有帶期別的明細)
	  	 		paymentNo = paymentNoDtl.substring(0,13)+"00";//繳款單號
	  	 	}	   
	 		PaymentUpdateCase qryData =  paymentService.getPaymentProcessQueryList(paymentNo);//繳款單主檔資料
	 		PaymentProcessQueryForm rptForm = new PaymentProcessQueryForm();
  		     
	 		if(qryData == null){
	 			return QRY_EMPTY;
	    	}else {
	    		BeanUtility.copyProperties(rptForm, qryData);
	  	    }
	    	List<PaymentProcessCase> qryDataUnacpdtl = paymentService.getPaymentProcessDetailList(paymentNo);//應收未收資料
	    	List<PaymentStageDtlCase> confirmPayment = paymentService.getPaymentAssignDetailList(paymentNo,0);//利息及執行費的分配明細
	    	//印報表
	    	String printDate = DateUtility.getNowChineseDate();
	    	ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
	    	String mApno = "";
	    	String appName="";
	    	String mPayKind="";
	    	String paymentSex = "";
	    	for(int j=0; j<qryDataUnacpdtl.size();j++){
	    		if(StringUtils.equals(qryDataUnacpdtl.get(j).getMapnoMk(), "1")){
	    		   mApno = qryDataUnacpdtl.get(j).getApno();//主辦案
	    		   appName = qryDataUnacpdtl.get(j).getBenName();//受益人姓名
	    		   mPayKind = qryDataUnacpdtl.get(j).getPayKind();
	    		   paymentSex = qryDataUnacpdtl.get(j).getPaymentSex();//受益人的性別
	    	   }
	    	}
	    	String empExt=queryService.getEmpExtData(mApno);//分機
	    	//String empExt = "7032";
	    	String evtName = paymentService.getEvtNameFromBaappbase(queryList.get(0).getIdn());//受理編號的事故者
	    	int execAmt = 0;
	    	int interestAmt = 0;
	    	for(int j=0; j<confirmPayment.size(); j++){//計算繳款單會用到的金額合計
	    		execAmt = execAmt + confirmPayment.get(j).getExecAmt().intValue();//總執行費
	    		interestAmt = interestAmt + confirmPayment.get(j).getPayInterest().intValue();//總利息費
	    	}
	    	rptForm.setTotExec(BigDecimal.valueOf(execAmt));//總執行費
	    	rptForm.setTotInterst(BigDecimal.valueOf(interestAmt));//總利息
	    	rptForm.setTotAmt(rptForm.getPayTotAmt());//應繳總額(含利息與執行費)
	    	PaymentReport report = new PaymentReport();
	        byte[] reportFile = report.doReportComp(paymentDate, paymentNoDtlList, null,evtName,  mApno, appName, mPayKind, empExt, confirmPayment, qryDataUnacpdtl, rptForm, paymentSex).toByteArray();
            String rptString = Base64.encodeBase64String(reportFile);
			return rptString;

		} catch (Exception e) {
			log.error("PaymentPrintWebservice 處理回傳資料  發生錯誤 - " + ExceptionUtility.getStackTrace(e));
			return null;
		}
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
}
