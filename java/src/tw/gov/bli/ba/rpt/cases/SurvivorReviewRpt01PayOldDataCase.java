package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 給付資料
 * 
 * @author EvelynHsu
 */

public class SurvivorReviewRpt01PayOldDataCase implements Serializable  {
    
    private String payYm; // 給付年月
    private BigDecimal oldaAmt; //基本月給付金額
    private BigDecimal qualCount; //符合人數
    private BigDecimal oldRate; //加計比率
    private BigDecimal oldbAmt; // 加計後給付金額
    private BigDecimal issueAmt;//核定金額
    private BigDecimal oldab; //計算式
    private BigDecimal compenAmt;//當月扣除失能
    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
        else
            return StringUtils.defaultString(payYm);
    }
    
    public String getPayYm() {
        return payYm;
    }
    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }
    public BigDecimal getOldaAmt() {
        return oldaAmt;
    }
    public void setOldaAmt(BigDecimal oldaAmt) {
        this.oldaAmt = oldaAmt;
    }
    public BigDecimal getQualCount() {
        return qualCount;
    }
    public void setQualCount(BigDecimal qualCount) {
        this.qualCount = qualCount;
    }
    public BigDecimal getOldRate() {
        return oldRate;
    }
    public void setOldRate(BigDecimal oldRate) {
        this.oldRate = oldRate;
    }
    public BigDecimal getOldbAmt() {
        return oldbAmt;
    }
    public void setOldbAmt(BigDecimal oldbAmt) {
        this.oldbAmt = oldbAmt;
    }

	public BigDecimal getIssueAmt() {
		return issueAmt;
	}

	public void setIssueAmt(BigDecimal issueAmt) {
		this.issueAmt = issueAmt;
	}
	
    public BigDecimal getCompenAmt() {
        return compenAmt;
    }

    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }	

	public BigDecimal getOldab() {
		return oldab;
	}

	public void setOldab(BigDecimal oldab) {
		this.oldab = oldab;
	}

   
    
    

}
