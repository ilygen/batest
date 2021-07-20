package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保老年年金給付受理編審清單 - 平均薪資
 * 
 * @author KIYOMI
 */
public class OldAgeReviewRpt01OnceAvgAmtDataCase implements Serializable {
    private BigDecimal avgWg;
    private BigDecimal nitrmY;
    private BigDecimal nitrmM;
    private BigDecimal oldTY;
    private BigDecimal oldTD;

    /**
     * 投保年月
     * 
     * @return
     */


    public OldAgeReviewRpt01OnceAvgAmtDataCase() {

    }

    public BigDecimal getAvgWg() {
        return avgWg;
    }

    public void setAvgWg(BigDecimal avgWg) {
        this.avgWg = avgWg;
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

    public BigDecimal getOldTY() {
        return oldTY;
    }

    public void setOldTY(BigDecimal oldTY) {
        this.oldTY = oldTY;
    }

    public BigDecimal getOldTD() {
        return oldTD;
    }

    public void setOldTD(BigDecimal oldTD) {
        this.oldTD = oldTD;
    }

}
