package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 老年年金加計金額調整紀錄維護作業
 *
 * @author frank
 */
public class BasicAmtMaintCase implements Serializable {
	private String payCode;      //給付類別
	private String cpiYear1;     // 年度一
    private String cpiYear2;     // 年度二
    private BigDecimal cpiNdex1;  // 物價指數一
    private BigDecimal cpiNdex2;  // 物價指數二
    private BigDecimal growThrate;    // 成長率
    private BigDecimal basicAmt;      // 基本金額(加計金額)
    private String payYmB;     // 給付年月起月
    private String payYmE;    // 給付年月迄月
    private String reqRpno;     // 報請核定文號
    private String issuRpno;    // 核定文號
    private String issuDesc;    // 核定結果
    private String user;        // 異動人員
    private String date;        // 處理日期
    private String sysDate;    // 系統日期
    


    

    public String getSysDate() {
		return sysDate;
	}





	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}





	public String getCpiYear1() {
		return cpiYear1;
	}





	public void setCpiYear1(String cpiYear1) {
		this.cpiYear1 = cpiYear1;
	}





	public String getCpiYear2() {
		return cpiYear2;
	}





	public void setCpiYear2(String cpiYear2) {
		this.cpiYear2 = cpiYear2;
	}





	public BigDecimal getCpiNdex1() {
		return cpiNdex1;
	}





	public void setCpiNdex1(BigDecimal cpiNdex1) {
		this.cpiNdex1 = cpiNdex1;
	}





	public BigDecimal getCpiNdex2() {
		return cpiNdex2;
	}





	public void setCpiNdex2(BigDecimal cpiNdex2) {
		this.cpiNdex2 = cpiNdex2;
	}





	public BigDecimal getGrowThrate() {
		return growThrate;
	}





	public void setGrowThrate(BigDecimal growThrate) {
		this.growThrate = growThrate;
	}





	public BigDecimal getBasicAmt() {
		
		return basicAmt;
	}





	public void setBasicAmt(BigDecimal basicAmt) {
		
		this.basicAmt = basicAmt;
	}





	public String getPayYmB() {
		return payYmB;
	}





	public void setPayYmB(String payYmB) {
		this.payYmB = payYmB;
	}





	public String getPayYmE() {
		return payYmE;
	}





	public void setPayYmE(String payYmE) {
		this.payYmE = payYmE;
	}





	public String getReqRpno() {
		return reqRpno;
	}





	public void setReqRpno(String reqRpno) {
		this.reqRpno = reqRpno;
	}





	public String getIssuRpno() {
		return issuRpno;
	}





	public void setIssuRpno(String issuRpno) {
		this.issuRpno = issuRpno;
	}





	public String getIssuDesc() {
		return issuDesc;
	}





	public void setIssuDesc(String issuDesc) {
		this.issuDesc = issuDesc;
	}





	public String getUser() {
		return user;
	}





	public void setUser(String user) {
		this.user = user;
	}





	public String getDate() {
		return date;
	}





	public void setDate(String date) {
		this.date = date;
	}


	
	
	public String getPayCode() {
		return payCode;
	}



	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}


	public String getDateStr() {
        if (StringUtils.isNotEmpty(date))
            return DateUtility.changeDateType(date);
        else
            return "";
    }


}
