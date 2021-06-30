package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;

/**
 * Case for 月核定給付撥款總額表
 * 
 * @author Goston
 */
public class MonthlyRpt07FooterCase implements Serializable {
    private String dataCount; // 筆數 - 各 給付種類 的筆數
    private String amt2; // 交易金額 - 各 給付種類 的金額
    private String taTyp2; // 出帳類別
    private String payTyp2; // 給付種類

    public MonthlyRpt07FooterCase() {

    }

    public String getDataCount() {
        return dataCount;
    }

    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }

    public String getAmt2() {
        return amt2;
    }

    public void setAmt2(String amt2) {
        this.amt2 = amt2;
    }

    public String getTaTyp2() {
        return taTyp2;
    }

    public void setTaTyp2(String taTyp2) {
        this.taTyp2 = taTyp2;
    }

    public String getPayTyp2() {
        return payTyp2;
    }

    public void setPayTyp2(String payTyp2) {
        this.payTyp2 = payTyp2;
    }

}
