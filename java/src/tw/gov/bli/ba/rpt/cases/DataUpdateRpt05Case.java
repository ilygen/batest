package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.common.annotation.LogField;

public class DataUpdateRpt05Case implements Serializable {
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String benEvtRel;// 受益人與事故者關係
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String evtName;// 事故者姓名
    private String benName;// 受益人姓名
    private String benIdnNo;// 受益人身分證號
    private String benBrDate;// 受益人出生日期
    private String payTyp;// 給付方式
    private String bankName;// 金融機構名稱
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號
    private String bureauAcc; // 局帳號
    
    // 給付檔資料
    private BigDecimal sumAplpayAmt; //實付金額
    private BigDecimal aplpayAmt; //受款人實付金額
    private String aplpayDate; // 帳務日期
    private String stexpndDate; //止付日期
    private String stexpndReason; //止付原因(01-撤銷、02-基本資料錯誤、03-帳號錯誤、04-改核不給付、05-核定金額異動、06-已死亡、07-更改給付方式、99-其他)
    private String chkDate;
    
    private String idn_Aply; // 身份證號
    private String name_Aply; // 姓名
    private String account_Pay; // 放款帳號

    private String fStyleForKS; //K,S案件是否比照老年年金受款人關係F的格式產生止付單
    private String peopleCount;
    
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
    public String getBenEvtRel() {
        return benEvtRel;
    }
    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }
    public String getIssuYm() {
        return issuYm;
    }
    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }
    public String getEvtName() {
        return evtName;
    }
    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }
    public String getBenName() {
        return benName;
    }
    public void setBenName(String benName) {
        this.benName = benName;
    }
    public String getBenIdnNo() {
        return benIdnNo;
    }
    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }
    public String getBenBrDate() {
        return benBrDate;
    }
    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
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
    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }
    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }
    public String getStexpndReason() {
        return stexpndReason;
    }
    public void setStexpndReason(String stexpndReason) {
        this.stexpndReason = stexpndReason;
    }
    public String getChkDate() {
        return chkDate;
    }
    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }
    public String getStexpndDate() {
        return stexpndDate;
    }
    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }    
    public String getIdn_Aply() {
        return idn_Aply;
    }
    public void setIdn_Aply(String idn_Aply) {
        this.idn_Aply = idn_Aply;
    }
    public String getAccount_Pay() {
        return account_Pay;
    }
    public void setAccount_Pay(String account_Pay) {
        this.account_Pay = account_Pay;
    }
    public String getName_Aply() {
        return name_Aply;
    }
    public void setName_Aply(String name_Aply) {
        this.name_Aply = name_Aply;
    }
    public String getBureauAcc() {
        return StringUtils.defaultString(bureauAcc);
    }
    public void setBureauAcc(String bureauAcc) {
        this.bureauAcc = bureauAcc;
    }
    public String getPayCodeStr() {
        if(apNo.equals("") || apNo.equals(null)){
            return "";
        } else {
            if(apNo.substring(0, 1).equals("L") )
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
            else if(apNo.substring(0, 1).equals("K"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
            else if(apNo.substring(0, 1).equals("S"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
            else
                return "";          
        }
    }
    public String getStexpndDateString() {
        return DateUtility.formatChineseDateString(DateUtility.changeDateType(stexpndDate), true);
    }
    public String getAplpayDateString() {
        return DateUtility.formatChineseDateString(DateUtility.changeDateType(aplpayDate), true);
    }
    public String getIssuYmString() {
        return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), true);
    }
    public String getPayYmString() {
        return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), true);
    }
    public String getIssuYmStr() {
        return DateUtility.changeWestYearMonthType(issuYm);
    }
    public String getPayYmStr() {
        return DateUtility.changeWestYearMonthType(payYm);
    }
    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    public String getDeptNameString() {
        String payKindCode = StringUtils.substring(apNo, 0, 1);

        if (StringUtils.equalsIgnoreCase(payKindCode, "L"))
            return "老年";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "K"))
            return "失能";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "S"))
            return "死亡";
        else
            return "";
    }
    public String getStexpndReasonStr() {
        if(stexpndReason.equals("") || stexpndReason.equals(null)){
            return "";
        } else {
            if(stexpndReason.equals("01"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_01;
            else if(stexpndReason.equals("02"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_02;
            else if(stexpndReason.equals("03"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_03;
            else if(stexpndReason.equals("04"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_04;
            else if(stexpndReason.equals("05"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_05;
            else if(stexpndReason.equals("06"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_06;
            else if(stexpndReason.equals("07"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_07;
            else if(stexpndReason.equals("99"))
                return ConstantKey.BAAPPBASE_STEXPNDREASON_STR_99;
            else
                return "";          
        }
    }
    public String getAplpayDate() {
        return aplpayDate;
    }
    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }
    public BigDecimal getSumAplpayAmt() {
        return sumAplpayAmt;
    }
    public void setSumAplpayAmt(BigDecimal sumAplpayAmt) {
        this.sumAplpayAmt = sumAplpayAmt;
    }
	public String getPayYm() {
		return payYm;
	}
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}
	public String getfStyleForKS() {
		return fStyleForKS;
	}
	public void setfStyleForKS(String fStyleForKS) {
		this.fStyleForKS = fStyleForKS;
	}
	public String getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(String peopleCount) {
		this.peopleCount = peopleCount;
	}
    
}
