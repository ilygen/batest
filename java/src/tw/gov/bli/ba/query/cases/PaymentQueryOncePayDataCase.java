package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 一次給付資料
 * 
 * @author Rickychi
 */
public class PaymentQueryOncePayDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String closeCause;// 結案原因
    private String oldSeniab;// 一次請領之年資採計方式
    private String oncePayMk;// 一次給付符合註記
    private String dabApNo;// 已領失能年金受理編號
    private BigDecimal dabAnnuAmt;// 已領失能年金金額
    private BigDecimal annuAmt; // 累計已領老年年金金額
    private BigDecimal oldtY;// 一次給付實付年資(X年X月-年)
    private BigDecimal oldtD;// 一次給付實付年資(X年X月-月)
    private BigDecimal nitrmY;// 一次給付實付年資(X年X日-年)
    private BigDecimal nitrmM;// 一次給付實付年資(X年X日-日)
    private BigDecimal onceOldAmt;// 老年一次金金額
    private BigDecimal oncePayAmt;// 一次給付核定金額
    private BigDecimal onceAvgAmt;// 一次給付平均薪資
    private BigDecimal oncePayYm;// 一次給付月數
    private BigDecimal diffAmt;// 差額金金額
    private BigDecimal sumPayAmt;// 已領年金總額

    // 一次給付更正欄位
    // [
    private String owesBdate;// 扣除期間起日
    private String owesEdate;// 扣除期間迄日

    // ]

    // 頁面顯示轉換
    // [
    public String getDabApNoStr() {
        if (StringUtils.isNotBlank(getDabApNo()) && getDabApNo().length() == ConstantKey.APNO_LENGTH) {
            return getDabApNo().substring(0, 1) + "-" + getDabApNo().substring(1, 2) + "-" + getDabApNo().substring(2, 7) + "-" + getDabApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getOwesBdateStr() {
        if (StringUtils.isNotBlank(getOwesBdate())) {
            return DateUtility.changeDateType(getOwesBdate());
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

    public BigDecimal getDiffAmt() {
        return diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public String getDabApNo() {
        return dabApNo;
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public BigDecimal getDabAnnuAmt() {
        return dabAnnuAmt;
    }

    public void setDabAnnuAmt(BigDecimal dabAnnuAmt) {
        this.dabAnnuAmt = dabAnnuAmt;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public BigDecimal getOldtY() {
        return oldtY;
    }

    public void setOldtY(BigDecimal oldtY) {
        this.oldtY = oldtY;
    }

    public BigDecimal getOldtD() {
        return oldtD;
    }

    public void setOldtD(BigDecimal oldtD) {
        this.oldtD = oldtD;
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getOnceOldAmt() {
        return onceOldAmt;
    }

    public void setOnceOldAmt(BigDecimal onceOldAmt) {
        this.onceOldAmt = onceOldAmt;
    }

    public BigDecimal getOncePayAmt() {
        return oncePayAmt;
    }

    public void setOncePayAmt(BigDecimal oncePayAmt) {
        this.oncePayAmt = oncePayAmt;
    }

    public BigDecimal getOnceAvgAmt() {
        return onceAvgAmt;
    }

    public void setOnceAvgAmt(BigDecimal onceAvgAmt) {
        this.onceAvgAmt = onceAvgAmt;
    }

    public BigDecimal getOncePayYm() {
        return oncePayYm;
    }

    public void setOncePayYm(BigDecimal oncePayYm) {
        this.oncePayYm = oncePayYm;
    }

    public BigDecimal getSumPayAmt() {
        return sumPayAmt;
    }

    public void setSumPayAmt(BigDecimal sumPayAmt) {
        this.sumPayAmt = sumPayAmt;
    }

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

}
