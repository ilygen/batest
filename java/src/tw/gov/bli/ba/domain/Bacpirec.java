package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 物價指數調整明細檔 (<code>BACPIREC</code>)
 *
 * @author Kiyomi
 */
@Table("BACPIREC")
public class Bacpirec implements Serializable {
	@PkeyField("ISSUYEAR")
	private String issuYear; // 核定年度
    @PkeyField("ADJYEAR")
    private String adjYear;    // 調整年度
    @LogField("ADJCPI")
    private BigDecimal adjCpi; // 調整指數
    @LogField("REQRPNO")
    private String reqRpno;    // 報請核定文號
    @LogField("ISSURPNO")
    private String issuRpno;   // 核定文號
    @LogField("ISSUDESC")
    private String issuDesc;   // 核定結果
    @LogField("CPIYEARB")
    private String cpiYearB;   // 指數年度（起）
    @LogField("CPIYEARE")
    private String cpiYearE;   // 指數年度（迄）
    @LogField("ADJYMB")
    private String adjYmB;     // 調整註記
    @LogField("UPDUSER")
    private String updUser;    // 異動者代號
    @LogField("UPDTIME")
    private String updTime;    // 異動日期時間
    @LogField("CRTUSER")
    private String crtUser;    // 異動者代號
    @LogField("CRTTIME")
    private String crtTime;    // 異動日期時間
    @LogField("USER")
    private String user;       // 異動人員
    @LogField("DATE")
    private String date;       // 處理日期
    @LogField("SYSDATE")
    private String sysDate;    // 系統日期
    private BigDecimal amount; // 年度合計
    private String adjYearS; // 申請年度(起)
    private String adjYearE; // 申請年度(迄)

    public Bacpirec() {

    }

    public String getAdjYear() {
        return adjYear;
    }
    public void setAdjYear(String adjYear) {
        this.adjYear = adjYear;
    }


    public BigDecimal getAdjCpi() {
        return adjCpi;
    }
    public void setAdjCpi(BigDecimal adjCpi) {
        this.adjCpi = adjCpi;
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

    public String getCpiYearB() {
        return cpiYearB;
    }
    public void setCpiYearB(String cpiYearB) {
        this.cpiYearB = cpiYearB;
    }

    public String getCpiYearE() {
        return cpiYearE;
    }
    public void setCpiYearE(String cpiYearE) {
        this.cpiYearE = cpiYearE;
    }

    public String getAdjYmB() {
        return adjYmB;
    }
    public void setAdjYmB(String adjYmB) {
        this.adjYmB = adjYmB;
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

    public String getCrtTime() {
        return crtTime;
    }
    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
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
    
    public String getSysDate() {
        return sysDate;
    }
    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAdjYearS() {
		return adjYearS;
	}

	public void setAdjYearS(String adjYearS) {
		this.adjYearS = adjYearS;
	}

	public String getAdjYearE() {
		return adjYearE;
	}

	public void setAdjYearE(String adjYearE) {
		this.adjYearE = adjYearE;
	}

	public String getIssuYear() {
		return issuYear;
	}

	public void setIssuYear(String issuYear) {
		this.issuYear = issuYear;
	}    

}
