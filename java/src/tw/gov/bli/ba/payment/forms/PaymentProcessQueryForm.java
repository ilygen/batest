package tw.gov.bli.ba.payment.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class PaymentProcessQueryForm  extends BaseValidatorForm {
	private String isPrint;
	private String idn;
	private String apNo;// 受理編號
	private String evtName;//姓名
	private String paymentNo;//繳款單號
	private String classNo;//移送案號
	private String paymentDateLine;//繳款期限
	private String zipCode;//郵遞區號
	private String addr;//地址
	private BigDecimal totAmtStage;//本金期數
	private BigDecimal interestStage;//利息期數
	private BigDecimal totStage;//本金期數+利息期數	
	private BigDecimal stageAmt;//每期攤還本金
	private BigDecimal totTrgAmt;//利息標的金額
	private BigDecimal interestTryStage;//利息試算期數
	private BigDecimal monthlyPayAmt;//利息每期本金
	private String caseType;//繳款單類別
	private BigDecimal payTotAmt;//應繳總額
	private BigDecimal payTotIntAmt;//應繳總額(含利息)
	private BigDecimal totAmt;//應繳總額(含利息、執行費用)
	private BigDecimal execAmt;//執行費用
	private String chkObj; // 給付種類
	private String mapnoMk;// 主辦案
	private String writeoffSeqNo;
	private String monthlyPayList;
	private String idForConfirm;//有勾選的
	private String mkForConfirm;//主辦案
	private String seqNoForConfirm;//銷帳序
	private String payAmtForConfirm;//應繳本金
	private String adjInterestList;//調整利息
	private String paymentDateLineList;//修改的dateline
	private BigDecimal accAmout;//本利和
	private BigDecimal totInterst;//總利息
	private BigDecimal totExec;//總執行費用
	private String execList;
	private String interstList;
	private BigDecimal tryInterestAmt;//試算本利和
	private String prtDate;//列印日期
	//bapm0d013m
	private String paymentNoforModify;//
	private String interestBegDates;//修改的利息起算日
	private String modifyString;//是否為修改
	private String insertString;
	private String prtModify;
	private String method;//
	private String afterInterest;//是否試算過利息，有試算過才可以執行開單確認
	private String interestEndDateList;
	private String interestBegDateList;
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
	    return errors;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

	}
	public String getIdn() {
		return idn;
	}
	public void setIdn(String idn) {
		this.idn = idn;
	}
	public String getApNo() {
		return apNo;
	}
	public void setApNo(String apNo) {
		this.apNo = apNo;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getPaymentDateLine() {
		return paymentDateLine;
	}
	public void setPaymentDateLine(String paymentDateLine) {
		this.paymentDateLine = paymentDateLine;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public BigDecimal getStageAmt() {
		return stageAmt;
	}
	public void setStageAmt(BigDecimal stageAmt) {
		this.stageAmt = stageAmt;
	}
	public BigDecimal getTotTrgAmt() {
		return totTrgAmt;
	}
	public void setTotTrgAmt(BigDecimal totTrgAmt) {
		this.totTrgAmt = totTrgAmt;
	}
	
	public BigDecimal getMonthlyPayAmt() {
		return monthlyPayAmt;
	}
	public void setMonthlyPayAmt(BigDecimal monthlyPayAmt) {
		this.monthlyPayAmt = monthlyPayAmt;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public BigDecimal getTotAmtStage() {
		return totAmtStage;
	}
	public void setTotAmtStage(BigDecimal totAmtStage) {
		this.totAmtStage = totAmtStage;
	}
	public BigDecimal getInterestStage() {
		return interestStage;
	}
	public void setInterestStage(BigDecimal interestStage) {
		this.interestStage = interestStage;
	}
	public BigDecimal getInterestTryStage() {
		return interestTryStage;
	}
	public void setInterestTryStage(BigDecimal interestTryStage) {
		this.interestTryStage = interestTryStage;
	}
	public BigDecimal getPayTotAmt() {
		return payTotAmt;
	}
	public void setPayTotAmt(BigDecimal payTotAmt) {
		this.payTotAmt = payTotAmt;
	}
	public String getChkObj() {
		return chkObj;
	}
	public void setChkObj(String chkObj) {
		this.chkObj = chkObj;
	}
	public String getMapnoMk() {
		return mapnoMk;
	}
	public void setMapnoMk(String mapnoMk) {
		this.mapnoMk = mapnoMk;
	}
	public String getWriteoffSeqNo() {
		return writeoffSeqNo;
	}
	public void setWriteoffSeqNo(String writeoffSeqNo) {
		this.writeoffSeqNo = writeoffSeqNo;
	}
	public String getIdForConfirm() {
		return idForConfirm;
	}
	public void setIdForConfirm(String idForConfirm) {
		this.idForConfirm = idForConfirm;
	}
	public String getMkForConfirm() {
		return mkForConfirm;
	}
	public void setMkForConfirm(String mkForConfirm) {
		this.mkForConfirm = mkForConfirm;
	}
	public String getSeqNoForConfirm() {
		return seqNoForConfirm;
	}
	public void setSeqNoForConfirm(String seqNoForConfirm) {
		this.seqNoForConfirm = seqNoForConfirm;
	}
	public String getPayAmtForConfirm() {
		return payAmtForConfirm;
	}
	public void setPayAmtForConfirm(String payAmtForConfirm) {
		this.payAmtForConfirm = payAmtForConfirm;
	}
	public BigDecimal getAccAmout() {
		return accAmout;
	}
	public void setAccAmout(BigDecimal accAmout) {
		this.accAmout = accAmout;
	}
	public BigDecimal getTotInterst() {
		return totInterst;
	}
	public void setTotInterst(BigDecimal totInterst) {
		this.totInterst = totInterst;
	}
	public BigDecimal getTryInterestAmt() {
		return tryInterestAmt;
	}
	public void setTryInterestAmt(BigDecimal tryInterestAmt) {
		this.tryInterestAmt = tryInterestAmt;
	}
	public String getInterestBegDates() {
		return interestBegDates;
	}
	public void setInterestBegDates(String interestBegDates) {
		this.interestBegDates = interestBegDates;
	}
	public String getAdjInterestList() {
		return adjInterestList;
	}
	public void setAdjInterestList(String adjInterestList) {
		this.adjInterestList = adjInterestList;
	}
	public BigDecimal getPayTotIntAmt() {
		return payTotIntAmt;
	}
	public void setPayTotIntAmt(BigDecimal payTotIntAmt) {
		this.payTotIntAmt = payTotIntAmt;
	}
	public BigDecimal getTotExec() {
		return totExec;
	}
	public void setTotExec(BigDecimal totExec) {
		this.totExec = totExec;
	}
	public BigDecimal getExecAmt() {
		return execAmt;
	}
	public void setExecAmt(BigDecimal execAmt) {
		this.execAmt = execAmt;
	}
	public String getExecList() {
		return execList;
	}
	public void setExecList(String execList) {
		this.execList = execList;
	}
	public String getInterstList() {
		return interstList;
	}
	public void setInterstList(String interstList) {
		this.interstList = interstList;
	}
	public BigDecimal getTotStage() {
		return totStage;
	}
	public void setTotStage(BigDecimal totStage) {
		this.totStage = totStage;
	}
	public BigDecimal getTotAmt() {
		return totAmt;
	}
	public void setTotAmt(BigDecimal totAmt) {
		this.totAmt = totAmt;
	}
	public String getPrtDate() {
		return prtDate;
	}
	public void setPrtDate(String prtDate) {
		this.prtDate = prtDate;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getModifyString() {
		return modifyString;
	}
	public void setModifyString(String modifyString) {
		this.modifyString = modifyString;
	}
	public String getPaymentNoforModify() {
		return paymentNoforModify;
	}
	public void setPaymentNoforModify(String paymentNoforModify) {
		this.paymentNoforModify = paymentNoforModify;
	}
	public String getPrtModify() {
		return prtModify;
	}
	public void setPrtModify(String prtModify) {
		this.prtModify = prtModify;
	}
	public String getPaymentDateLineList() {
		return paymentDateLineList;
	}
	public void setPaymentDateLineList(String paymentDateLineList) {
		this.paymentDateLineList = paymentDateLineList;
	}
	public String getEvtName() {
		return evtName;
	}
	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
	public String getAfterInterest() {
		return afterInterest;
	}
	public void setAfterInterest(String afterInterest) {
		this.afterInterest = afterInterest;
	}
	public String getMonthlyPayList() {
		return monthlyPayList;
	}
	public void setMonthlyPayList(String monthlyPayList) {
		this.monthlyPayList = monthlyPayList;
	}
	public String getInterestEndDateList() {
		return interestEndDateList;
	}
	public void setInterestEndDateList(String interestEndDateList) {
		this.interestEndDateList = interestEndDateList;
	}
	public String getInterestBegDateList() {
		return interestBegDateList;
	}
	public void setInterestBegDateList(String interestBegDateList) {
		this.interestBegDateList = interestBegDateList;
	}
	public String getInsertString() {
		return insertString;
	}
	public void setInsertString(String insertString) {
		this.insertString = insertString;
	}
	public String getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
}
