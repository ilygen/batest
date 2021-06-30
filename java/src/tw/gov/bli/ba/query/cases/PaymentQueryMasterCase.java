package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 給付查詢作業 清單資料
 * 
 * @author Rickychi
 */
public class PaymentQueryMasterCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo;// 受理編號
    private String evtName;// 事故者姓名
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String appDate;// 申請日期
    private String pagePayKind;// 給付別(受理編號第一碼)
    private String caseTyp;// 案件類別
    private String procStat;// 處理狀態
    private String payKind;// 給付別
    private String secrecy; // 是否保密
    private String detailLock; // 是否可查看明細資料

    // 頁面顯示轉換
    // [
    public String getProcStatStr() {
        String tmpCaseTypStr = "";
        String tmpProcStatStr = "";

        if (StringUtils.isNotBlank(getProcStat())) {
            String tmpCaseTyp = caseTyp;
            String tmpProcStat = procStat;
            // 轉換 資料別
            if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_2;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_3;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_4;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_5;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_6;
            }
            // 轉換 處理狀態
            if ((ConstantKey.BAAPPBASE_PROCSTAT_00).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_01).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_10).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_12).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_13).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_20).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_50).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_80).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_80;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_90).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_99).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
            }
        }
        return tmpCaseTypStr + "-" + tmpProcStatStr;
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
    }

    // ]

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

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
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

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getSecrecy() {
        return secrecy;
    }

    public void setSecrecy(String secrecy) {
        this.secrecy = secrecy;
    }

    public String getDetailLock() {
        return detailLock;
    }

    public void setDetailLock(String detailLock) {
        this.detailLock = detailLock;
    }

}
