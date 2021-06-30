package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 遺屬年金編審註記程度調整
 * 
 * @author Goston
 */
public class SurvivorCheckMarkLevelAdjustCase implements Serializable {
    // Header 資料
    private String apNo; // 受理編號
    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String evtJobDate; // 事故日期
    private String issuYm; // 核定年月
    private String appDate; // 申請日期
    private String procStat; // 處理狀態
    private String caseTyp; // 案件類別
    private String regetCipbMk; //重讀CIPB

    // 受款人資料
    private List<SurvivorCheckMarkLevelAdjustBenDataCase> benList;

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

    /**
     * 事故者出生日期
     * 
     * @return
     */
    public String getEvtBrDateString() {
        return DateUtility.formatChineseDateString(evtBrDate, false);
    }

    /**
     * 事故日期
     * 
     * @return
     */
    public String getEvtJobDateString() {
        return DateUtility.formatChineseDateString(evtJobDate, false);
    }

    /**
     * 核定年月
     * 
     * @return
     */
    public String getIssuYmString() {
        return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false);
    }

    /**
     * 申請日期
     * 
     * @return
     */
    public String getAppDateString() {
        return DateUtility.formatChineseDateString(appDate, false);
    }

    /**
     * 處理狀態
     * 
     * @return
     */
    public String getProcStatString() {
        if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_00))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_01))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_10))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_11))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_12))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_13))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_20))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_30))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_40))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_50))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_80))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_80;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_90))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_99))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
        else
            return "";
    }

    /**
     * 案件類別
     * 
     * @return
     */
    public String getCaseTypString() {
        if (StringUtils.equals(caseTyp, ConstantKey.BAAPPBASE_CASETYP_1))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if (StringUtils.equals(caseTyp, ConstantKey.BAAPPBASE_CASETYP_2))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if (StringUtils.equals(caseTyp, ConstantKey.BAAPPBASE_CASETYP_3))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if (StringUtils.equals(caseTyp, ConstantKey.BAAPPBASE_CASETYP_4))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if (StringUtils.equals(caseTyp, ConstantKey.BAAPPBASE_CASETYP_5))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else
            return "";
    }

    /**
     * 新增 受款人資料
     * 
     * @param benData
     */
    public void addBenData(SurvivorCheckMarkLevelAdjustBenDataCase benData) {
        if (benList == null)
            benList = new ArrayList<SurvivorCheckMarkLevelAdjustBenDataCase>();

        benList.add(benData);
    }

    /**
     * 檢查案件是否含有編審資料
     * 
     * @return
     */
    public boolean hasCheckFileData() {
        for (SurvivorCheckMarkLevelAdjustBenDataCase benData : benList) {
            if (benData.getDetailList() != null && benData.getDetailList().size() > 0)
                return true;
        }

        return false;
    }

    /**
     * 依傳入的 資料列編號 在 benList 中 <br>
     * 取出符合的該 資料列編號 的 SurvivorCheckMarkLevelAdjustBenDetailDataCase
     * 
     * @param baChkFileId 資料列編號
     * @return
     */
    public SurvivorCheckMarkLevelAdjustBenDetailDataCase searchBenDetailByKey(String baChkFileId) {
        if (StringUtils.isBlank(baChkFileId))
            return null;

        for (SurvivorCheckMarkLevelAdjustBenDataCase benData : benList) {
            for (SurvivorCheckMarkLevelAdjustBenDetailDataCase benDetailData : benData.getDetailList()) {
                if (StringUtils.equals(benDetailData.getBaChkFileId().toPlainString(), baChkFileId))
                    return benDetailData;
            }
        }

        return null;
    }

    /**
     * 受款人資料 筆數
     * 
     * @return
     */
    public int getBenListSize() {
        if (benList != null)
            return benList.size() - 1; // 第一筆為事故者, 遺屬年金受款人的部份不列事故者 故須 - 1
        else
            return 0;
    }

    /**
     * 設定 編審註記資料 畫面輸入欄位之值
     * 
     * @param baChkFileId 資料列編號
     * @param adjLevel 程度調整
     * @param valisYm 有效年月 - 起
     * @param valieYm 有效年月 - 迄
     */
    public void setDetailListInputData(String baChkFileId, String adjLevel, String valisYm, String valieYm) {
        for (SurvivorCheckMarkLevelAdjustBenDataCase benData : benList) {
            for (SurvivorCheckMarkLevelAdjustBenDetailDataCase benDetailData : benData.getDetailList()) {
                if (StringUtils.equals(benDetailData.getBaChkFileId().toPlainString(), baChkFileId)) {
                    benDetailData.setAdjLevel(adjLevel);
                    benDetailData.setValisYm(valisYm);
                    benDetailData.setValieYm(valieYm);
                }
            }
        }
    }

    /**
     * 取得 受款人資料 中的事故者資料
     * 
     * @return
     */
    public SurvivorCheckMarkLevelAdjustBenDataCase getEvtDataInBenList() {
        if (benList == null || benList.size() == 0)
            return null; // 不可能發生

        for (SurvivorCheckMarkLevelAdjustBenDataCase benData : benList) {
            if (StringUtils.equals(benData.getSeqNo(), "0000"))
                return benData;
        }

        return null; // 不可能發生
    }

    /**
     * 取得 受款人資料 中的 不含 事故者的資料
     * 
     * @return
     */
    public List<SurvivorCheckMarkLevelAdjustBenDataCase> getBenDataInBenList() {
        if (benList == null || benList.size() <= 1) {
            return new ArrayList<SurvivorCheckMarkLevelAdjustBenDataCase>();
        }
        else {
            return benList.subList(1, benList.size()); // 第一筆為事故者, 遺屬年金受款人的部份不列事故者
        }
    }

    public SurvivorCheckMarkLevelAdjustCase() {
        benList = new ArrayList<SurvivorCheckMarkLevelAdjustBenDataCase>();
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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
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

    public List<SurvivorCheckMarkLevelAdjustBenDataCase> getBenList() {
        return benList;
    }

    public void setBenList(List<SurvivorCheckMarkLevelAdjustBenDataCase> benList) {
        this.benList = benList;
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
