package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 已退匯止付或退回現金尚未沖轉收回案件清單
 * 
 * @author Noctis
 */
public class OtherRpt02Case implements Serializable {
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal recRem;// 未收餘額
    private String brChkDate; // 退匯初核日期
    private BigDecimal remitAmt; // 退匯金額
    private String source; // 來源
    private BigDecimal available_Money;// 可用餘額
    private String divSeq;// 分割序號

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

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public String getBrChkDate() {
        return brChkDate;
    }

    public void setBrChkDate(String brChkDate) {
        this.brChkDate = brChkDate;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getAvailable_Money() {
        return available_Money;
    }

    public void setAvailable_Money(BigDecimal available_Money) {
        this.available_Money = available_Money;
    }

    public String getDivSeq() {
        return divSeq;
    }

    public void setDivSeq(String divSeq) {
        this.divSeq = divSeq;
    }

}
