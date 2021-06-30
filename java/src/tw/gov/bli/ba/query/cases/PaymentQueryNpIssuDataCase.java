/**
 * 
 */
package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 國保核定資料
 * 
 * @author Rickychi
 */
public class PaymentQueryNpIssuDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal sagtotAmt;// 代扣保費金額
    private BigDecimal itrtAmt;// 利息(20091023新增)(代扣需加此欄方為代扣保費總和)
    private BigDecimal recAmt;// 收回金額
    private BigDecimal supAmt;// 補發金額
    private BigDecimal cutAmt;// 減領金額
    private BigDecimal otherAmt;// 另案扣減金額
    private BigDecimal aplPayAmt;// 實付金額
    private String manChkFlg;// 人工審核結果

    private List<PaymentQueryNpIssuDataCase> issuDataList;// 核定資料
    private PaymentQueryNpIssuDataCase[] issuDataArray;// 核定資料
    // 頁面顯示轉換
    // [

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(payYm) && payYm.length() == 6) {
            return DateUtility.changeWestYearMonthType(payYm);
        }
        else {
            return payYm;
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(issuYm) && issuYm.length() == 6) {
            return DateUtility.changeWestYearMonthType(issuYm);
        }
        else {
            return issuYm;
        }
    }

    // ]
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

    public List<PaymentQueryNpIssuDataCase> getIssuDataList() {
        return issuDataList;
    }

    public void setIssuDataList(List<PaymentQueryNpIssuDataCase> issuDataList) {
        this.issuDataList = issuDataList;
    }

    public PaymentQueryNpIssuDataCase[] getIssuDataArray() {
        return issuDataArray;
    }

    public void setIssuDataArray(PaymentQueryNpIssuDataCase[] issuDataArray) {
        this.issuDataArray = issuDataArray;
    }
}
