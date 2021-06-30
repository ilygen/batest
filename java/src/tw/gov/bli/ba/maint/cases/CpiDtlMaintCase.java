package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 物價指數調整明細維護作業
 * 
 * @author Kiyomi
 */
public class CpiDtlMaintCase implements Serializable {
    private String cpiYear; // 指數年度 起
    private BigDecimal cpIndex; // 累計成長率
    private BigDecimal cpiB; // 物價指數（起）
    private BigDecimal cpiE; // 物價指數（迄）
    private String adjYear; // 申請年度
    private String adjYearS; // 申請年度(起)
    private String adjYearE; // 申請年度(迄)
    private String issuYear; // 核定年度
    private String cpiYearE; // 指數年度 迄
    private String adjMk; // 調整註記
    private String reqRpno; // 報請核定文號
    private String issuRpno; // 核定文號
    private String issuDesc; // 核定結果
    private String user; // 異動人員 
    private String date; // 處理日期
    private String sysDate;    // 系統日期
    private BigDecimal amount; // 年度合計
    private String adjYearDis; //每次新增顯示申請年度使用
    private List<String> adjYearList; // 申請年度 修改頁面使用
    
	public String getDateStr() {
        if (StringUtils.isNotEmpty(date))
            return DateUtility.changeDateType(date);
        else
            return "";
    }
	
	public String getAdjYearStr() {
		
		int amountInt = amount.intValue();
        if (amountInt == 1){
            return adjYearS;
        }else if(amountInt == 2){
            return adjYearS+","+adjYearE;
        }else{
        	return adjYearS+"..."+adjYearE;
        }
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
    public String getAdjYear() {
        return adjYear;
    }
    public void setAdjYear(String adjYear) {
        this.adjYear = adjYear;
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
    public String getAdjMk() {
        return adjMk;
    }
    public void setAdjMk(String adjMk) {
        this.adjMk = adjMk;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAdjYearDis() {
		return adjYearDis;
	}

	public void setAdjYearDis(String adjYearDis) {
		this.adjYearDis = adjYearDis;
	}

	public List<String> getAdjYearList() {
		return adjYearList;
	}

	public void setAdjYearList(List<String> adjYearList) {
		this.adjYearList = adjYearList;
	}    

}
