package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 物價指數調整明細檔 (<code>BABASICAMT</code>)
 *
 * @author frank
 */
@Table("BABASICAMT")
public class Babasicamt implements Serializable {
    @PkeyField("PAYCODE")
    private String payCode;    // 給付別
    @PkeyField("CPIYEAR1")
    private String cpiYear1;    // 指數年度一
    @PkeyField("CPIYEAR2")
    private String cpiYear2;    // 指數年度二
    @LogField("CPINDEX1")
    private BigDecimal cpiNdex1; // 物價指數一
    @LogField("CPINDEX2")
    private BigDecimal cpiNdex2; // 物價指數二
    @LogField("GROWTHRATE")
    private BigDecimal growThrate; // 成長率
    @LogField("BASICAMT")
    private BigDecimal basicAmt; // 基本金額(加計金額)
    @LogField("REQRPNO")
    private String reqRpno;    // 報請核定文號
    @LogField("PAYYMB")
    private String payYmB;    // 給付年月起月
    @LogField("PAYYME")
    private String payYmE;    // 給付年月迄月
    @LogField("ISSURPNO")
    private String issuRpno;    // 核定文號
    @LogField("ISSUDESC")
    private String issuDesc;    // 核定結果
    @LogField("CRTUSER")
    private String crtUser;    // 新增者代號
    @LogField("CRTTIME")
    private String crtTime;    // 新增日期時間
    @LogField("UPDUSER")
    private String updUser;    // 異動者代號
    @LogField("UPDTIME")
    private String updTime;    // 異動日期時間
    @LogField("USER")
    private String user;       // 異動人員
    @LogField("DATE")
    private String date;       // 處理日期
    @LogField("SYSDATE")
    private String sysDate;    // 系統日期
    
    

    public String getSysDate() {
		return sysDate;
	}


	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}


	public Babasicamt() {

    }


	public String getPayCode() {
		return payCode;
	}


	public void setPayCode(String payCode) {
		this.payCode = payCode;
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


	public String getCrtUser() {
		return crtUser;
	}


	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}


	public String getCrtTime() {
		return crtTime;
	}


	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}


	public String getUpdUser() {
		return updUser;
	}


	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}


	public String getUpdTime() {
		return updTime;
	}


	public void setUpdTime(String updTime) {
		this.updTime = updTime;
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
	    
	    
    

}
