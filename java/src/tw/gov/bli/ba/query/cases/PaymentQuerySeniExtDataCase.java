package tw.gov.bli.ba.query.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 年資資料 - 欠費期間/承保異動資料
 * 
 * @author Rickychi
 */
public class PaymentQuerySeniExtDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    // 欠費期間資料欄位
    // [
    private String owesBdate;// 欠費期間起日
    private String owesEdate;// 欠費期間迄日
    private String owesMk;// 欠費註記
    // ]

    // 承保異動資料欄位
    // [
    private String uno;// 保險證號
    private String txcd;// 異動別
    private String wage;// 投保薪資
    private String dept;// 部門
    private String efDte;// 生效日期
    private String proDte;// 退保日期

    // ]

    // 頁面顯示轉換
    // [
    public String getOwesBdateStr() {
        if (StringUtils.isNotBlank(getOwesBdate())) {
            return DateUtility.changeDateType(getOwesBdate());
        }
        else {
            return "";
        }
    }

    public String getEfDteStr() {
        if (StringUtils.isNotBlank(getEfDte())) {
            return DateUtility.changeDateType(getEfDte());
        }
        else {
            return "";
        }
    }

    public String getProDteStr() {
        if (StringUtils.isNotBlank(getProDte())) {
            return DateUtility.changeDateType(getProDte());
        }
        else {
            return "";
        }
    }

    public String getOwesEdateStr() {
        if (StringUtils.isNotBlank(getOwesEdate())) {
            return DateUtility.changeDateType(getOwesEdate());
        }
        else {
            return "";
        }
    }

    // ]

    public String getOwesBdate() {
        return owesBdate;
    }

    public void setOwesBdate(String owesBdate) {
        this.owesBdate = owesBdate;
    }

    public String getOwesEdate() {
        return owesEdate;
    }

    public void setOwesEdate(String owesEdate) {
        this.owesEdate = owesEdate;
    }

    public String getOwesMk() {
        return owesMk;
    }

    public void setOwesMk(String owesMk) {
        this.owesMk = owesMk;
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

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
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

    public String getProDte() {
        return proDte;
    }

    public void setProDte(String proDte) {
        this.proDte = proDte;
    }
}
