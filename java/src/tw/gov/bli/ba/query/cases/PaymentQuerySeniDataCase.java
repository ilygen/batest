package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;

/**
 * Case for 給付查詢作業 年資資料
 * 
 * @author Rickychi
 */
public class PaymentQuerySeniDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String apNo; // 受理編號
    private BigDecimal nitrmY; // 投保年資(X年X月-年)
    private BigDecimal nitrmM; // 投保年資(X年X月-月)
    private BigDecimal itrmY; // 投保年資(X年X日-年)
    private BigDecimal itrmD; // 投保年資(X年X日-日)
    private BigDecimal aplPaySeniY; // 實付年資(年)
    private BigDecimal aplPaySeniM; // 實付年資(月)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valseniY; // 國保年資(年)
    private BigDecimal valseniM; // 國保年資(月)
    private BigDecimal insAvgAmt; // 平均月投保薪資(60個月)

    private List<PaymentQuerySeniExtDataCase> SeniMaintDataList;// 欠費期間資料
    private List<PaymentQuerySeniExtDataCase> txcdDataList;// 承保異動資料

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    // ]

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
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

    public BigDecimal getItrmY() {
        return itrmY;
    }

    public void setItrmY(BigDecimal itrmY) {
        this.itrmY = itrmY;
    }

    public BigDecimal getItrmD() {
        return itrmD;
    }

    public void setItrmD(BigDecimal itrmD) {
        this.itrmD = itrmD;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public BigDecimal getValseniY() {
        return valseniY;
    }

    public void setValseniY(BigDecimal valseniY) {
        this.valseniY = valseniY;
    }

    public BigDecimal getValseniM() {
        return valseniM;
    }

    public void setValseniM(BigDecimal valseniM) {
        this.valseniM = valseniM;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public List<PaymentQuerySeniExtDataCase> getSeniMaintDataList() {
        return SeniMaintDataList;
    }

    public void setSeniMaintDataList(List<PaymentQuerySeniExtDataCase> seniMaintDataList) {
        SeniMaintDataList = seniMaintDataList;
    }

    public List<PaymentQuerySeniExtDataCase> getTxcdDataList() {
        return txcdDataList;
    }

    public void setTxcdDataList(List<PaymentQuerySeniExtDataCase> txcdDataList) {
        this.txcdDataList = txcdDataList;
    }
}
