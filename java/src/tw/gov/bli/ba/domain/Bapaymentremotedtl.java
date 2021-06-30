package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Case for 繳款單 webservice明細
 * 
 * @author Zehua
 */
public class Bapaymentremotedtl  implements Serializable{
	private String barCode1;//條碼1
	private String barCode2;//條碼2
	private String barCode3;//條碼3
	private int divSeq;//分割序號
	private String tempHandleNo;//提繳受理編號
	private int divAmount;//分割金額
	private String tempChkMemo;//暫收查明移至摘要
	private String gvSeq;//受款人序
	private String nlwkMk;//普職註記
	private String cmMk;//現醫註記
	private String adwkMk;//一般加職註記
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
	
	public int getDivSeq() {
		return divSeq;
	}
	public void setDivSeq(int divSeq) {
		this.divSeq = divSeq;
	}
	public String getTempHandleNo() {
		return tempHandleNo;
	}
	public void setTempHandleNo(String tempHandleNo) {
		this.tempHandleNo = tempHandleNo;
	}
	
	public int getDivAmount() {
		return divAmount;
	}
	public void setDivAmount(int divAmount) {
		this.divAmount = divAmount;
	}
	public String getTempChkMemo() {
		return tempChkMemo;
	}
	public void setTempChkMemo(String tempChkMemo) {
		this.tempChkMemo = tempChkMemo;
	}
	public String getGvSeq() {
		return gvSeq;
	}
	public void setGvSeq(String gvSeq) {
		this.gvSeq = gvSeq;
	}
	public String getNlwkMk() {
		return nlwkMk;
	}
	public void setNlwkMk(String nlwkMk) {
		this.nlwkMk = nlwkMk;
	}
	public String getCmMk() {
		return cmMk;
	}
	public void setCmMk(String cmMk) {
		this.cmMk = cmMk;
	}
	public String getAdwkMk() {
		return adwkMk;
	}
	public void setAdwkMk(String adwkMk) {
		this.adwkMk = adwkMk;
	}
}
