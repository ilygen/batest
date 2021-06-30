package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;

/**
 * Case for 月核定給付撥款總額表
 * 
 * @author Goston
 */
public class MonthlyRpt07MiddleCase implements Serializable {
    private String dataCount; // 筆數 - 各 行庫局 的筆數
    private String amt2; // 交易金額 - 各 行庫局 的金額
    private String taTyp2; // 出帳類別
    private String hbank2; // 總行代號

    public MonthlyRpt07MiddleCase() {

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

    public String getHbank2() {
        return hbank2;
    }

    public void setHbank2(String hbank2) {
        this.hbank2 = hbank2;
    }

}
