package tw.gov.bli.ba.query.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 明細資料 - xx函註記
 * 
 * @author Rickychi
 */
public class PaymentQueryLetterTypeMkCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String proDate;// 承辦/創收日期
    private String ndomk1;// 函別內容一
    private String ndomk2;// 函別內容二
    private String paramName; // BAPARAM.paramName - 處理函別名稱 for 行政支援查詢 功能
    private String ndomkName1; // BAPANDOMK.ndomkName - 處理註記一名稱 for 行政支援查詢 功能
    private String ndomkName2; // BAPARAM.paramName - 處理註記二名稱 for 行政支援查詢 功能
    private String reliefCause; // 救濟事由
    private String reliefKind; // 救濟種類
    private String reliefStat;// 行政救濟辦理情形

    // 頁面顯示轉換
    // [
    public String getProDateStr() {
        if (StringUtils.isNotBlank(getProDate())) {
            return DateUtility.changeDateType(getProDate());
        }
        else {
            return "";
        }
    }

    // ]

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getNdomk2() {
        return ndomk2;
    }

    public void setNdomk2(String ndomk2) {
        this.ndomk2 = ndomk2;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getNdomkName1() {
        return ndomkName1;
    }

    public void setNdomkName1(String ndomkName1) {
        this.ndomkName1 = ndomkName1;
    }

    public String getNdomkName2() {
        return ndomkName2;
    }

    public void setNdomkName2(String ndomkName2) {
        this.ndomkName2 = ndomkName2;
    }

    public String getReliefCause() {
        return reliefCause;
    }

    public void setReliefCause(String reliefCause) {
        this.reliefCause = reliefCause;
    }

    public String getReliefKind() {
        return reliefKind;
    }

    public void setReliefKind(String reliefKind) {
        this.reliefKind = reliefKind;
    }

    public String getReliefStat() {
        return reliefStat;
    }

    public void setReliefStat(String reliefStat) {
        this.reliefStat = reliefStat;
    }
}
