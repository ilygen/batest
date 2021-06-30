package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * Case for 給付查詢作業 歸檔記錄-核定年月
 * 
 * @author KIYOMI
 */
public class PaymentQueryUnqualifiedNoticeDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;

    private List<PaymentQueryUnqualifiedNoticeDataCase> detailList;// 明細資料

    @PkeyField("BAAPPBASEID")
    private BigDecimal baappbaseId;// 資料列編號

    @LogField("APNO")
    private String apNo;// 受理編號

    @LogField("SEQNO")
    private String seqNo;// 序號

    @LogField("CASETYP")
    private String caseTyp;// 案件類別

    @LogField("BENIDNNO")
    private String benIdnNo;// 受益人身分證號

    @LogField("BENNAME")
    private String benName;// 受益人姓名

    @LogField("ISSUYM")
    private String issuYm;// 核定年月

    @LogField("UNQUALIFIEDCAUSE")
    private String unqualifiedCause;// 不合格原因

    @LogField("NOTIFYFORM")
    private String notifyForm;// 核定通知書格式

    @LogField("UNQUALIFIEDCAUSEDESC")
    private String unqualifiedCauseDesc;// 不合格原因中文描述

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

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getUnqualifiedCause() {
        return unqualifiedCause;
    }

    public void setUnqualifiedCause(String unqualifiedCause) {
        this.unqualifiedCause = unqualifiedCause;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getUnqualifiedCauseDesc() {
        return unqualifiedCauseDesc;
    }

    public void setUnqualifiedCauseDesc(String unqualifiedCauseDesc) {
        this.unqualifiedCauseDesc = unqualifiedCauseDesc;
    }

    public String getCaseTypStr() {
        // 轉換 案件類別
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        }
        else {
            return "";
        }
    }

    public String getIssuYmChinese() {
        return StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(issuYm));
    }

    public List<PaymentQueryUnqualifiedNoticeDataCase> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PaymentQueryUnqualifiedNoticeDataCase> detailList) {
        this.detailList = detailList;
    }

}
