package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

public class MonthlyRpt23Case implements Serializable{

    private String apNo;// 受理編號
    private String seqNo; //受款人序號
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal payAmt;// 實付金額
    private String accName;// 戶名
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    
    
    /**
     * 受理號碼
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    
    /**
     * 核定年月
     * 
     * @return
     */
    public String getIssuYmString() {
        if (StringUtils.length(issuYm) == 6){
            return DateUtility.changeWestYearMonthType(issuYm);
        }else{
            return StringUtils.defaultString(issuYm);
        }
    }
    
    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6){
            return DateUtility.changeWestYearMonthType(payYm);
        }else{
            return StringUtils.defaultString(payYm);
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
    public BigDecimal getPayAmt() {
        return payAmt;
    }
    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getCommZip() {
        return commZip;
    }
    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }
    public String getCommAddr() {
        return commAddr;
    }
    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }
    
    
    
}
