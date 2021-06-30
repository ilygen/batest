package tw.gov.bli.ba.payment.webservice;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

/**
 * webservice for 繳款單
 * 
 * @author Zehua
 */
public class PaymentCashWebserviceImpl implements PaymentCashWebservice {
	private static Log log = LogFactory.getLog(PaymentCashWebserviceImpl.class);

	private PaymentService paymentService;

	// 測試程式
	// public static void main(String[] args){
	// try {
	// // 呼叫WebService
	// String endpoint =
	// "http://bliapbd.bli.gov.tw/BaWeb/remoting/mtRemoteService";
	// String namespace = "http://webservice.payment.ba.bli.gov.tw/"; //
	// WSDL裡描述的namespace
	// String operationName = "doApply";// WSDL裡描述的方法名稱
	// Service service = new Service();
	// Call call = (Call) service.createCall();
	// call.setTargetEndpointAddress(endpoint);
	// call.setOperationName(new QName(namespace, operationName));
	//
	// call.addParameter("reqXml", org.apache.axis.encoding.XMLType.XSD_STRING,
	// javax.xml.rpc.ParameterMode.IN);// 設置傳入參數欄位
	// call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 設置返回類型
	// String reqXml = "<?xml version=\"1.0\"
	// encoding=\"UTF-8\"?><BLIXML><Header><CltAppSeq></CltAppSeq><MsgId>MEFCRWOQRYWS</MsgId></Header><SignonReq><Sid>ME-CPA</Sid><Pwd>eniLNOcF</Pwd></SignonReq><TrxReq><Ubno>02001898</Ubno><Ubnochk>Y</Ubnochk><Rwoyy>2016</Rwoyy></TrxReq></BLIXML>";
	// String result = (String) call.invoke(new Object[] { reqXml }); //
	// 呼叫WebService取得回傳結果
	// System.out.print(result);

	// Service s = new Service();
	// Call call = (Call) s.createCall();
	// String xml = null;
	// call.setTargetEndpointAddress("http://127.0.0.1:8080/BaWeb/remoting/mtRemoteService");
	// call.setOperationName(new QName("mtRemoteService", "doApply"));
	// xml = "aaaa";
	// log.debug("cherry test:=====呼叫XML====="+xml);
	// Object[] params = {xml};
	// Object obj = call.invoke(params);
	// if(obj==null){
	// log.debug("cherry test 無回傳值");
	// return null;
	// }
	// }
	// catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	/**
	 * doApply 進入點
	 * 
	 * @return
	 */
	public String doApply(String xmlString) {
		try {
			log.info("PaymentCashWebservice 收到 XML 字串為: " + xmlString);
			PaymentWebserviceProcessCase caseData = new PaymentWebserviceProcessCase();
			if (StringUtils.isBlank(xmlString)) {
				return null;
			} else {
				caseData = parseRequest(xmlString);// parse傳入參數
			}
			if (caseData == null) {
				return null;
			}
			PaymentWebserviceProcessCase processData = processRequest(caseData);// 處裡邏輯
			if (processData == null) {
				return null;
			}
			String resXml = generateResXml(processData);
			// 寫入處理的繳款單資料
			paymentService.insertPaymentWebservice(processData);
			return resXml;

		} catch (Exception e) {
			log.error("PaymentCashWebserviceImpl 發生錯誤 - " + ExceptionUtility.getStackTrace(e));
			// return generateResopnseXML(null, STATUS_9, INFO_9);
		}
		return null;
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
	public String generateResXml(PaymentWebserviceProcessCase processData) {
		try {
			log.info("PaymentCashWebservice generateResXml");
			Element returnElmtRoot = new Element("Root");

			Document returnDocJDOM = new Document(returnElmtRoot);
			Element secondElmtRoot = new Element("Data");
			// ----------Header----------
			secondElmtRoot.addContent(new Element("CASH_AMT").addContent(String.valueOf(processData.getAmt())));//現金金額
			secondElmtRoot.addContent(new Element("WAITMK").addContent(processData.getWaitMk()));//待確認
			secondElmtRoot.addContent(new Element("DIVMRK").addContent(processData.getDivMrk()));//分割註記
			secondElmtRoot.addContent(new Element("PER_UNIT_CD").addContent(processData.getPerUnitCd()));//個人或單位
			secondElmtRoot.addContent(new Element("IDNO").addContent(processData.getIdno()));//身分證號
			secondElmtRoot.addContent(new Element("PER_UNIT_NAME").addContent(processData.getPerUnitName()));//個人或單位名稱
			// secondElmtRoot.addContent(new
			// Element("ACCEPT_ISSUE_KIND").addContent(processData.getAccptIssueKind()));

			Element rows = new Element("Rows");
			List<PaymentWebserviceDtlCase> dtlCase = processData.getDtlCase();
			Element row = null;
			for (int i = 0; i < dtlCase.size(); i++) {
				row = new Element("Row");
				row.addContent(new Element("ACCEPT_ISSUE_KIND")
						.addContent(String.valueOf(dtlCase.get(i).getAccpetIssueKind())));//收繳核發種類
				row.addContent(new Element("DIVSEQ").addContent(String.valueOf(dtlCase.get(i).getDivSeq())));//分割序號
				row.addContent(new Element("OFFKIND").addContent(dtlCase.get(i).getOffKind()));//銷號類別
				row.addContent(new Element("TEMPHANDLENO").addContent(dtlCase.get(i).getTempHandleNo()));//提繳受理編號
				//分割金額
				if (i == 0 && dtlCase.get(i).getDivAmout() == 0) {
					row.addContent(new Element("DIVAMOUNT").addContent(String.valueOf("")));
				} else {
					row.addContent(new Element("DIVAMOUNT").addContent(String.valueOf(dtlCase.get(i).getDivAmout())));
				}
				row.addContent(new Element("TEMPCHKMEMO").addContent(dtlCase.get(i).getTempChkMemo()));//暫收查明移至摘要
				row.addContent(new Element("GVSEQ").addContent(dtlCase.get(i).getGvSeq()));//受款人序
				row.addContent(new Element("NLWKMK").addContent(dtlCase.get(i).getNlwkMk()));//普職註記
				row.addContent(new Element("CMMK").addContent(dtlCase.get(i).getCmmk()));//現醫註記
				row.addContent(new Element("ADWKMK").addContent(dtlCase.get(i).getAdwkMk()));//一般加職註記
				rows.addContent(row);
			}

			secondElmtRoot.addContent(rows);
			returnElmtRoot.addContent(secondElmtRoot);

			XMLOutputter fmt = new XMLOutputter(Format.getCompactFormat());
			StringWriter fwXML = new StringWriter();
			fmt.output(returnDocJDOM, fwXML);
			fwXML.close();
			String resXml = fwXML.toString().replace("\r", "").replace("\n", "");

			log.info("PaymentCashWebservice 回傳的 XML 字串為: " + resXml);
			return resXml;
		} catch (Exception e) {
			log.error("PaymentCashWebserviceImpl 設定回傳XML時發生錯誤：" + ExceptionUtility.getStackTrace(e));
			return null;
		}
	}

	/**
	 * parse XML
	 * 
	 * @return
	 */
	public PaymentWebserviceProcessCase parseRequest(String xmlString) {
		try {
			log.info("PaymentCashWebservice parseRequest");
			PaymentWebserviceProcessCase reqCase = new PaymentWebserviceProcessCase();

			SAXBuilder bSAX = new SAXBuilder(false);
			StringReader xmlReader = new StringReader(xmlString);

			log.debug(xmlReader.toString());
			Document docJDOM = bSAX.build(xmlReader);
			Element elmtRoot = docJDOM.getRootElement();

			Element qryData = elmtRoot.getChild("QRYDATA");
			String edate = qryData.getChildText("EDATE");// 入帳日期
			String amt = qryData.getChildText("AMT");// 應繳總金額
			String sitDte = qryData.getChildText("SITDTE");// 繳納日期
			String barCode1 = qryData.getChildText("BARCODE1");// 條碼一
			String barCode2 = qryData.getChildText("BARCODE2");// 條碼二
			String barCode3 = qryData.getChildText("BARCODE3");// 條碼三
			String pno = qryData.getChildText("PNO");// 員工編號
			reqCase.setAmt(BigDecimal.valueOf(Long.parseLong(amt)));// 應繳總金額
			reqCase.setEdate(edate);// 入帳日期
			reqCase.setSitDte(sitDte);// 繳納日期
			reqCase.setBarCode1(barCode1);// 條碼一
			reqCase.setBarCode2(barCode2);// 條碼二
			reqCase.setBarCode3(barCode3);// 條碼三
			reqCase.setPno(pno);// 員工編號
			return reqCase;

		} catch (Exception e) {
			log.error("PaymentCashWebServiceImpl 發生錯誤  - " + ExceptionUtility.getStackTrace(e));
			// return generateResopnseXML(null, STATUS_9, INFO_9);
			return null;
		}

	}

	/**
	 * 處理資料邏輯
	 * 
	 * @return
	 */
	public PaymentWebserviceProcessCase processRequest(PaymentWebserviceProcessCase caseData) {
		try {
			log.info("PaymentCashWebservice processRequest");
			PaymentWebserviceProcessCase returnCase = new PaymentWebserviceProcessCase();
			BeanUtility.copyProperties(returnCase, caseData);
			String paymentNo = returnCase.getBarCode2().substring(1, 14) + "00";// 繳款單主案號為BARCODE第2碼~第13+"00"
			// 應收未收檔
			List<PaymentDetailCase> paymentData = paymentService.getUnacpdtlData(returnCase, paymentNo);
			// 取得利息及執行費
			List<PaymentStageDtlCase> paymentAssignData = paymentService.getPaymenInterestExecAmt(paymentNo);
			// 主檔資料
			PaymentDetailCase baseData = paymentService.getBaappbaseData(returnCase, paymentNo);
			BigDecimal totRecRem = BigDecimal.ZERO;// 總應繳金額
			BigDecimal totInterest = BigDecimal.ZERO;// 總利息費用
			BigDecimal totExec = BigDecimal.ZERO;// 總執行費
			if (paymentData != null) {
				for (int i = 0; i < paymentData.size(); i++) {
					totRecRem = totRecRem.add(paymentData.get(i).getRecRem());// 加總應繳金額
				}
			}
			if (paymentAssignData != null) {
				for (int i = 0; i < paymentAssignData.size(); i++) {
					totInterest = totInterest.add(paymentAssignData.get(i).getPayInterest());// 繳款單利息
					totExec = totExec.add(paymentAssignData.get(i).getExecAmt());// 繳款單執行費
				}
			}
			BigDecimal unacpAmt = totRecRem.add(totInterest).add(totExec);// 此繳款單應繳總金額(含利息+執行費用)
			// 母筆資料start
			returnCase.setPerUnitCd("1");// 個人或單位 1:個人
			// 當傳入應繳總金額時(payamt+totinterest+totexec)>應繳總金額，待確認註記：Y
			if (caseData.getAmt().intValue() > unacpAmt.intValue()) {
				returnCase.setWaitMk("Y");
			} else {
				returnCase.setWaitMk("N");
			}
			returnCase.setAccptIssueKind(returnCase.getBarCode2().substring(1, 3));// 給付種類
			returnCase.setIdno(baseData.getBenIdnNo());// 受益人idn
			returnCase.setPerUnitName(baseData.getBenName());// 受益人姓名

			int amt = returnCase.getAmt().intValue();
			// 是否需要分割，此次收回金額 > 應收未收檔未收餘額時就要分割 Y:分割 N:不分割
			if (amt > paymentData.get(0).getRecRem().intValue()) {
				returnCase.setDivMrk("Y");
			} else {
				returnCase.setDivMrk("N");
			}
			List<PaymentWebserviceDtlCase> dtlList = new ArrayList<PaymentWebserviceDtlCase>();
			int dataCount = 0;
			int dataCountExec = 0;
			// 子筆的第0筆，無論如何都要寫
			PaymentWebserviceDtlCase dtlCase0 = new PaymentWebserviceDtlCase();

			dtlCase0.setAccpetIssueKind("");
			String apno = "";
			String nlwkMk = "1";
			String adwkMk = "1";
			dtlCase0.setNlwkMk("1");
			dtlCase0.setAdwkMk("1");
			dtlCase0.setCmmk("1");
			if (paymentData != null) {
				apno = paymentData.get(0).getApno();
				if (StringUtils.isNotBlank(paymentData.get(0).getNlWkMk())) {
					nlwkMk = paymentData.get(0).getNlWkMk();
					dtlCase0.setNlwkMk(nlwkMk);
				}
				if (StringUtils.isNotBlank(paymentData.get(0).getAdWkMk())) {
					adwkMk = paymentData.get(0).getAdWkMk();
					dtlCase0.setAdwkMk(adwkMk);
				}
			}
			dtlList.add(dtlCase0);
			// 開始處理繳款單(應該要計算的為繳款單的期數+利息與執行費獨立的期數)
			for (int i = 0; i < (paymentData.size() + paymentAssignData.size()); i++) {

				if (amt > 0) {
					if (i < paymentData.size()) {
						PaymentWebserviceDtlCase dtlCase = new PaymentWebserviceDtlCase();
						// 未收餘額
						int recRem = paymentData.get(i).getRecRem().intValue();
						// 若收回金額>未收餘額，表示收回金額還大於未收餘額，可以繼續沖銷未收餘額
						// 分割序號，第1筆開始記錄
						if (amt > recRem) {
							dtlCase.setDivSeq(i + 1);
							dtlCase.setOffKind("1");// 扣本金
							dtlCase.setTempHandleNo(paymentData.get(i).getApno());// 提繳編號
							dtlCase.setDivAmout(paymentData.get(i).getRecRem().intValue());// 分割金額
							dtlCase.setTempChkMemo("");
							dtlCase.setGvSeq(paymentData.get(i).getSeqNo());// 受款人序
							if (StringUtils.equals(paymentData.get(i).getApno().substring(0, 1), "L")) {// 老年統一在加值註記塞1
								dtlCase.setAdwkMk("1");
								dtlCase.setNlwkMk("1");
							} else {
								if (StringUtils.isNotBlank(paymentData.get(i).getAdWkMk())) {
									dtlCase.setAdwkMk(paymentData.get(i).getAdWkMk());
								} else {
									dtlCase.setAdwkMk("1");
								}
								if (StringUtils.isNotBlank(paymentData.get(i).getNlWkMk())) {
									dtlCase.setNlwkMk(paymentData.get(i).getNlWkMk());
								} else {
									dtlCase.setNlwkMk("1");
								}
							}
							dtlCase.setAccpetIssueKind(paymentData.get(i).getPayKind());
							dtlCase.setCmmk("1");
							// AMT可以繼續沖銷
							amt = amt - recRem;
							dtlList.add(dtlCase);
							// 若收回金額=未收餘額，表示收回金額=未收餘額，沖銷此筆後收回金額歸零
						} else if (amt == recRem) {
							dtlCase.setDivSeq(i + 1);
							dtlCase.setOffKind("1");// 扣本金
							dtlCase.setTempHandleNo(paymentData.get(i).getApno());// 提繳編號
							dtlCase.setDivAmout(paymentData.get(i).getRecRem().intValue());// 分割金額
							dtlCase.setTempChkMemo("");
							dtlCase.setGvSeq(paymentData.get(i).getSeqNo());// 受款人序
							if (StringUtils.isNotBlank(paymentData.get(i).getAdWkMk())) {
								dtlCase.setAdwkMk(paymentData.get(i).getAdWkMk());
							} else {
								dtlCase.setAdwkMk("1");
							}
							if (StringUtils.isNotBlank(paymentData.get(i).getNlWkMk())) {
								dtlCase.setNlwkMk(paymentData.get(i).getNlWkMk());
							} else {
								dtlCase.setNlwkMk("1");
							}
							dtlCase.setCmmk("1");
							dtlCase.setAccpetIssueKind(paymentData.get(i).getPayKind());
							// AMT-RECREM = 0 不會再繼續沖銷
							amt = amt - recRem;
							dtlList.add(dtlCase);
							// 若收回金額-此筆未收餘額<0，表示收回金額<未收餘額，沖銷此筆後收回金額歸零，不必在沖銷
						} else {
							dtlCase.setDivSeq(i + 1);
							dtlCase.setOffKind("1");// 扣本金
							dtlCase.setTempHandleNo(paymentData.get(i).getApno());// 提繳編號
							dtlCase.setDivAmout(amt);// 分割金額
							dtlCase.setTempChkMemo("");
							dtlCase.setGvSeq(paymentData.get(i).getSeqNo());// 受款人序
							dtlCase.setAccpetIssueKind(paymentData.get(i).getPayKind());
							if (StringUtils.isNotBlank(paymentData.get(i).getAdWkMk())) {
								dtlCase.setAdwkMk(paymentData.get(i).getAdWkMk());
							} else {
								dtlCase.setAdwkMk("1");
							}
							if (StringUtils.isNotBlank(paymentData.get(i).getNlWkMk())) {
								dtlCase.setNlwkMk(paymentData.get(i).getNlWkMk());
							} else {
								dtlCase.setNlwkMk("1");
							}
							dtlCase.setCmmk("1");
							// AMT歸零
							amt = 0;
							dtlList.add(dtlCase);
						}
					} else {
						// dataCount=繳款單的利息與執行費期數,amt扣完繳款單，可以繼續扣利息、執行費
						if (dataCount < paymentAssignData.size()) {
							PaymentWebserviceDtlCase dtlCase = new PaymentWebserviceDtlCase();
							// 開始扣interest
							int interestAmt = paymentAssignData.get(dataCount).getPayInterest().intValue();
							if (interestAmt > 0) {
								// 收回金額還大於利息費用，可以繼續沖銷
								if (amt > interestAmt) {
									dtlCase.setDivSeq(dtlList.size());// 分割序號從本金扣完的次數開始算
									dtlCase.setOffKind("3");// 扣利息
									dtlCase.setTempHandleNo(apno);// 提繳編號
									dtlCase.setDivAmout(interestAmt);// 分割金額
									dtlCase.setTempChkMemo("");
									dtlCase.setGvSeq("0");// 受款人序
									if (StringUtils.equals(apno.substring(0, 1), "L")) {
										dtlCase.setAdwkMk("1");
										dtlCase.setNlwkMk("1");
									} else {
										dtlCase.setAdwkMk(adwkMk);
										dtlCase.setNlwkMk(nlwkMk);
									}
									dtlCase.setCmmk("1");
									dtlCase.setAccpetIssueKind(returnCase.getBarCode2().substring(1, 3));
									// 可以繼續扣，因收回金額>利息費用
									amt = amt - interestAmt;
									dataCount = dataCount + 1;
									dtlList.add(dtlCase);
									// 收回金額還=利息費用，不用繼續沖銷
								} else if (amt == interestAmt) {
									dtlCase.setDivSeq(dtlList.size());
									dtlCase.setOffKind("3");// 扣利息
									dtlCase.setTempHandleNo(apno);// 提繳編號
									dtlCase.setDivAmout(interestAmt);// 分割金額
									dtlCase.setTempChkMemo("");
									dtlCase.setGvSeq("0");// 受款人序
									if (StringUtils.equals(apno.substring(0, 1), "L")) {
										dtlCase.setAdwkMk("1");
										dtlCase.setNlwkMk("1");
									} else {
										dtlCase.setAdwkMk(adwkMk);
										dtlCase.setNlwkMk(nlwkMk);
									}

									dtlCase.setCmmk("1");
									dtlCase.setAccpetIssueKind(returnCase.getBarCode2().substring(1, 3));
									// amt扣完歸零，下次不會再進來
									amt = amt - interestAmt;
									dataCount = dataCount + 1;
									dtlList.add(dtlCase);
									// 收回金額<利息費用，不用繼續沖銷
								} else {
									amt = 0;
									dataCount = dataCount + 1;
								}
							} else {
								// 如果為當期利息為0就直接做下一筆
								dataCount = dataCount + 1;
							}

						}

						if (dataCountExec < paymentAssignData.size()) {
							PaymentWebserviceDtlCase dtlCase = new PaymentWebserviceDtlCase();
							// 開始扣執行費
							int execAmt = paymentAssignData.get(dataCountExec).getExecAmt().intValue();
							if (execAmt > 0) {
								// 收回金額還大於執行費用，可以繼續沖銷
								if (amt > execAmt) {
									dtlCase.setDivSeq(dtlList.size());// 分割序號從本金+利息扣完的次數開始算
									dtlCase.setOffKind("2");// 扣執行費
									dtlCase.setTempHandleNo(apno);// 提繳編號
									dtlCase.setDivAmout(execAmt);// 分割金額
									dtlCase.setTempChkMemo("");
									dtlCase.setGvSeq("0");// 受款人序
									if (StringUtils.equals(apno.substring(0, 1), "L")) {
										dtlCase.setAdwkMk("1");
										dtlCase.setNlwkMk("1");
									} else {
										dtlCase.setAdwkMk(adwkMk);
										dtlCase.setNlwkMk(nlwkMk);
									}
									dtlCase.setCmmk("1");
									dtlCase.setAccpetIssueKind(returnCase.getBarCode2().substring(1, 3));
									amt = amt - execAmt;
									dataCountExec = dataCountExec + 1;
									dtlList.add(dtlCase);
									// 收回金額還=執行費用，不用繼續沖銷
								} else if (amt == execAmt) {
									dtlCase.setDivSeq(dtlList.size());
									dtlCase.setOffKind("2");// 扣執行費
									dtlCase.setTempHandleNo(apno);// 提繳編號
									dtlCase.setDivAmout(execAmt);// 分割金額
									dtlCase.setTempChkMemo("");
									dtlCase.setGvSeq("0");// 受款人序
									if (StringUtils.equals(apno.substring(0, 1), "L")) {
										dtlCase.setAdwkMk("1");
										dtlCase.setNlwkMk("1");
									} else {
										dtlCase.setAdwkMk(adwkMk);
										dtlCase.setNlwkMk(nlwkMk);
									}

									dtlCase.setCmmk("1");
									dtlCase.setAccpetIssueKind(returnCase.getBarCode2().substring(1, 3));
									amt = amt - execAmt;
									dataCountExec = dataCountExec + 1;
									dtlList.add(dtlCase);
									// 收回金額<利息費用，不用繼續沖銷
								} else {
									amt = 0;
									dataCountExec = dataCountExec + 1;
									// 如果為當期執行費為0就直接做下一筆
									dataCountExec = dataCountExec + 1;
								}
							} else {
								// 如果為當期執行費為0就直接做下一筆
								dataCountExec = dataCountExec + 1;
							}

						}
					}
				}

			}
			// 扣完所有金額後還有錢，就最後再放一個註記offkind=4
			if (amt > 0) {
				// paymentData.size() + paymentAssignData.size()
				PaymentWebserviceDtlCase dtlCase2 = new PaymentWebserviceDtlCase();
				dtlCase2.setDivSeq(dtlList.size());
				dtlCase2.setOffKind("4");
				dtlCase2.setTempHandleNo(apno);// 提繳編號
				dtlCase2.setDivAmout(amt);// 分割金額
				dtlCase2.setTempChkMemo("");
				dtlCase2.setGvSeq("0");// 受款人序
				if (StringUtils.equals(apno.substring(0, 1), "L")) {
					dtlCase2.setAdwkMk("1");
					dtlCase2.setNlwkMk("1");
				} else {
					dtlCase2.setAdwkMk(adwkMk);
					dtlCase2.setNlwkMk(nlwkMk);
				}
				dtlCase2.setAccpetIssueKind(returnCase.getBarCode2().substring(1, 3));
				dtlCase2.setCmmk("1");
				amt = 0;
				dataCountExec = dataCountExec + 1;
				dtlList.add(dtlCase2);
			}
			returnCase.setDtlCase(dtlList);

			return returnCase;

		} catch (Exception e) {
			log.error("PaymentCashWebserviceImpl 處理回傳資料  發生錯誤 - " + ExceptionUtility.getStackTrace(e));
			return null;
		}
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
}
