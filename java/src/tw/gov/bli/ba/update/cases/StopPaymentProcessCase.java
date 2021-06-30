package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.update.cases.StopPaymentProcessDetailCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.common.util.DateUtil;

public class StopPaymentProcessCase implements Serializable {
    private String apNo; // 受理編號
    private String appDate; // 申請日期
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String evtIdnNo; // 事故者身分證號
    private String procStat; // 處理狀態
    private String caseTyp; // 資料別
    private BigDecimal befIssueAmt; // 核定金額
    private String promoteUser; // 承辦人員
    private String payTyp; // 給付方式
    
    private String issuYm; // 核定年月
    private BigDecimal aplpayAmt; // 合計實付金額
    private String aplpayDate; // 核付日期
    private String payEndYm; // 給付年月(迄)
    private String payBegYm; // 給付年月(起)
    private String stexpndMk; // 止付註記
    private String stexpndReason; // 止付原因
    private String stexpndDate; // 止付日期
    private String btnUpdate; // 控制存檔按紐是否可以按
    private String evtJobDate; //事故者離職日
    
    private List<StopPaymentProcessDetailCase> applyList; // 給付核定檔
    private List<String> payYmList;//編審註記：依給付年月排序 說明資料
    
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
    public String getEvtBrDate() {
        return evtBrDate;
    }
    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }
    public String getEvtIdnNo() {
        return evtIdnNo;
    }
    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }
    public String getIssuYm() {
        return issuYm;
    }
    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }
    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }
    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }
    public String getStexpndMk() {
        return stexpndMk;
    }
    public void setStexpndMk(String stexpndMk) {
        this.stexpndMk = stexpndMk;
    }
    public String getProcStat() {
        return procStat;
    }
    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }
    public String getPromoteUser() {
        return promoteUser;
    }
    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }
    public String getStexpndReason() {
        return stexpndReason;
    }
    public void setStexpndReason(String stexpndReason) {
        this.stexpndReason = stexpndReason;
    }
    public String getStexpndDate() {
        return stexpndDate;
    }
    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }
    
    //事故者離職日
    public String getEvtJobDateStr(){
        if (StringUtils.isNotBlank(evtJobDate)&&(evtJobDate.length())>7){
            return DateUtility.changeDateType(evtJobDate);
        }
        return evtJobDate;
    }
    
    public String getPayCode() {
        if(apNo.substring(0, 1).equals("L") )
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        else if(apNo.substring(0, 1).equals("K"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        else if(apNo.substring(0, 1).equals("S"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        else
            return "";        
    }
    
    public String getProcStatName() {
        if(procStat.equals("00"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
        else if(procStat.equals("01"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
        else if(procStat.equals("10"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
        else if(procStat.equals("11"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
        else if(procStat.equals("12"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
        else if(procStat.equals("13"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
        else if(procStat.equals("20"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
        else if(procStat.equals("30"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
        else if(procStat.equals("40"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
        else if(procStat.equals("50"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
        else if(procStat.equals("90"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
        else if(procStat.equals("99"))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
        else 
            return "";
    }
    public String getAppDateChineseString() {
        if(StringUtils.isNotEmpty(appDate))
            return DateUtil.changeDateType(appDate);
        else
            return "";
    }
    public String getEvtBrDateChineseString() {
        if(StringUtils.isNotEmpty(evtBrDate))
            return DateUtil.changeDateType(evtBrDate);
        else
            return "";
    }
    public String getStexpndDateChineseString() {
        if(StringUtils.isNotEmpty(stexpndDate))
            return DateUtil.changeDateType(stexpndDate);
        else
            return "";
    }
    public String getIssuYmChineseString() {
        if(StringUtils.isNotEmpty(issuYm))
            return DateUtil.changeDateType(issuYm);
        else
            return "";
    }
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    public String getCaseTyp() {
        return caseTyp;
    }
    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    public String getAplpayDate() {
        return aplpayDate;
    }
    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }
    public String getPayEndYm() {
        return payEndYm;
    }
    public void setPayEndYm(String payEndYm) {
        this.payEndYm = payEndYm;
    }
    public String getPayBegYm() {
        return payBegYm;
    }
    public void setPayBegYm(String payBegYm) {
        this.payBegYm = payBegYm;
    }
    public List<StopPaymentProcessDetailCase> getApplyList() {
        return applyList;
    }
    public void setApplyList(List<StopPaymentProcessDetailCase> applyList) {
        this.applyList = applyList;
    }
    public List<String> getPayYmList() {
        return payYmList;
    }
    public void setPayYmList(List<String> payYmList) {
        this.payYmList = payYmList;
    }
    
    public String getPayEndYmStr() {
        if(StringUtils.isNotEmpty(payEndYm))
            return DateUtil.changeWestYearMonthType(payEndYm);
        else
            return "";
    }
    public String getPayBegYmStr() {
        if(StringUtils.isNotEmpty(payBegYm))
            return DateUtil.changeWestYearMonthType(payBegYm);
        else
            return "";
    }
    
    public String getAplpayDateChineseString() {
        if(StringUtils.isNotEmpty(aplpayDate))
            return DateUtil.changeDateType(aplpayDate);
        else
            return "";
    }
    
    public String getCaseTypStr() {
        if(StringUtils.isNotEmpty(caseTyp)){
            if(caseTyp.equals("1"))
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
        else
            return "";
    }
    public String getBtnUpdate() {
        return btnUpdate;
    }
    public void setBtnUpdate(String btnUpdate) {
        this.btnUpdate = btnUpdate;
    }
    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }
    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }
    public String getPayTyp() {
        return payTyp;
    }
    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
    public String getEvtJobDate() {
        return evtJobDate;
    }
    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }
    
    
}
