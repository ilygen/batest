package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 眷屬資料更正 - 事故者資料
 * 
 * @author Evelyn Hsu
 */


public class DependantEvtDataUpdateCase implements Serializable {
    
    private BigDecimal baappbaseId; // 給付主檔資料編號
    private String evtName; // 事故者姓名 
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String appDate;// 申請日期
    private String apNo; //受理編號
    private String evtDieDate; //事故者死亡日期
    private String evtJobDate; //事故日期
    private String issuYm;//核定年月
    private String caseTyp;// 案件類別

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo1(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo2(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(1, 2);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo3(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(2, 7);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo4(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    
    /**
     * 申請日期
     * 
     * @return
     */
    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
    }
    
    public String getAppDateChineseString() {
        return DateUtil.changeDateType(appDate);
    }
    
    /**
     * 事故者出生日期
     * 
     * @return
     */
    public String getEvtBrDateString() {
        if (StringUtils.length(evtBrDate) == 8) {
            String birthday = DateUtility.changeDateType(evtBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "民國前 " : "民國 ") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), true);
        }
        else {
            return StringUtils.defaultString(evtBrDate);
        }
    }
    
    /**
     * 事故者死亡日期
     *  
     * @return
     */
    public String getEvtDieDateStr() {
        if (StringUtils.length(evtDieDate) == 8) {
           
            return DateUtility.changeDateType(evtDieDate);
        }
        else {
            return StringUtils.defaultString(evtDieDate);
        }
    }
    
    /**
     * 事故日期
     *  
     * @return
     */
    public String getEvtJobDateStr() {
        if (StringUtils.length(evtJobDate) == 8) {
           
            return DateUtility.changeDateType(evtJobDate);
        }
        else {
            return StringUtils.defaultString(evtJobDate);
        }
    }
    
    public String getEvtBrDateChineseString() {
        if (StringUtils.length(evtBrDate) == 8) {
            return DateUtil.changeDateType(evtBrDate);
        }
        else {
                return StringUtils.defaultString(evtBrDate);
            }
    }
    
    public String getPayCode() {
        if (apNo.substring(0, 1).equals("L"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        else if (apNo.substring(0, 1).equals("K"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        else if (apNo.substring(0, 1).equals("S"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        else
            return "";
    }
    
    
    
    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }
    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }
    public String getEvtName() {
        return evtName;
    }
    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }
    public String getEvtIdnNo() {
        return evtIdnNo;
    }
    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }
    public String getEvtBrDate() {
        return evtBrDate;
    }
    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }
    public String getAppDate() {
        return appDate;
    }
    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

}
