package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

public class MonthlyBatchRptCase implements Serializable {
    private String rptTyp;// 報表種類
    private String rptDate;// 列印日期
    private String version;// 版次
    private BigDecimal pageNo;// 頁次
    private BigDecimal seqNo;// 序號
    private String payTyp;// 給付別
    private String appDate;// 申請(受理)日期
    private String apNo;// 受理編號
    private String evtName;// 事故者姓名
    private String caseTyp;// 案件類別
    private String paysYm; // 給付年月(起)
    private String payeYm; // 給付年月(迄)
    private BigDecimal tissueAmt;// 合計核定金額
    private BigDecimal taplPayAmt;// 合計實付金額
    private String chkMan;// 審核人員
    private String upCauseCode; // 異動狀況
    private String cprnDate; // 核定清單印表日期
    private String cprnPage; // 核定清單頁次
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String payYm;// 給付年月    
    private String changeYyp;
    private String issuYm;// 核定年月
    public String getRptTyp() {
        return rptTyp;
    }
    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }
    public String getRptDate() {
        return rptDate;
    }
    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public BigDecimal getPageNo() {
        return pageNo;
    }
    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
    }
    public BigDecimal getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }
    public String getPayTyp() {
        return payTyp;
    }
    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
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
    public String getEvtName() {
        return evtName;
    }
    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }
    public String getPaysYm() {
        return paysYm;
    }
    public void setPaysYm(String paysYm) {
        this.paysYm = paysYm;
    }
    public String getPayeYm() {
        return payeYm;
    }
    public void setPayeYm(String payeYm) {
        this.payeYm = payeYm;
    }
    public BigDecimal getTissueAmt() {
        return tissueAmt;
    }
    public void setTissueAmt(BigDecimal tissueAmt) {
        this.tissueAmt = tissueAmt;
    }
    public BigDecimal getTaplPayAmt() {
        return taplPayAmt;
    }
    public void setTaplPayAmt(BigDecimal taplPayAmt) {
        this.taplPayAmt = taplPayAmt;
    }
    public String getChkMan() {
        return chkMan;
    }
    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }
    public String getUpCauseCode() {
        return upCauseCode;
    }
    public void setUpCauseCode(String upCauseCode) {
        this.upCauseCode = upCauseCode;
    }
    public String getCprnDate() {
        return cprnDate;
    }
    public void setCprnDate(String cprnDate) {
        this.cprnDate = cprnDate;
    }
    public String getCprnPage() {
        return cprnPage;
    }
    public void setCprnPage(String cprnPage) {
        this.cprnPage = cprnPage;
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
    public String getPayYm() {
        return payYm;
    }
    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }
    public String getChangeYyp() {
        return changeYyp;
    }
    public void setChangeYyp(String changeYyp) {
        this.changeYyp = changeYyp;
    }
    public String getIssuYm() {
        return issuYm;
    }
    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }
    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    public String getIssuYmStr() {
        if(StringUtils.length(issuYm) == 6)
            return DateUtility.changeWestYearMonthType(issuYm);
        else
            return "";
    }
    public String getUpCauseCodeStr() {
        if(upCauseCode.equals("01") )
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_1;
        else if(upCauseCode.equals("02"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_2;
        else if(upCauseCode.equals("03"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_3;
        else if(upCauseCode.equals("04"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_4;
        else if(upCauseCode.equals("05"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_5;
        else if(upCauseCode.equals("06"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_6;
        else if(upCauseCode.equals("07"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_7;
        else if(upCauseCode.equals("08"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_8;
        else if(upCauseCode.equals("09"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_9;
        else if(upCauseCode.equals("10"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_10;
        else if(upCauseCode.equals("11"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_11;
        else if(upCauseCode.equals("12"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_12;
        else if(upCauseCode.equals("13"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_13;
        else if(upCauseCode.equals("14"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_14;
        else if(upCauseCode.equals("15"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_15;
        else if(upCauseCode.equals("16"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_16;
        else if(upCauseCode.equals("17"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_17;
        else if(upCauseCode.equals("18"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_18;
        else if(upCauseCode.equals("19"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_19;
        else if(upCauseCode.equals("20"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_20;
        else if(upCauseCode.equals("21"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_21;
        else if(upCauseCode.equals("22"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_22;
        else if(upCauseCode.equals("23"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_23;
        else if(upCauseCode.equals("24"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_24;
        else if(upCauseCode.equals("99"))
            return ConstantKey.BAAPPBASE_UPCAUSECODE_STR_99;
        else
            return "";
    }
    public String getAppDateStr() {
        if (StringUtils.length(appDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(appDate), false);
        else
            return "";
    }
    public String getCprnDateStr() {
        if (StringUtils.length(cprnDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(cprnDate), false);
        else
            return "";
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
    public String getCaseTyp() {
        return caseTyp;
    }
    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    public String getPaysYmStr() {
        if(StringUtils.isNotEmpty(paysYm))
            return DateUtility.changeWestYearMonthType(paysYm);
        else
            return "";
    }
    public String getPayeYmStr() {
        if(StringUtils.isNotEmpty(payeYm))
            return DateUtility.changeWestYearMonthType(payeYm);
        else
            return "";
    }
    public String getCaseTypStr() {
        if(caseTyp.equals("1") )
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if(caseTyp.equals("2"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if(caseTyp.equals("3"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if(caseTyp.equals("4"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if(caseTyp.equals("5"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if(caseTyp.equals("6"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else
            return "";
    }
}
