package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.domain.Bachkfile;

public class CheckMarkLevelAdjustCase implements Serializable {
    private BigDecimal baappbaseId;// 給付主檔資料編號
    private String apNo;// 受理編號
    private String formatApNo;// 受理編號輸出格式
    private String kind;// 給付種類FOR頁面
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtIdnNo;// 事故者身分證號
    private String procStat;// 處理狀態
    private String caseTyp;// 案件類別
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String appDate;//申請日期
    private String evtJobDate;//事故者離職日
    private String regetCipbMk; //重讀CIPB
    private List<ArrayList<Bachkfile>> detailList = new ArrayList<ArrayList<Bachkfile>>();// 清單資料
    private List<String> payYmList = new ArrayList<String>();//編審註記：依給付年月排序 說明資料
    private List<Bachkfile> seqnoList = new ArrayList<Bachkfile>();//編審註記：依受款人排序 說明資料
    private List<String> markList = new ArrayList<String>();//編審註記：依編審註記排序 說明資料
    
    
    public String getApNo1Str() {
        if(StringUtils.isNotBlank(getApNo())&&getApNo().length()>=1){
            return getApNo().substring(0, 1);
        }else{
            return "";
        }
    }
    
    public String getApNo2Str() {
        if(StringUtils.isNotBlank(getApNo())&&getApNo().length()>=2){
            return getApNo().substring(1, 2);
        }else{
            return "";
        }
    }
    
    public String getApNo3Str() {
        if(StringUtils.isNotBlank(getApNo())&&getApNo().length()>=7){
            return getApNo().substring(2, 7);
        }else{
            return "";
        }
    }
    
    public String getApNo4Str() {
        if(StringUtils.isNotBlank(getApNo())&&getApNo().length()>=12){
            return getApNo().substring(7, 12);
        }else{
            return "";
        }
    }

    public String getChineseAppDate(){
        if (StringUtils.isNotBlank(appDate) && (appDate.length() > 7)) {
            return DateUtility.changeDateType(appDate);
        }
        return appDate;
    }
    public String getChinesePayYm() {
            return DateUtility.changeWestYearMonthType(payYm);
    }
    
    //事故者離職日
    public String getEvtJobDateStr(){
        if (StringUtils.isNotBlank(evtJobDate)&&(evtJobDate.length())>7){
            return DateUtility.changeDateType(evtJobDate);
        }
        return evtJobDate;
    }

    public String getChineseIssuYm() {
            return DateUtility.changeWestYearMonthType(issuYm);
    }

    public String getChineseEvtBrDate() {
        if (StringUtils.isNotBlank(evtBrDate) && (evtBrDate.length() > 7 )) {
            return DateUtility.changeDateType(evtBrDate);
        }
        return evtBrDate;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getFormatApNo() {
        return formatApNo;
    }

    public void setFormatApNo(String formatApNo) {
        this.formatApNo = formatApNo;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
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

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public List<ArrayList<Bachkfile>> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<ArrayList<Bachkfile>> detailList) {
        this.detailList = detailList;
    }

    public List<String> getPayYmList() {
        return payYmList;
    }

    public void setPayYmList(List<String> payYmList) {
        this.payYmList = payYmList;
    }

    public List<Bachkfile> getSeqnoList() {
        return seqnoList;
    }

    public void setSeqnoList(List<Bachkfile> seqnoList) {
        this.seqnoList = seqnoList;
    }

    public List<String> getMarkList() {
        return markList;
    }

    public void setMarkList(List<String> markList) {
        this.markList = markList;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }
    
    public String getRegetCipbMk() {
        return regetCipbMk;
    }

    public void setRegetCipbMk(String regetCipbMk) {
        this.regetCipbMk = regetCipbMk;
    }    

}
