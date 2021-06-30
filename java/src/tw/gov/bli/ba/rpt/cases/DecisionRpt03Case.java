package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 歸檔清單點交清冊
 * 
 * @author Noctis
 */
public class DecisionRpt03Case implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private String payCode;// 給付別
    private BigDecimal minArcPg;// 歸檔起頁次
    private BigDecimal maxArcPg;// 歸檔迄頁次
    private BigDecimal countNumber;// 件數

    private String arcDate;// 歸檔日期(印表日期)

    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.isBlank(payCode))
            return "";

        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "勞保老年年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "勞保失能年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "勞保遺屬年金";
        else
            return "";
    }

    /**
     * 歸檔日期(印表日期)(格式為: YYY 年 MM 月 DD 日)
     * 
     * @return
     */
    public String getArcDateString() {
        if (StringUtils.length(arcDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(arcDate), true); // 中文年月日
        else
            return StringUtils.defaultString(arcDate);
    }

    public DecisionRpt03Case() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public BigDecimal getMinArcPg() {
        return minArcPg;
    }

    public void setMinArcPg(BigDecimal minArcPg) {
        this.minArcPg = minArcPg;
    }

    public BigDecimal getMaxArcPg() {
        return maxArcPg;
    }

    public void setMaxArcPg(BigDecimal maxArcPg) {
        this.maxArcPg = maxArcPg;
    }

    public BigDecimal getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(BigDecimal countNumber) {
        this.countNumber = countNumber;
    }

}
