package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

public class StopPaymentProcessDetailCase implements Serializable {
    private String firstCol; // 第一欄
    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String aplpayDate; // 核付日期
    private String remitDate; // 入帳日期
    
    private String appDate; // 申請日期
    private String benName; // 受款人姓名
    private String benIdnNo; // 受款人身分證號
    private String benEvtRel; // 關係
    private String payTyp; // 給付方式
    private String accRel; // 戶名與受益人關係
    private BigDecimal mustIssueAmt; // 可領金額
    private String stexpnd; // 止付條件(註)
    private String evtJobDate; //事故者離職日
    
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
    public String getAplpayDate() {
        return aplpayDate;
    }
    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }
    public String getRemitDate() {
        return remitDate;
    }
    public void setRemitDate(String remitDate) {
        this.remitDate = remitDate;
    }
    public String getAppDate() {
        return appDate;
    }
    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }
    public String getBenName() {
        return benName;
    }
    public void setBenName(String benName) {
        this.benName = benName;
    }
    public String getBenIdnNo() {
        return benIdnNo;
    }
    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }
    public String getBenEvtRel() {
        return benEvtRel;
    }
    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }
    public String getPayTyp() {
        return payTyp;
    }
    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
    public String getAccRel() {
        return accRel;
    }
    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }
    public BigDecimal getMustIssueAmt() {
        return mustIssueAmt;
    }
    public void setMustIssueAmt(BigDecimal mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
    }
    
    //事故者離職日
    public String getEvtJobDateStr(){
        if (StringUtils.isNotBlank(evtJobDate)&&(evtJobDate.length())>7){
            return DateUtility.changeDateType(evtJobDate);
        }
        return evtJobDate;
    }
    
    public String getBenEvtRelStr() {
        if(StringUtils.isNotEmpty(benEvtRel)){
            if(benEvtRel.equals("1"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_1;
            else if(benEvtRel.equals("2"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_2;
            else if(benEvtRel.equals("3"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_3;
            else if(benEvtRel.equals("4"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_4;
            else if(benEvtRel.equals("5"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_5;
            else if(benEvtRel.equals("6"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_6;
            else if(benEvtRel.equals("7"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_7;
            else if(benEvtRel.equals("A"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_A;
            else if(benEvtRel.equals("C"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_C;
            else if(benEvtRel.equals("F"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_F;
            else if(benEvtRel.equals("N"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_N;
            else if(benEvtRel.equals("Z"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_Z;
            else if(benEvtRel.equals("O"))
                return ConstantKey.BAAPPBASE_BENEVTREL_STR_O;
            else 
                return "";
        }
        else
            return "";
    }
    
    public String getPayTypStr() {
        if(StringUtils.isNotEmpty(payTyp)){
            if(payTyp.equals("1"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_1;
            else if(payTyp.equals("2"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_2;
            else if(payTyp.equals("3"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_3;
            else if(payTyp.equals("4"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_4;
            else if(payTyp.equals("5"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_5;
            else if(payTyp.equals("6"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_6;
            else if(payTyp.equals("8"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_8;
            else if(payTyp.equals("A"))
                return ConstantKey.BAAPPBASE_PAYTYP_STR_A;
            else
                return "";
        }
        else
            return "";
    }
    
    public String getAplpayDateStr() {
        if(StringUtils.isNotEmpty(aplpayDate))
            return DateUtil.changeDateType(aplpayDate);
        else
            return "";
    }
    
    public String getRemitDateStr() {
        if(StringUtils.isNotEmpty(aplpayDate))
            return DateUtil.changeDateType(remitDate);
        else
            return "";
    }
    public String getFirstCol() {
        return firstCol;
    }
    public void setFirstCol(String firstCol) {
        this.firstCol = firstCol;
    }
    public String getStexpnd() {
        return stexpnd;
    }
    public void setStexpnd(String stexpnd) {
        this.stexpnd = stexpnd;
    }
    public String getEvtJobDate() {
        return evtJobDate;
    }
    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }
    
    
}
