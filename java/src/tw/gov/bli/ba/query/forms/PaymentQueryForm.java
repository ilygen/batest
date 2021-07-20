package tw.gov.bli.ba.query.forms;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.util.DateUtility;

/**
 * 查詢作業 - 給付查詢作業 (BAIQ0D010Q)
 * 
 * @author Rickychi
 */
public class PaymentQueryForm extends BaseValidatorForm {
    private String method;

    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String idn;// 身分證號
    private String name;// 姓名
    private String brDate;// 出生日期
    private String qryCond;// 查詢條件
    private String startYm;// 查詢年月(起)
    private String endYm;// 查詢年月(迄)
    private String issuYm;// 核定年月
    private String payYm;// 給付年月

    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序
    private String seqNo;// 序號

    private String issuYmOption;// 核定年月下拉選項
    private String payYmOption;// 給付年月下拉選項
    private String seqNoOption;// 受款人下拉選項
    private BigDecimal marginAmt;// 老年差額金
    
    private String payKind;//

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getReApNo() {
        return reApNo;
    }

    public void setReApNo(String reApNo) {
        this.reApNo = reApNo;
    }

    public BigDecimal getApSeq() {
        return apSeq;
    }

    public void setApSeq(BigDecimal apSeq) {
        this.apSeq = apSeq;
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getStartYmStr() {
        if (StringUtils.isNotBlank(getStartYm()) && getStartYm().length() == 5) {
            return DateUtility.changeChineseYearMonthType(getStartYm());
        }
        else {
            return "";
        }
    }

    public String getEndYmStr() {
        if (StringUtils.isNotBlank(getEndYm()) && getEndYm().length() == 5) {
            return DateUtility.changeChineseYearMonthType(getEndYm());
        }
        else {
            return "";
        }
    }

    public String getBrDateStr() {
        if (StringUtils.isNotBlank(getBrDate())) {
            return DateUtility.changeDateType(getBrDate());
        }
        else {
            return "";
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrDate() {
        return brDate;
    }

    public void setBrDate(String brDate) {
        this.brDate = brDate;
    }

    public String getQryCond() {
        return qryCond;
    }

    public void setQryCond(String qryCond) {
        this.qryCond = qryCond;
    }

    public String getStartYm() {
        return startYm;
    }

    public void setStartYm(String startYm) {
        this.startYm = startYm;
    }

    public String getEndYm() {
        return endYm;
    }

    public void setEndYm(String endYm) {
        this.endYm = endYm;
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

    public String getIssuYmOption() {
        return issuYmOption;
    }

    public void setIssuYmOption(String issuYmOption) {
        this.issuYmOption = issuYmOption;
    }

    public String getPayYmOption() {
        return payYmOption;
    }

    public void setPayYmOption(String payYmOption) {
        this.payYmOption = payYmOption;
    }

    public String getSeqNoOption() {
        return seqNoOption;
    }

    public void setSeqNoOption(String seqNoOption) {
        this.seqNoOption = seqNoOption;
    }
    
    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }    
    
    public BigDecimal getMarginAmt() {
        return marginAmt;
    }

    public void setMarginAmt(BigDecimal marginAmt) {
        this.marginAmt = marginAmt;
    }
}
