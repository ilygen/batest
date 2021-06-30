package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 明細資料 - 核定/給付年月資料
 * 
 * @author Rickychi
 */
public class PaymentQueryIssuPayDataCase implements Serializable{
    private String benName;// 受款人姓名
    private String benEvtRel;// 關係
    private String seqNo; // 序號
    private String apNo; // 受理編號
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

    private BigDecimal adjustAmt; // 調整金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal remitAmt; // 退匯沖抵
    private BigDecimal ovrcutAmt; // 收回沖抵
    private BigDecimal payBanance; // 給付沖抵
    // private String chkDate; // 核付日期

    private String viewFlag;// 受理清單檢視按鈕狀態
    private String isShowIssuYm;// 是否顯示核定年月
    private String isShowAplpayDate;// 是否顯示核付日期
    private String isShowPayYm;// 是否顯示給付年月
    private List<PaymentQueryIssuPayDataCase> issuPayDataList; // 核定資料(分類 by 核定年月、核付日期、給付年月)
    private List<PaymentQueryIssuPayExtDataCase> issuPayExtDataList; // 核定資料(分類 by 核定年月、核付日期、給付年月)
    private PaymentQueryIssuPayDataCase[] issuPayDataArray; // 核定資料(分類 by 核定年月、核付日期、給付年月)
    private PaymentQueryIssuPayExtDataCase[] issuPayExtDataArray; // 核定資料(分類 by 核定年月、核付日期、給付年月)

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

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
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

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
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

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
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

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public BigDecimal getOvrcutAmt() {
        return ovrcutAmt;
    }

    public void setOvrcutAmt(BigDecimal ovrcutAmt) {
        this.ovrcutAmt = ovrcutAmt;
    }

    public BigDecimal getPayBanance() {
        return payBanance;
    }

    public void setPayBanance(BigDecimal payBanance) {
        this.payBanance = payBanance;
    }

    public String getViewFlag() {
        return viewFlag;
    }

    public void setViewFlag(String viewFlag) {
        this.viewFlag = viewFlag;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
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

    public String getIsShowIssuYm() {
        return isShowIssuYm;
    }

    public void setIsShowIssuYm(String isShowIssuYm) {
        this.isShowIssuYm = isShowIssuYm;
    }

    public String getIsShowAplpayDate() {
        return isShowAplpayDate;
    }

    public void setIsShowAplpayDate(String isShowAplpayDate) {
        this.isShowAplpayDate = isShowAplpayDate;
    }

    public String getIsShowPayYm() {
        return isShowPayYm;
    }

    public void setIsShowPayYm(String isShowPayYm) {
        this.isShowPayYm = isShowPayYm;
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

    public String getStexpndDate() {
        return stexpndDate;
    }

    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }

    public List<PaymentQueryIssuPayDataCase> getIssuPayDataList() {
        return issuPayDataList;
    }

    public void setIssuPayDataList(List<PaymentQueryIssuPayDataCase> issuPayDataList) {
        this.issuPayDataList = issuPayDataList;
    }

    public List<PaymentQueryIssuPayExtDataCase> getIssuPayExtDataList() {
        return issuPayExtDataList;
    }

    public void setIssuPayExtDataList(List<PaymentQueryIssuPayExtDataCase> issuPayExtDataList) {
        this.issuPayExtDataList = issuPayExtDataList;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public PaymentQueryIssuPayDataCase[] getIssuPayDataArray() {
        return issuPayDataArray;
    }

    public void setIssuPayDataArray(PaymentQueryIssuPayDataCase[] issuPayDataArray) {
        this.issuPayDataArray = issuPayDataArray;
    }

    public PaymentQueryIssuPayExtDataCase[] getIssuPayExtDataArray() {
        return issuPayExtDataArray;
    }

    public void setIssuPayExtDataArray(PaymentQueryIssuPayExtDataCase[] issuPayExtDataArray) {
        this.issuPayExtDataArray = issuPayExtDataArray;
    }

    public BigDecimal getInheritorAmt() {
        return inheritorAmt;
    }

    public void setInheritorAmt(BigDecimal inheritorAmt) {
        this.inheritorAmt = inheritorAmt;
    }
}
