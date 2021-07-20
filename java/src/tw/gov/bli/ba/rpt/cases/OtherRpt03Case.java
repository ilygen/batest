package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 已退匯止付尚未應收款立帳案件清單
 * 
 * @author Noctis
 */
public class OtherRpt03Case implements Serializable {
	private String apNo;// 受理編號
    private String seqNo;// 序號
    private String oriIssuYm;// 核定年月
    private String payYm;// 給付年月
    private String brChkDate; // 退匯初核日期
    private BigDecimal remitAmt; // 退匯金額
    
	public String getApNo() {
		return apNo;
	}
	public void setApNo(String apNo) {
		this.apNo = apNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getOriIssuYm() {
		return oriIssuYm;
	}
	public void setOriIssuYm(String oriIssuYm) {
		this.oriIssuYm = oriIssuYm;
	}
	public String getPayYm() {
		return payYm;
	}
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}

	public String getBrChkDate() {
		return brChkDate;
	}
	public void setBrChkDate(String brChkDate) {
		this.brChkDate = brChkDate;
	}
	public BigDecimal getRemitAmt() {
		return remitAmt;
	}
	public void setRemitAmt(BigDecimal remitAmt) {
		this.remitAmt = remitAmt;
	}

}
