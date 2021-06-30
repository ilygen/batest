package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 物價指數調整明細檔 (<code>BACPIDTL</code>)
 * 
 * @author Kiyomi
 */
@Table("BACPIDTL")
public class Bacpidtl implements Serializable {
	@PkeyField("ISSUYEAR")
	private String issuYear; // 核定年度
    @PkeyField("ADJYEAR")
    private String cpiYear; // 指數年度 起
    @LogField("CPINDEX")
    private BigDecimal cpIndex; // 累計成長率
    @LogField("CPIB")
    private BigDecimal cpiB; // 物價指數（起）
    @LogField("CPIE")
    private BigDecimal cpiE; // 物價指數（迄）
    @LogField("ADJYEAR")
    private String adjYear; // 調整年度
    @LogField("CPIYEARE")
    private String cpiYearE; // 指數年度 迄
    @LogField("ADJMK")
    private String adjMk; // 調整註記
    @LogField("REQRPNO")
    private String reqRpno; // 報請核定文號
    @LogField("ISSURPNO")
    private String issuRpno; // 核定文號
    @LogField("ISSUDESC")
    private String issuDesc; // 核定結果
    @LogField("UPDUSER")
    private String updUser; // 異動者代號
    @LogField("UPDTIME")
    private String updTime; // 異動日期時間    
    @LogField("CRTUSER")
    private String crtUser; // 新增者代號
    @LogField("CRTTIME")
    private String crtTime; // 新增日期時間 
    @LogField("USER")
    private String user; // 異動人員 
    @LogField("DATE")
    private String date; // 處理日期
    @LogField("SYSDATE")
    private String sysDate;    // 系統日期
    private BigDecimal amount; // 年度合計
    private String adjYearS; // 申請年度(起)
    private String adjYearE; // 申請年度(迄)
    private BigDecimal seqNo; // 新增序號
    
    //update使用
    private String oldCpiYear;
    private String oldCpiYearE;
    private BigDecimal oldCpIndex;
    private BigDecimal oldCpiB;
    private BigDecimal oldCpiE;

    public Bacpidtl() {

    }

    public String getCpiYear() {
        return cpiYear;
    }

    public void setCpiYear(String cpiYear) {
        this.cpiYear = cpiYear;
    }
    
    public BigDecimal getCpIndex() {
        return cpIndex;
    }
    public void setCpIndex(BigDecimal cpIndex) {
        this.cpIndex = cpIndex;
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
    
    public String getAdjYear() {
        return adjYear;
    }
    public void setAdjYear(String adjYear) {
        this.adjYear = adjYear;
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
    public String getAdjMk() {
        return adjMk;
    }
    public void setAdjMk(String adjMk) {
        this.adjMk = adjMk;
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

	public BigDecimal getCpiB() {
		return cpiB;
	}

	public void setCpiB(BigDecimal cpiB) {
		this.cpiB = cpiB;
	}

	public BigDecimal getCpiE() {
		return cpiE;
	}

	public void setCpiE(BigDecimal cpiE) {
		this.cpiE = cpiE;
	}

	public String getCpiYearE() {
		return cpiYearE;
	}

	public void setCpiYearE(String cpiYearE) {
		this.cpiYearE = cpiYearE;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	public String getIssuYear() {
		return issuYear;
	}

	public void setIssuYear(String issuYear) {
		this.issuYear = issuYear;
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

	public BigDecimal getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	public String getOldCpiYear() {
		return oldCpiYear;
	}

	public void setOldCpiYear(String oldCpiYear) {
		this.oldCpiYear = oldCpiYear;
	}

	public String getOldCpiYearE() {
		return oldCpiYearE;
	}

	public void setOldCpiYearE(String oldCpiYearE) {
		this.oldCpiYearE = oldCpiYearE;
	}

	public BigDecimal getOldCpIndex() {
		return oldCpIndex;
	}

	public void setOldCpIndex(BigDecimal oldCpIndex) {
		this.oldCpIndex = oldCpIndex;
	}

	public BigDecimal getOldCpiB() {
		return oldCpiB;
	}

	public void setOldCpiB(BigDecimal oldCpiB) {
		this.oldCpiB = oldCpiB;
	}

	public BigDecimal getOldCpiE() {
		return oldCpiE;
	}

	public void setOldCpiE(BigDecimal oldCpiE) {
		this.oldCpiE = oldCpiE;
	}

}
