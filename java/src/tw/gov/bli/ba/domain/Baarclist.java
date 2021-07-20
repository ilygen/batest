package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 案件歸檔紀錄檔
 * 
 * @author jerry
 */
public class Baarclist implements Serializable {
    private BigDecimal baarclistNum;// 序號
    private String payCode;// 給付別
    private String caseTyp; // 案件類別
    private String issuYm; // 核定年月
    private String exeBdate;// 決行日期起
    private String exeEdate;// 決行日期迄
    private BigDecimal arcPg;// 歸檔頁次
    private String arcDate;// 歸檔日期(印表日期)
    private String apNo; // 受理編號
    private String evtName; // 事故者姓名
    private String chkMan; // 審核人員
    private String exeMan; // 決行人員
    private String delMl;// 刪除註記
    private String note;// 備註
    private String exeDate;//決行日期

    // field not in baarclist
    private String maxIssuYm;
    private String minIssuYm;
    
    //For DecisionRpt03
    private BigDecimal minArcPg;// 歸檔起頁次
    private BigDecimal maxArcPg;// 歸檔迄頁次
    private BigDecimal countNumber;// 件數


    public BigDecimal getBaarclistNum() {
        return baarclistNum;
    }

    public void setBaarclistNum(BigDecimal baarclistNum) {
        this.baarclistNum = baarclistNum;
    }

    public BigDecimal getArcPg() {
        return arcPg;
    }

    public void setArcPg(BigDecimal arcPg) {
        this.arcPg = arcPg;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getExeBdate() {
        return exeBdate;
    }

    public void setExeBdate(String exeBdate) {
        this.exeBdate = exeBdate;
    }

    public String getExeEdate() {
        return exeEdate;
    }

    public void setExeEdate(String exeEdate) {
        this.exeEdate = exeEdate;
    }

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getDelMl() {
        return delMl;
    }

    public void setDelMl(String delMl) {
        this.delMl = delMl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getMaxIssuYm() {
        return maxIssuYm;
    }

    public void setMaxIssuYm(String maxIssuYm) {
        this.maxIssuYm = maxIssuYm;
    }

    public String getMinIssuYm() {
        return minIssuYm;
    }

    public void setMinIssuYm(String minIssuYm) {
        this.minIssuYm = minIssuYm;
    }

	public BigDecimal getMinArcPg() {
		return minArcPg;
	}

	public void setMinArcPg(BigDecimal minArcPg) {
		this.minArcPg = minArcPg;
	}

	public BigDecimal getMaxArcPg() {
		return maxArcPg;
	}

	public void setMaxArcPg(BigDecimal maxArcPg) {
		this.maxArcPg = maxArcPg;
	}

	public BigDecimal getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(BigDecimal countNumber) {
		this.countNumber = countNumber;
	}

}
