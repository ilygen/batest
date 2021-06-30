package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保老年年金給付受理編審清單 - 請領他類給付資料 - 申請失業給付記錄 <br>
 * 注意: 本 Case 中的日期皆為民國日期
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01JoblessPayDataCase implements Serializable {
    private String apNo; // 受理編號
    private String symDte; // 本次給付起日
    private String tymDte; // 本次給付迄日
    private String apDte; // 受理日期
    private String name; // 姓名
    private String tdte; // 離職日期
    private BigDecimal chkAmt; // 核定金額
    private String chkDte; // 核定日期
    private String payDte; // 核付日期
    private String npyDte; // 不給付日期
    private String aprDte; // 求職登記日
    private String ndcMrk; // 補件註記
    private String savDt1; // 補件日期

    /**
     * 受理號碼 14 碼<br>
     * 格式: xxx-x-xx-xxxxxx-xx
     * 
     * @return
     */
    public String getApNoString() {
        this.apNo = StringUtils.rightPad(apNo, 14, " ");
        return apNo.substring(0, 3) + "-" + apNo.substring(3, 4) + "-" + apNo.substring(4, 6) + "-" + apNo.substring(6, 12) + "-" + apNo.substring(12, 14);
    }

    /**
     * 本次給付起日
     * 
     * @return
     */
    public String getSymDteString() {
        if (StringUtils.length(symDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(symDte), false);
        else
            return StringUtils.defaultString(symDte);
    }

    /**
     * 本次給付迄日
     * 
     * @return
     */
    public String getTymDteString() {
        if (StringUtils.length(tymDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(tymDte), false);
        else
            return StringUtils.defaultString(tymDte);
    }

    /**
     * 受理日期
     * 
     * @return
     */
    public String getApDteString() {
        if (StringUtils.length(apDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(apDte), false);
        else
            return StringUtils.defaultString(apDte);
    }

    /**
     * 離職日期
     * 
     * @return
     */
    public String getTdteString() {
        if (StringUtils.length(tdte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(tdte), false);
        else
            return StringUtils.defaultString(tdte);
    }

    /**
     * 核定日期
     * 
     * @return
     */
    public String getChkDteString() {
        if (StringUtils.length(chkDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(chkDte), false);
        else
            return StringUtils.defaultString(chkDte);
    }

    /**
     * 核付日期
     * 
     * @return
     */
    public String getPayDteString() {
        if (StringUtils.length(payDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(payDte), false);
        else
            return StringUtils.defaultString(payDte);
    }

    /**
     * 不給付日期
     * 
     * @return
     */
    public String getNpyDteString() {
        if (StringUtils.length(npyDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(npyDte), false);
        else
            return StringUtils.defaultString(npyDte);
    }

    /**
     * 求職登記日
     * 
     * @return
     */
    public String getAprDteString() {
        if (StringUtils.length(aprDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(aprDte), false);
        else
            return StringUtils.defaultString(aprDte);
    }

    /**
     * 補件日期
     * 
     * @return
     */
    public String getSavDt1String() {
        if (StringUtils.length(savDt1) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(savDt1), false);
        else
            return StringUtils.defaultString(savDt1);
    }

    public OldAgeReviewRpt01JoblessPayDataCase() {

    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSymDte() {
        return symDte;
    }

    public void setSymDte(String symDte) {
        this.symDte = symDte;
    }

    public String getTymDte() {
        return tymDte;
    }

    public void setTymDte(String tymDte) {
        this.tymDte = tymDte;
    }

    public String getApDte() {
        return apDte;
    }

    public void setApDte(String apDte) {
        this.apDte = apDte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTdte() {
        return tdte;
    }

    public void setTdte(String tdte) {
        this.tdte = tdte;
    }

    public BigDecimal getChkAmt() {
        return chkAmt;
    }

    public void setChkAmt(BigDecimal chkAmt) {
        this.chkAmt = chkAmt;
    }

    public String getChkDte() {
        return chkDte;
    }

    public void setChkDte(String chkDte) {
        this.chkDte = chkDte;
    }

    public String getPayDte() {
        return payDte;
    }

    public void setPayDte(String payDte) {
        this.payDte = payDte;
    }

    public String getNpyDte() {
        return npyDte;
    }

    public void setNpyDte(String npyDte) {
        this.npyDte = npyDte;
    }

    public String getAprDte() {
        return aprDte;
    }

    public void setAprDte(String aprDte) {
        this.aprDte = aprDte;
    }

    public String getNdcMrk() {
        return ndcMrk;
    }

    public void setNdcMrk(String ndcMrk) {
        this.ndcMrk = ndcMrk;
    }

    public String getSavDt1() {
        return savDt1;
    }

    public void setSavDt1(String savDt1) {
        this.savDt1 = savDt1;
    }

}
