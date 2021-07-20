package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;
/**
 * 繳款單溢領 WEB SERVICE 主檔 (<code>BAPAYMENTREMOTE</code>)
 * 
 */
@Table("BAPAYMENTREMOTE")
public class Bapaymentremote  implements Serializable{
	private String eDate;//入帳日期
	private BigDecimal cashAmt;//應繳總金額
	private String sitDte;//繳納日期
	private String barCode1;//條碼1
	private String barCode2;//條碼2
	private String barCode3;//條碼3
	private String pno;//員工編號
	private String insKd;//保險別
	private String bliAccountCode;//局帳戶代號
	private String bookEdBook;//入帳方式
	private String bkAccountDt;//入帳日期
	private String batchNo;//批號
	private String serialNo;//流水號
	private String indexNo;//序號
	private String msg;//訊息
	private String status;//狀態
	private String waitMk;//待確認
	private String divMrk;//分割註記
	private String perUnitCd;//個人或單位
	private String perUnitName;//個人或單位名稱
	private String idNo;//身分證號
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	public String getSitDte() {
		return sitDte;
	}
	public void setSitDte(String sitDte) {
		this.sitDte = sitDte;
	}
	public String getBarCode1() {
		return barCode1;
	}
	public void setBarCode1(String barCode1) {
		this.barCode1 = barCode1;
	}
	public String getBarCode2() {
		return barCode2;
	}
	public void setBarCode2(String barCode2) {
		this.barCode2 = barCode2;
	}
	public String getBarCode3() {
		return barCode3;
	}
	public void setBarCode3(String barCode3) {
		this.barCode3 = barCode3;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getInsKd() {
		return insKd;
	}
	public void setInsKd(String insKd) {
		this.insKd = insKd;
	}
	public String getBliAccountCode() {
		return bliAccountCode;
	}
	public void setBliAccountCode(String bliAccountCode) {
		this.bliAccountCode = bliAccountCode;
	}
	public String getBookEdBook() {
		return bookEdBook;
	}
	public void setBookEdBook(String bookEdBook) {
		this.bookEdBook = bookEdBook;
	}
	public String getBkAccountDt() {
		return bkAccountDt;
	}
	public void setBkAccountDt(String bkAccountDt) {
		this.bkAccountDt = bkAccountDt;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWaitMk() {
		return waitMk;
	}
	public void setWaitMk(String waitMk) {
		this.waitMk = waitMk;
	}
		
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
		
	public String getDivMrk() {
		return divMrk;
	}
	public void setDivMrk(String divMrk) {
		this.divMrk = divMrk;
	}
	public String getPerUnitCd() {
		return perUnitCd;
	}
	public void setPerUnitCd(String perUnitCd) {
		this.perUnitCd = perUnitCd;
	}
	public String getPerUnitName() {
		return perUnitName;
	}
	public void setPerUnitName(String perUnitName) {
		this.perUnitName = perUnitName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
		
}
