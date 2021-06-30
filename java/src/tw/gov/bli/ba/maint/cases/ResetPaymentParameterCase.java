package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.query.cases.PaymentQueryChkFileDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 重設案件給付參數資料
 * 
 * @author KIYOMI
 */
public class ResetPaymentParameterCase implements Serializable {

    // BAAPPBASE
    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String apItem; // 申請項目
    private String caseTyp; // 案件類別
    private String procStat; // 處理狀態
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期

    // BAAPPEXPAND
    private String cpiBaseYM; // CPI 基準年月
    private String insAvgAmtAppYear; // 計算平均薪資申請年度

    // BADAPR
    private BigDecimal aplPaySeniY; // 老年-實付年資(年) / 失能-實發年資(日)
    private BigDecimal aplPaySeniM; // 老年-實付年資(月) / 失能-實發年資(日)
    private BigDecimal insAvgAmt; // 平均薪資

    public String getProcStatStr() {
        String tmpCaseTypStr = "";
        String tmpProcStatStr = "";

        if (StringUtils.isNotBlank(getProcStat())) {
            String tmpCaseTyp = getCaseTyp();
            String tmpProcStat = getProcStat();
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
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_90).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_99).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
            }
        }
        return tmpCaseTypStr + "-" + tmpProcStatStr;
    }

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

    public String getApNo1() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(0, 1);
        }
        else {
            return "";
        }
    }

    public String getApNo2() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(1, 2);
        }
        else {
            return "";
        }
    }

    public String getApNo3() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(2, 7);
        }
        else {
            return "";
        }
    }

    public String getApNo4() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(7, 12);
        }
        else {
            return "";
        }
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

    // ]

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

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getCpiBaseYM() {
        return cpiBaseYM;
    }

    public void setCpiBaseYM(String cpiBaseYM) {
        this.cpiBaseYM = cpiBaseYM;
    }

    public String getInsAvgAmtAppYear() {
        return insAvgAmtAppYear;
    }

    public void setInsAvgAmtAppYear(String insAvgAmtAppYear) {
        this.insAvgAmtAppYear = insAvgAmtAppYear;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }
}
