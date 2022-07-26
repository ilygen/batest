package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>)
 * 
 * @author Rickychi
 */
@Table("BAFAMILYTEMP")
public class Bafamilytemp implements Serializable {
    @PkeyField("BAFAMILYTEMPID")
    private BigDecimal bafamilytempId;// 資料列編號

    @PkeyField("SEQNO")
    private String seqNo;// 序號

    private String famAppDate;// 遺屬/眷屬申請日期
    private String famIdnNo;// 遺屬/眷屬身分證號
    private String famName;// 遺屬/眷屬姓名
    private String famBrDate;// 遺屬/眷屬出生日期
    private String famSex;// 遺屬/眷屬性別
    private String famNationTyp;// 遺屬/眷屬國籍別
    private String famNationCode;// 遺屬/眷屬國籍
    private String famEvtRel;// 遺屬/眷屬與事故者關係
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String payTyp;// 給付方式
    private String bankName;// 金融機構名稱
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號
    private String chkPayBankId;// 金融機構總代號 複驗
    private String chkBranchId;// 分支代號 複驗
    private String chkPayEeacc;// 銀行帳號 複驗
    private String accIdn;// 戶名IDN
    private String accName;// 戶名
    private String accRel;// 戶名與受益人關係
    private String accSeqNo;// 被共同具領之受款人員序號
    private BigDecimal mitRate;// 匯款匯費
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String monIncomeMk;// 每月工作收入註記
    private BigDecimal monIncome;// 每月工作收入
    private String raiseChildMk;// 配偶扶養註記
    private String marryDate;// 結婚日期
    private String interDictMk;// 受禁治產(監護)宣告
    private String handIcapMk;// 領有重度以上身心障礙手冊或證明註記
    private String studMk;// 在學註記
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String mobilePhone; // 手機複驗
    private String schoolCode;// 學校代碼
    private String adoPtDate;// 收養日期
    private String raiseEvtMk;// 被保險人扶養
    private String specialAcc;// 專戶
    private String benMarrMk;// 婚姻狀況
    private String savingMk;// 計息存儲
    private String assignName;// 代辦人姓名
    private String assignIdnNo;// 代辦人身分證號
    private String assignBrDate;// 代辦人出生日期
    private String checkMk;// 它系統轉入檢核註記(檢核完成=Y)
    
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public BigDecimal getBafamilytempId() {
        return bafamilytempId;
    }

    public void setBafamilytempId(BigDecimal bafamilytempId) {
        this.bafamilytempId = bafamilytempId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
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

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
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

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

	public String getChkPayBankId() {
		return chkPayBankId;
	}

	public void setChkPayBankId(String chkPayBankId) {
		this.chkPayBankId = chkPayBankId;
	}

	public String getChkBranchId() {
		return chkBranchId;
	}

	public void setChkBranchId(String chkBranchId) {
		this.chkBranchId = chkBranchId;
	}

	public String getChkPayEeacc() {
		return chkPayEeacc;
	}

	public void setChkPayEeacc(String chkPayEeacc) {
		this.chkPayEeacc = chkPayEeacc;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getAdoPtDate() {
		return adoPtDate;
	}

	public void setAdoPtDate(String adoPtDate) {
		this.adoPtDate = adoPtDate;
	}

	public String getRaiseEvtMk() {
		return raiseEvtMk;
	}

	public void setRaiseEvtMk(String raiseEvtMk) {
		this.raiseEvtMk = raiseEvtMk;
	}

	public String getSpecialAcc() {
		return specialAcc;
	}

	public void setSpecialAcc(String specialAcc) {
		this.specialAcc = specialAcc;
	}

	public String getBenMarrMk() {
		return benMarrMk;
	}

	public void setBenMarrMk(String benMarrMk) {
		this.benMarrMk = benMarrMk;
	}

	public String getSavingMk() {
		return savingMk;
	}

	public void setSavingMk(String savingMk) {
		this.savingMk = savingMk;
	}

	public String getAssignName() {
		return assignName;
	}

	public void setAssignName(String assignName) {
		this.assignName = assignName;
	}

	public String getAssignIdnNo() {
		return assignIdnNo;
	}

	public void setAssignIdnNo(String assignIdnNo) {
		this.assignIdnNo = assignIdnNo;
	}

	public String getAssignBrDate() {
		return assignBrDate;
	}

	public void setAssignBrDate(String assignBrDate) {
		this.assignBrDate = assignBrDate;
	}

	public String getCheckMk() {
		return checkMk;
	}

	public void setCheckMk(String checkMk) {
		this.checkMk = checkMk;
	}

}
