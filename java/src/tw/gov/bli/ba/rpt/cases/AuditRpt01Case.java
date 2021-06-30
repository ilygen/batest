package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 逾期未決行案件管制表
 * 
 * @author Noctis
 */
public class AuditRpt01Case implements Serializable {

    private String endYm;// 截止年月
    private String appDate;// 申請日期
    private String crtTime;// 新增日期時間
    private String apNo;// 受理編號
    private String evtName;// 事故者姓名
    private String procStat;// 處理狀態
    private String lastDate;// 截止年月
    private String lastUser;// 截止年月
    private String leterType;// 行政支援函別
    private String leterTypeStr;// 行政支援函別 中文
    private String ndomk1; // 處理註記
    private String ndomk1Str; // 處理註記 中文
    private String caseTyp;// 案件類別
    private String chkMan;// 審核人員
    private String apItem;// 申請項目
    private String evtIdnNo;// 事故者身分證號
    private String chkList;// 本次編審註記列表
    private String preChkList;// 前次編審註記列表

    /**
     * 案件類別
     */
    public String getCaseTypString() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else
            return "";
    }

    /**
     * 處理狀態
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
     * 申請項目
     */
    public String getApItemStr() {
        String apItemStr = "";
        if (StringUtils.isNotBlank(getApItem())) {
            // 轉換 申請項目
            if ((ConstantKey.BAAPPBASE_APITEM_1).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_2).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_2;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_3).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_3;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_4).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_4;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_5).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_5;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_7).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_7;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_8).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_8;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_9).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_9;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_0).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_0;
            }
        }
        return apItemStr;
    }

    public String getEndYm() {
        return endYm;
    }

    public void setEndYm(String endYm) {
        this.endYm = endYm;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
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

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public String getLeterType() {
        return leterType;
    }

    public void setLeterType(String leterType) {
        this.leterType = leterType;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getNdomk1Str() {
        return ndomk1Str;
    }

    public void setNdomk1Str(String ndomk1Str) {
        this.ndomk1Str = ndomk1Str;
    }

    public String getLeterTypeStr() {
        return leterTypeStr;
    }

    public void setLeterTypeStr(String leterTypeStr) {
        this.leterTypeStr = leterTypeStr;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getChkList() {
        return chkList;
    }

    public void setChkList(String chkList) {
        this.chkList = chkList;
    }

    public String getPreChkList() {
        return preChkList;
    }

    public void setPreChkList(String preChkList) {
        this.preChkList = preChkList;
    }
}
