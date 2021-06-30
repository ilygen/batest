package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 應收帳務明細檔
 * 
 * @author Rickychi
 */
public class Baunacpdtl implements Serializable {
    private BigDecimal baunacpdtlId;// 資料列編號(應收帳務明細編號)
    private BigDecimal baunacprecId;// 應收記錄編號
    private BigDecimal unacpDtlNo;// 應收帳務明細序號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String recKind;// 收回種類
    private String recTyp;// 收回方式
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String benIds;// 受款人社福識別碼
    private String benIdnNo;// 受款人身分證號
    private String benName;// 受款人姓名
    private String benBrDate;// 受款人出生日期
    private BigDecimal recAmt;// 應收(應扣減)金額
    private BigDecimal woAmt;// 已收(已扣減)金額
    private BigDecimal realRrecAmt;// 實收(扣減)金額
    private BigDecimal intAmt;// 利息收入
    private String recDate;// 收回(扣減)日期
    private String aplPayDate;// 入帳日期
    private String procMk;// 處理回覆結果
    private String procMkDate;// 處理回覆日期
    private String procStat;// 處理狀態
    private String procMan;// 處理人員
    private String procDate;// 處理日期
    private String evtIds;// 被保險人社福識別碼
    private String evtIdnNo;// 被保險人身分證號
    private String evtName;// 被保險人姓名
    private String evtBrDate;// 被保險人出生日期
    private String evtJobDate;// 事故者離職日期
    private BigDecimal recRem;// 未收餘額
    private String sts;// 資料狀態
    private String chkDate;//核定日期
    // Field not in BAUNACPDTL
    // [
    // private String apNo; // 受理編號
    // private String evtIdnNo; // 事故者身分證號
    // private String evtName; // 事故者姓名
    // private String evtBrDate; // 事故者出生日期
    // private String issuYm; // 核定年月
    // private String payYm; // 給付年月
    private String unacpDesc; // 應收原因代號
    private String unapcKind; // (應收)收回種類
    // private String procStat; // (應收)處理狀態
    private String unacpDate; // 應收立帳日期
    // private String aplPayDate; // 入帳日期
    // private BigDecimal recAmt; // 應收金額
    private BigDecimal unapcDtlNo; // 收回序號
    private String recProcStat; // (已收)處理狀態
    // private String recKind; // (已收)收回種類
    // private String recTyp; // 收回方式
    // private String recDate; // 收回日期
    private String recAplPayDate; // 入帳日期
    // private BigDecimal woAmt; // 收回金額
    private BigDecimal recWoAmt; // 應收餘額
    private BigDecimal rowNum; // 序
    private String brChkDate; // 退匯初核日期
    private BigDecimal remitAmt; // 退匯金額
    private String source; // 來源
    private String bDebtYear;
    private String benSex;
    private String payKind;//給付方式
    private BigDecimal payAmt;//應繳本金
    
    private String naChgMk;
    private String nlWkMk;
    private String adWkMk;    
    // ]

    public BigDecimal getBaunacpdtlId() {
        return baunacpdtlId;
    }

    public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
        this.baunacpdtlId = baunacpdtlId;
    }

    public BigDecimal getBaunacprecId() {
        return baunacprecId;
    }

    public void setBaunacprecId(BigDecimal baunacprecId) {
        this.baunacprecId = baunacprecId;
    }

    public BigDecimal getUnacpDtlNo() {
        return unacpDtlNo;
    }

    public void setUnacpDtlNo(BigDecimal unacpDtlNo) {
        this.unacpDtlNo = unacpDtlNo;
    }

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

    public String getRecKind() {
        return recKind;
    }

    public void setRecKind(String recKind) {
        this.recKind = recKind;
    }

    public String getRecTyp() {
        return recTyp;
    }

    public void setRecTyp(String recTyp) {
        this.recTyp = recTyp;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getWoAmt() {
        return woAmt;
    }

    public void setWoAmt(BigDecimal woAmt) {
        this.woAmt = woAmt;
    }

    public BigDecimal getRealRrecAmt() {
        return realRrecAmt;
    }

    public void setRealRrecAmt(BigDecimal realRrecAmt) {
        this.realRrecAmt = realRrecAmt;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getProcMk() {
        return procMk;
    }

    public void setProcMk(String procMk) {
        this.procMk = procMk;
    }

    public String getProcMkDate() {
        return procMkDate;
    }

    public void setProcMkDate(String procMkDate) {
        this.procMkDate = procMkDate;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getProcMan() {
        return procMan;
    }

    public void setProcMan(String procMan) {
        this.procMan = procMan;
    }

    public String getProcDate() {
        return procDate;
    }

    public void setProcDate(String procDate) {
        this.procDate = procDate;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }
    
    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getUnacpDesc() {
        return unacpDesc;
    }

    public void setUnacpDesc(String unacpDesc) {
        this.unacpDesc = unacpDesc;
    }

    public String getUnapcKind() {
        return unapcKind;
    }

    public void setUnapcKind(String unapcKind) {
        this.unapcKind = unapcKind;
    }

    public String getUnacpDate() {
        return unacpDate;
    }

    public void setUnacpDate(String unacpDate) {
        this.unacpDate = unacpDate;
    }

    public BigDecimal getUnapcDtlNo() {
        return unapcDtlNo;
    }

    public void setUnapcDtlNo(BigDecimal unapcDtlNo) {
        this.unapcDtlNo = unapcDtlNo;
    }

    public String getRecProcStat() {
        return recProcStat;
    }

    public void setRecProcStat(String recProcStat) {
        this.recProcStat = recProcStat;
    }

    public String getRecAplPayDate() {
        return recAplPayDate;
    }

    public void setRecAplPayDate(String recAplPayDate) {
        this.recAplPayDate = recAplPayDate;
    }

    public BigDecimal getRecWoAmt() {
        return recWoAmt;
    }

    public void setRecWoAmt(BigDecimal recWoAmt) {
        this.recWoAmt = recWoAmt;
    }

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public BigDecimal getRowNum() {
        return rowNum;
    }

    public void setRowNum(BigDecimal rowNum) {
        this.rowNum = rowNum;
    }

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChkDate() {
		return chkDate;
	}

	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}

	public String getbDebtYear() {
		return bDebtYear;
	}

	public void setbDebtYear(String bDebtYear) {
		this.bDebtYear = bDebtYear;
	}

	public String getPayKind() {
		return payKind;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getBenSex() {
		return benSex;
	}

	public void setBenSex(String benSex) {
		this.benSex = benSex;
	}
	
    public String getNaChgMk() {
        return naChgMk;
    }

    public void setNaChgMk(String naChgMk) {
        this.naChgMk = naChgMk;
    }    
    
    public String getNlWkMk() {
        return nlWkMk;
    }

    public void setNlWkMk(String nlWkMk) {
        this.nlWkMk = nlWkMk;
    }     
    
    public String getAdWkMk() {
        return adWkMk;
    }

    public void setAdWkMk(String adWkMk) {
        this.adWkMk = adWkMk;
    }

}
