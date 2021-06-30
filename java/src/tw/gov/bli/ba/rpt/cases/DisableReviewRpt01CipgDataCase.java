package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * Case for 勞保失能年金給付受理編審清單 - 被保險人投保薪資
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01CipgDataCase implements Serializable {

    private String avgYm; // 投保年月

    private BigDecimal avgWg;// 月投保薪資

    private String dwMk; // 雙薪註記

    /**
     * 投保年月
     * 
     * @return
     */
    public String getAvgYmString() {
        if (StringUtils.length(avgYm) == 6) {
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(avgYm), false);
        }
        else {
            return StringUtils.defaultString(avgYm);
        }
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

    public String getDwMk() {
        return dwMk;
    }

    public void setDwMk(String dwMk) {
        this.dwMk = dwMk;
    }

}
