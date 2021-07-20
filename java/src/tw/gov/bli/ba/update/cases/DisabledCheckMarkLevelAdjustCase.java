package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金編審註記程度調整
 * 
 * @author Goston
 */
public class DisabledCheckMarkLevelAdjustCase implements Serializable {
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
    private String regetCipbMk; // 重讀CIPB
    private BigDecimal baappbaseId;
    private String appDateJsp; // 申請日期 畫面使用
    private String evtJobDateJsp; // 事故日期 畫面使用
    private String payKind;// 給付種類
    
    // 受款人資料
    private List<DisabledCheckMarkLevelAdjustBenDataCase> benList;

    // 眷屬資料
    private List<DisabledCheckMarkLevelAdjustFamDataCase> famList;

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
    public void addBenData(DisabledCheckMarkLevelAdjustBenDataCase benData) {
        if (benList == null)
            benList = new ArrayList<DisabledCheckMarkLevelAdjustBenDataCase>();

        benList.add(benData);
    }

    /**
     * 新增 眷屬資料
     * 
     * @param famData
     */
    public void addFamData(DisabledCheckMarkLevelAdjustFamDataCase famData) {
        if (famList == null)
            famList = new ArrayList<DisabledCheckMarkLevelAdjustFamDataCase>();

        famList.add(famData);
    }

    /**
     * 檢查案件是否含有編審資料
     * 
     * @return
     */
    public boolean hasCheckFileData() {
        for (DisabledCheckMarkLevelAdjustBenDataCase benData : benList) {
            if (benData.getDetailList() != null && benData.getDetailList().size() > 0)
                return true;
        }

        for (DisabledCheckMarkLevelAdjustFamDataCase famData : famList) {
            if (famData.getDetailList() != null && famData.getDetailList().size() > 0)
                return true;
        }

        return false;
    }

    /**
     * 依傳入的 資料列編號 在 benList 中 <br>
     * 取出符合的該 資料列編號 的 DisabledCheckMarkLevelAdjustBenDetailDataCase
     * 
     * @param baChkFileId 資料列編號
     * @return
     */
    public DisabledCheckMarkLevelAdjustBenDetailDataCase searchBenDetailByKey(String baChkFileId) {
        if (StringUtils.isBlank(baChkFileId))
            return null;

        for (DisabledCheckMarkLevelAdjustBenDataCase benData : benList) {
            for (DisabledCheckMarkLevelAdjustBenDetailDataCase benDetailData : benData.getDetailList()) {
                if (StringUtils.equals(benDetailData.getBaChkFileId().toPlainString(), baChkFileId))
                    return benDetailData;
            }
        }

        return null;
    }

    /**
     * 依傳入的 資料列編號 在 famList 中 <br>
     * 取出符合的該 資料列編號 的 DisabledCheckMarkLevelAdjustFamDetailDataCase
     * 
     * @param baChkFileId 資料列編號
     * @return
     */
    public DisabledCheckMarkLevelAdjustFamDetailDataCase searchFamDetailByKey(String baChkFileId) {
        if (StringUtils.isBlank(baChkFileId))
            return null;

        for (DisabledCheckMarkLevelAdjustFamDataCase famData : famList) {
            for (DisabledCheckMarkLevelAdjustFamDetailDataCase famDetailData : famData.getDetailList()) {
                if (StringUtils.equals(famDetailData.getBaChkFileId().toPlainString(), baChkFileId))
                    return famDetailData;
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
            return benList.size();
        else
            return 0;
    }

    /**
     * 眷屬資料 筆數
     * 
     * @return
     */
    public int getFamListSize() {
        if (famList != null)
            return famList.size();
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
        boolean bExist = false;

        for (DisabledCheckMarkLevelAdjustBenDataCase benData : benList) {
            for (DisabledCheckMarkLevelAdjustBenDetailDataCase benDetailData : benData.getDetailList()) {
                if (StringUtils.equals(benDetailData.getBaChkFileId().toPlainString(), baChkFileId)) {
                    benDetailData.setAdjLevel(adjLevel);
                    benDetailData.setValisYm(valisYm);
                    benDetailData.setValieYm(valieYm);
                    bExist = true;
                }
            }
        }

        if (!bExist) {
            for (DisabledCheckMarkLevelAdjustFamDataCase famData : famList) {
                for (DisabledCheckMarkLevelAdjustFamDetailDataCase famDetailData : famData.getDetailList()) {
                    if (StringUtils.equals(famDetailData.getBaChkFileId().toPlainString(), baChkFileId)) {
                        famDetailData.setAdjLevel(adjLevel);
                        famDetailData.setValisYm(valisYm);
                        famDetailData.setValieYm(valieYm);
                        bExist = true;
                    }
                }
            }
        }
    }

    public DisabledCheckMarkLevelAdjustCase() {
        benList = new ArrayList<DisabledCheckMarkLevelAdjustBenDataCase>();
        famList = new ArrayList<DisabledCheckMarkLevelAdjustFamDataCase>();
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

    public List<DisabledCheckMarkLevelAdjustBenDataCase> getBenList() {
        return benList;
    }

    public void setBenList(List<DisabledCheckMarkLevelAdjustBenDataCase> benList) {
        this.benList = benList;
    }

    public List<DisabledCheckMarkLevelAdjustFamDataCase> getFamList() {
        return famList;
    }

    public void setFamList(List<DisabledCheckMarkLevelAdjustFamDataCase> famList) {
        this.famList = famList;
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

	public BigDecimal getBaappbaseId() {
		return baappbaseId;
	}

	public void setBaappbaseId(BigDecimal baappbaseId) {
		this.baappbaseId = baappbaseId;
	}

	public String getAppDateJsp() {
		return appDateJsp;
	}

	public void setAppDateJsp(String appDateJsp) {
		this.appDateJsp = appDateJsp;
	}

	public String getEvtJobDateJsp() {
		return evtJobDateJsp;
	}

	public void setEvtJobDateJsp(String evtJobDateJsp) {
		this.evtJobDateJsp = evtJobDateJsp;
	}

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }    

}
