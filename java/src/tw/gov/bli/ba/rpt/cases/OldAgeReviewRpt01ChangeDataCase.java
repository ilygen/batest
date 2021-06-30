package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保老年年金給付受理編審清單 - 承保異動資料
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01ChangeDataCase implements Serializable {
    private String uno; // 保險證號
    private String txcd; // 異動代號
    private String efDte; // 生效日期
    private BigDecimal wage; // 投保薪資
    private String dept; // 工作部門註記

    /**
     * 生效日期
     * 
     * @returns
     */
    public String getEfDteString() {
        if (StringUtils.length(efDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(efDte), false);
        else
            return StringUtils.defaultString(efDte);
    }

    public OldAgeReviewRpt01ChangeDataCase() {

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

    public String getEfDte() {
        return efDte;
    }

    public void setEfDte(String efDte) {
        this.efDte = efDte;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

}
