package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import tw.gov.bli.ba.util.DateUtility;

public class CiptUtilityCase implements Comparable<CiptUtilityCase>, Serializable {
    private String intTp = "";// 保險別
    private String sidMk = ""; // 特殊身分註記
    private String txcdType2 = "";// TXCD_TYPE2 --無退保日期註記
    private String uno = "";// 保險證號
    private String txcd = "";// 異動別
    private String txcdTypeA = "";// TXCD_TYPEA --取消資格註記
    private BigDecimal wage;// 投保薪資
    private String txcdTypeB = "";// TXCD_TYPEB --薪資逕調註記
    private String dept = "";// 工作部門註記
    private String efDte = "";// 生效日期
    private String exitDte = "";// EXITDTE --退保日期(TXCD欄位值為2)
    private String tsMk;// 異動原因註記

    public String getIntTp() {
        return intTp;
    }

    public void setIntTp(String intTp) {
        this.intTp = intTp;
    }

    public String getSidMk() {
        return sidMk;
    }

    public void setSidMk(String sidMk) {
        this.sidMk = sidMk;
    }

    public String getTxcdType2() {
        return txcdType2;
    }

    public void setTxcdType2(String txcdType2) {
        this.txcdType2 = txcdType2;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getTxcd() {
        return txcd;
    }

    public void setTxcd(String txcd) {
        this.txcd = txcd;
    }

    public String getTxcdTypeA() {
        return txcdTypeA;
    }

    public void setTxcdTypeA(String txcdTypeA) {
        this.txcdTypeA = txcdTypeA;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public String getTxcdTypeB() {
        return txcdTypeB;
    }

    public void setTxcdTypeB(String txcdTypeB) {
        this.txcdTypeB = txcdTypeB;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEfDte() {
        return efDte;
    }

    public void setEfDte(String efDte) {
        this.efDte = efDte;
    }

    public String getExitDte() {
        return exitDte;
    }

    public void setExitDte(String exitDte) {
        this.exitDte = exitDte;
    }

    public String getTsMk() {
        return tsMk;
    }

    public void setTsMk(String tsMk) {
        this.tsMk = tsMk;
    }

    /**
     * 生效日期
     * 
     * @returns
     */
    public String getEfDteString() {
        if (StringUtils.length(efDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(efDte), false);
        else
            return StringUtils.defaultString(efDte);
    }

    /**
     * 退保日期
     * 
     * @returns
     */
    public String getExitDteString() {
        if (StringUtils.length(exitDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(exitDte), false);
        else
            return StringUtils.defaultString(exitDte);
    }

    public String getDataString() {
        String str = "intTp=" + StringUtils.rightPad(intTp, 4, " ") + "sidMk=" + StringUtils.rightPad(sidMk, 4, " ") + "txcdType2=" + StringUtils.rightPad(txcdType2, 4, " ") + "uno=" + StringUtils.rightPad(uno, 12, " ") + "txcd=" + StringUtils.rightPad(txcd, 4, " ") + "txcdTypeA=" + StringUtils.rightPad(txcdTypeA, 4, " ") + "wage=" + StringUtils.leftPad(String.valueOf(wage == null ? 0 : wage.intValue()), 7, " ") + "  txcdTypeB=" + StringUtils.rightPad(txcdTypeB, 4, " ")
                        + "dept=" + StringUtils.rightPad(dept, 4, " ") + "efDte=" + StringUtils.rightPad(efDte, 10, " ") + "exitDte=" + exitDte;
        return str;
    }

    public int compareTo(CiptUtilityCase ciptCaseObj) throws ClassCastException {
        int efdte = NumberUtils.toInt(this.getEfDte());
        int objEfDte = NumberUtils.toInt(ciptCaseObj.getEfDte());
        if (efdte > objEfDte) {
            return 1;
        }
        if (efdte < objEfDte) {
            return -1;
        }
        return 0;
    }

}
