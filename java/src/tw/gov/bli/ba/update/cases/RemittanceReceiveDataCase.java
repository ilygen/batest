package tw.gov.bli.ba.update.cases;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.PkeyField;
/**
 * Case for 老年年金應收收回處理
 * 
 * @author Noctis
 */
public class RemittanceReceiveDataCase {
    private String apNo;      // 受理編號
    private String seqNo;     // 受款人序
    private String oriIssuYm;// 核定年月
    private String payYm;// 給付年月
    private String brChkDate;// 退匯日期
    private BigDecimal remitAmt;// 退匯金額
    private String issuKind;//
    private String brNote;// 
    //BAREGIVEDTL FOR 應收收回處理作業
    private String transActionId;// 交易編號
    private String transActionSeq;// 交易序號
    private String insKd;// 保險別
    
    //For BAUNACPDTL
    private BigDecimal baunacpdtlId; //資料列編號(應收帳務明細編號)
    private BigDecimal baacpdtlId; //
    private String issuYm;
    private BigDecimal recRem;
    private BigDecimal recAmt;

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

	public String getPayYm() {
		return payYm;
	}

	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}

	public String getOriIssuYm() {
		return oriIssuYm;
	}

	public void setOriIssuYm(String oriIssuYm) {
		this.oriIssuYm = oriIssuYm;
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

	public String getIssuKind() {
		return issuKind;
	}

	public void setIssuKind(String issuKind) {
		this.issuKind = issuKind;
	}

	public String getBrNote() {
		return brNote;
	}

	public void setBrNote(String brNote) {
		this.brNote = brNote;
	}

	public String getTransActionId() {
		return transActionId;
	}

	public void setTransActionId(String transActionId) {
		this.transActionId = transActionId;
	}

	public String getTransActionSeq() {
		return transActionSeq;
	}

	public void setTransActionSeq(String transActionSeq) {
		this.transActionSeq = transActionSeq;
	}

	public String getInsKd() {
		return insKd;
	}

	public void setInsKd(String insKd) {
		this.insKd = insKd;
	}

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}

	public BigDecimal getRecRem() {
		return recRem;
	}

	public void setRecRem(BigDecimal recRem) {
		this.recRem = recRem;
	}

	public BigDecimal getBaunacpdtlId() {
		return baunacpdtlId;
	}

	public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
		this.baunacpdtlId = baunacpdtlId;
	}

	public BigDecimal getBaacpdtlId() {
		return baacpdtlId;
	}

	public void setBaacpdtlId(BigDecimal baacpdtlId) {
		this.baacpdtlId = baacpdtlId;
	}

	public BigDecimal getRecAmt() {
		return recAmt;
	}

	public void setRecAmt(BigDecimal recAmt) {
		this.recAmt = recAmt;
	}

}
