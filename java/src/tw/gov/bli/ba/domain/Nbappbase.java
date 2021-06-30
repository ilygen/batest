package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 國保給付主檔
 * 
 * @author Rickychi
 */
@Table("NBAPPBASE")
public class Nbappbase implements Serializable {
    private String appDate; // 申請日期(收件日期)
    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String payYms; // 首次發放起月
    private String payYme; // 首次發放迄月
    private String evtDt; // 事故日期
    private BigDecimal issueAmt; // 核定金額
    private String manChkFlg; // 人工審核結果
    private String acceptMark; // 合格註記
    private String closeReason; // 結案原因
    private String closeDt; // 結案日期
    private String labMerge;// 併計勞保年資

    private String dabLevel;// 障礙等級 0:無 4:輕度 3:中度 2:重度1:極重度
    private String dabType; // 障礙類別
    private String dabPart; // 障礙鑑定部位

    private String evtIds; //事故者社福識別碼
    private String evtIdnNo; //事故者身分證號
    private String evteeBirt; //事故者出生日期
    private String evteeName; // 事故者姓名
    private String evtSex; //事故者性別
    private String evtIdent; //事故者身分別
    private String dieDate; //事故者死亡日期
    private String benIds; //受益人社福識別碼
    private String benIdnNo; //受益人身分證號
    private String beneeName; //受益人姓名
    private String beneeBirt; //受益人出生日期
    private String benSex; //受益人性別
    private String benEvtRel; //受益人與事故者關係
    private String commTel; //電話1
    private String mobile; //電話2
    private String commType; //通訊地址別
    private String commZip; //通訊郵遞區號
    private String commAddr; //通訊地址
    private String payType; //給付方式 國勞不一致(待釐清)
    private String payBankId; //金融機構總代號
    private String branchId; //分支代號
    private String payEeacc; //銀行帳號
    private String accIdn; //戶名IDN
    private String accName; //戶名
    private String accRel; // 戶名與受益人關係
    private String grdIdnNo; //法定代理人身分證號
    private String grdName; //法定代理人姓名
    private String grdBirth; //法定代理人出生日期
    private String handIcApDt; // 手冊鑑定日期
    private String diaDabDt;
    

    // Field not in BAAPPBASE
    // [
    private String chkDt;// 核定日期
    private String aplPayDate;// 帳務日期
    private BigDecimal valSeni; // 國保年資 月
    private BigDecimal valSenid; // 國保年資 日
    //private BigDecimal issueAmt;// 核定金額
    private BigDecimal sagtotAmt;// 代扣保費金額
    private BigDecimal itrtAmt;// 利息(20091023新增)(代扣需加此欄方為代扣保費總和)
    private BigDecimal recAmt;// 收回金額
    private BigDecimal supAmt;// 補發金額
    private BigDecimal cutAmt;// 減領金額
    private BigDecimal otherAmt;// 另案扣減金額
    private BigDecimal aplPayAmt;// 實付金額
    //private String manChkFlg;// 人工審核結果

    // ]

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
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

    public String getEvteeName() {
        return evteeName;
    }

    public void setEvteeName(String evteeName) {
        this.evteeName = evteeName;
    }

    public String getEvtDt() {
        return evtDt;
    }

    public void setEvtDt(String evtDt) {
        this.evtDt = evtDt;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }

    public String getAcceptMark() {
        return acceptMark;
    }

    public void setAcceptMark(String acceptMark) {
        this.acceptMark = acceptMark;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getHandIcApDt() {
        return handIcApDt;
    }

    public void setHandIcApDt(String handIcApDt) {
        this.handIcApDt = handIcApDt;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getPayYme() {
        return payYme;
    }

    public void setPayYme(String payYme) {
        this.payYme = payYme;
    }

    public String getLabMerge() {
        return labMerge;
    }

    public void setLabMerge(String labMerge) {
        this.labMerge = labMerge;
    }

    public String getDabLevel() {
        return dabLevel;
    }

    public void setDabLevel(String dabLevel) {
        this.dabLevel = dabLevel;
    }

    public String getDabType() {
        return dabType;
    }

    public void setDabType(String dabType) {
        this.dabType = dabType;
    }

    public String getDabPart() {
        return dabPart;
    }

    public void setDabPart(String dabPart) {
        this.dabPart = dabPart;
    }

    public BigDecimal getValSeni() {
        return valSeni;
    }

    public void setValSeni(BigDecimal valSeni) {
        this.valSeni = valSeni;
    }

    public BigDecimal getSagtotAmt() {
        return sagtotAmt;
    }

    public void setSagtotAmt(BigDecimal sagtotAmt) {
        this.sagtotAmt = sagtotAmt;
    }

    public BigDecimal getItrtAmt() {
        return itrtAmt;
    }

    public void setItrtAmt(BigDecimal itrtAmt) {
        this.itrtAmt = itrtAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
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

	public String getEvteeBirt() {
		return evteeBirt;
	}

	public void setEvteeBirt(String evteeBirt) {
		this.evteeBirt = evteeBirt;
	}

	public String getEvtSex() {
		return evtSex;
	}

	public void setEvtSex(String evtSex) {
		this.evtSex = evtSex;
	}

	public String getEvtIdent() {
		return evtIdent;
	}

	public void setEvtIdent(String evtIdent) {
		this.evtIdent = evtIdent;
	}

	public String getDieDate() {
		return dieDate;
	}

	public void setDieDate(String dieDate) {
		this.dieDate = dieDate;
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

	public String getBeneeName() {
		return beneeName;
	}

	public void setBeneeName(String beneeName) {
		this.beneeName = beneeName;
	}

	public String getBeneeBirt() {
		return beneeBirt;
	}

	public void setBeneeBirt(String beneeBirt) {
		this.beneeBirt = beneeBirt;
	}

	public String getBenSex() {
		return benSex;
	}

	public void setBenSex(String benSex) {
		this.benSex = benSex;
	}

	public String getBenEvtRel() {
		return benEvtRel;
	}

	public void setBenEvtRel(String benEvtRel) {
		this.benEvtRel = benEvtRel;
	}

	public String getCommTel() {
		return commTel;
	}

	public void setCommTel(String commTel) {
		this.commTel = commTel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getCommZip() {
		return commZip;
	}

	public void setCommZip(String commZip) {
		this.commZip = commZip;
	}

	public String getCommAddr() {
		return commAddr;
	}

	public void setCommAddr(String commAddr) {
		this.commAddr = commAddr;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPayEeacc() {
		return payEeacc;
	}

	public void setPayEeacc(String payEeacc) {
		this.payEeacc = payEeacc;
	}

	public String getAccIdn() {
		return accIdn;
	}

	public void setAccIdn(String accIdn) {
		this.accIdn = accIdn;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccRel() {
		return accRel;
	}

	public void setAccRel(String accRel) {
		this.accRel = accRel;
	}

	public String getGrdIdnNo() {
		return grdIdnNo;
	}

	public void setGrdIdnNo(String grdIdnNo) {
		this.grdIdnNo = grdIdnNo;
	}

	public String getGrdName() {
		return grdName;
	}

	public void setGrdName(String grdName) {
		this.grdName = grdName;
	}

	public String getGrdBirth() {
		return grdBirth;
	}

	public void setGrdBirth(String grdBirth) {
		this.grdBirth = grdBirth;
	}

	public String getDiaDabDt() {
		return diaDabDt;
	}

	public void setDiaDabDt(String diaDabDt) {
		this.diaDabDt = diaDabDt;
	}

	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	public BigDecimal getValSenid() {
		return valSenid;
	}

	public void setValSenid(BigDecimal valSenid) {
		this.valSenid = valSenid;
	}
	
	

}
