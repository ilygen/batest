package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 國保資料payKind=38
 * 
 * @author Rickychi
 */

public class DisableReviewRpt01NpData38Case implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String labMerge;// 併計勞保年資
    private BigDecimal valSeni; // 國保年資 月
    private BigDecimal valSenid; // 國保年資 日
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal sagtotAmt;// 代扣保費金額
    private BigDecimal itrtAmt;// 利息(20091023新增)(代扣需加此欄方為代扣保費總和)
    private BigDecimal recAmt;// 收回金額
    private BigDecimal supAmt;// 補發金額
    private BigDecimal cutAmt;// 減領金額
    private BigDecimal otherAmt;// 另案扣減金額
    private BigDecimal aplPayAmt;// 實付金額
    private String manChkFlg;// 人工審核結果
    private String comnpMk; // 是否併計國保年資

    // 是否併計國保年資 替換中文
    public String getComnpMkStr() {
        if (StringUtils.equals(comnpMk, "Y")) {
            return "Y-同意併計";
        }
        else if (StringUtils.equals(comnpMk, "N")) {
            return "N-逕不併計";
        }
        else if (StringUtils.equals(comnpMk, "X")) {
            return "X-同意不併計";
        }
        else {
            return "";
        }
    }

    // 頁面顯示轉換
    // [
    // 國保年資字串
    public String getValSeniDisplay() {

        String valSeniDisplay = "";
        // 國保年資 如為0 不帶入
        if (valSeni != null && valSenid != null) {

            if (valSeni.compareTo(new BigDecimal("0")) != 0 && valSenid.compareTo(new BigDecimal("0")) != 0) {
                valSeniDisplay = valSeni + "月" + valSenid + "日";
            }
            else if (valSeni.compareTo(new BigDecimal("0")) != 0 && valSenid.compareTo(new BigDecimal("0")) == 0) {
                valSeniDisplay = valSeni + "月";
            }
            else if (valSeni.compareTo(new BigDecimal("0")) == 0 && valSenid.compareTo(new BigDecimal("0")) != 0) {
                valSeniDisplay = valSenid + "日";
            }

        }

        return valSeniDisplay;
        // 轉換國保年資 20140402修改
        // if(valSeni != null){
        // int intValSeni = valSeni.intValue();
        // String nYear = "";
        // String nMonth = "";
        // String valSeniDisplay = "";
        // if (intValSeni > 0 && intValSeni <= 12){
        // nMonth = String.valueOf(intValSeni);
        // valSeniDisplay = nMonth+"月";
        // }else if(intValSeni % 12 == 0){
        // nYear = String.valueOf(intValSeni/12);
        // valSeniDisplay = nYear+"年";
        // }else if(intValSeni % 12 != 0){
        // nYear = String.valueOf(intValSeni/12);
        // nMonth = String.valueOf(intValSeni % 12);
        // valSeniDisplay = nYear+"年"+nMonth+"月";
        // }
        // return valSeniDisplay;
        // }else{
        // return "";
        // }
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(apNo) && apNo.length() == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return "";
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(issuYm) && issuYm.length() == 6) {
            return DateUtility.changeWestYearMonthType(issuYm);
        }
        else {
            return issuYm;
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(payYm) && payYm.length() == 6) {
            return DateUtility.changeWestYearMonthType(payYm);
        }
        else {
            return payYm;
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

    public BigDecimal getValSeni() {
        return valSeni;
    }

    public void setValSeni(BigDecimal valSeni) {
        this.valSeni = valSeni;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getSagtotAmt() {
        return sagtotAmt;
    }

    public void setSagtotAmt(BigDecimal sagtotAmt) {
        this.sagtotAmt = sagtotAmt;
    }

    public BigDecimal getItrtAmt() {
        return itrtAmt;
    }

    public void setItrtAmt(BigDecimal itrtAmt) {
        this.itrtAmt = itrtAmt;
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

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }

    public String getLabMerge() {
        return labMerge;
    }

    public void setLabMerge(String labMerge) {
        this.labMerge = labMerge;
    }

    public String getComnpMk() {
        return comnpMk;
    }

    public void setComnpMk(String comnpMk) {
        this.comnpMk = comnpMk;
    }

    public BigDecimal getValSenid() {
        return valSenid;
    }

    public void setValSenid(BigDecimal valSenid) {
        this.valSenid = valSenid;
    }

}
