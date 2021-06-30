package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 平均薪資
 * 
 * @author Rickychiclose
 */
public class PaymentQueryAvgAmtDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal nitrmY; // 投保年資(年-年金制)
    private BigDecimal nitrmM; // 投保年資(月-年金制)
    private BigDecimal itrmY; // 投保年資(年)
    private BigDecimal itrmD; // 投保年資(日)
    private BigDecimal aplPaySeniY; // 實付年資(年)
    private BigDecimal aplPaySeniM; // 實付年資(月)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valseniY; // 國保已繳年資(年)
    private BigDecimal valseniM; // 國保已繳年資(月)
    private BigDecimal insAvgAmt; // 平均薪資
    private String prType;// 先核普通
    private String evTyp;// 傷病分類
    private String evtIdnNo;// 事故者身分證號

    private String appMonth;// 平均薪資月數
    private String realAvgMon;// 實際均薪月數

    private String avgYm; // 投保年月
    private BigDecimal avgWg;// 月投保薪資

    // 頁面顯示轉換
    // [

    public String getEvTypStr() {
        String evTypStr = "";
        if ((ConstantKey.BAAPPEXPAND_EVTYP_1).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_1;
        }
        else if ((ConstantKey.BAAPPEXPAND_EVTYP_2).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_2;
        }
        else if ((ConstantKey.BAAPPEXPAND_EVTYP_3).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_3;
        }
        else if ((ConstantKey.BAAPPEXPAND_EVTYP_4).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_4;
        }
        return evTypStr;
    }

    public String getAvgYmStr() {
        if (StringUtils.isNotBlank(getAvgYm()) && getAvgYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getAvgYm());
        }
        else {
            return getAvgYm();
        }
    }
    // ]

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

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getAvgYm() {
        return avgYm;
    }

    public void setAvgYm(String avgYm) {
        this.avgYm = avgYm;
    }

    public BigDecimal getAvgWg() {
        return avgWg;
    }

    public void setAvgWg(BigDecimal avgWg) {
        this.avgWg = avgWg;
    }

    public String getAppMonth() {
        return appMonth;
    }

    public void setAppMonth(String appMonth) {
        this.appMonth = appMonth;
    }

    public String getRealAvgMon() {
        return realAvgMon;
    }

    public void setRealAvgMon(String realAvgMon) {
        this.realAvgMon = realAvgMon;
    }

}
