package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 明細資料 - 核定/給付年月資料 (分類 by 核定年月、核付日期、給付年月)
 * 
 * @author Rickychi
 */
public class PaymentQueryIssuPayExtDataCase implements Serializable {
    private String benName;// 受款人姓名
    private String benEvtRel;// 關係
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal offsetAmt; // 紓困金額
    private BigDecimal otherAmt; // 扣減總金額
    private BigDecimal recAmt; // 補發／收回金額
    private BigDecimal aplpayAmt; // 實付金額
    private String manchkMk;// 核定結果
    private String remitDate;// 帳務日期
    private String aplpayDate;// 核付日期
    private String stexpndDate; // 止付日期
    private BigDecimal inheritorAmt; // 計息存儲金額

    // 頁面顯示轉換
    // [
    public String getBenEvtRelName() {
        if (("1").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        else if (("2").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        else if (("3").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        else if (("4").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        else if (("5").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        else if (("6").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        else if (("7").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        else if (("A").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        else if (("C").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        else if (("E").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        else if (("F").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        else if (("N").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        else if (("Z").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        else if (("O").equals(benEvtRel))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        else
            return "";
    }

    public String getManchkMkStr() {
        String manchkMkStr = "";
        if (StringUtils.isNotBlank(getManchkMk())) {
            // 轉換 人工審核結果
            // 核定資料的審核結果改為給付、不給付(主任要求的)
            if ((ConstantKey.PAYMENT_REVIEW_MANCHKMK_Y).equals(getManchkMk())) {
                manchkMkStr = "給付";
            }
            else if ((ConstantKey.PAYMENT_REVIEW_MANCHKMK_N).equals(getManchkMk())) {
                manchkMkStr = "不給付";
            }
        }
        return manchkMkStr;
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return "";
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getPayYm());
        }
        else {
            return "";
        }
    }

    public String getRemitDateStr() {
        if (StringUtils.isNotBlank(getRemitDate())) {
            if(getRemitDate().length()>8){
                return StringUtils.substring(getRemitDate(), 0, getRemitDate().length()-8) + DateUtility.changeDateType(StringUtils.substring(getRemitDate(), getRemitDate().length()-8, getRemitDate().length()));
            }else{
                return DateUtility.changeDateType(getRemitDate());
            }
        }
        else {
            return "";
        }
    }

    public String getAplpayDateStr() {
        if (StringUtils.isNotBlank(getAplpayDate())) {
            return DateUtility.changeDateType(getAplpayDate());
        }
        else {
            return "";
        }
    }

    public String getStexpndDateStr() {
        if (StringUtils.isNotBlank(getStexpndDate())) {
            return DateUtility.changeDateType(getStexpndDate());
        }
        else {
            return "";
        }
    }

    // ]

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

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }

    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public String getManchkMk() {
        return manchkMk;
    }

    public void setManchkMk(String manchkMk) {
        this.manchkMk = manchkMk;
    }

    public String getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(String remitDate) {
        this.remitDate = remitDate;
    }

    public String getAplpayDate() {
        return aplpayDate;
    }

    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }

    public String getStexpndDate() {
        return stexpndDate;
    }

    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getInheritorAmt() {
        return inheritorAmt;
    }

    public void setInheritorAmt(BigDecimal inheritorAmt) {
        this.inheritorAmt = inheritorAmt;
    }
}
