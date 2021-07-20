package tw.gov.bli.ba.update.cases;

import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * Case for 遺屬年金應收收回處理
 * 
 * @author Noctis
 */
public class SurvivorAccountsReceivableDataCase {
    private BigDecimal baunacpdtlId;// 資料列編號(應收帳務明細編號)
    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String sts;// 資料狀態
    private String unacpDate; // 應收立帳日期
    private BigDecimal recAmt;// 應收金額
    private BigDecimal recRem;// 應收未收餘額
    private String benIds;// 受款人社福識別碼
    private String benIdnNo;// 受款人身分證號
    private String payKind; // 給付種類

    public String getUnacpDateStr() {
        if (StringUtils.isNotBlank(getUnacpDate())) {
            return DateUtility.changeDateType(getUnacpDate());
        }
        else {
            return "";
        }
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

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public String getUnacpDate() {
        return unacpDate;
    }

    public void setUnacpDate(String unacpDate) {
        this.unacpDate = unacpDate;
    }

    public BigDecimal getBaunacpdtlId() {
        return baunacpdtlId;
    }

    public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
        this.baunacpdtlId = baunacpdtlId;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

}
