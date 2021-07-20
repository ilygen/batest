package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 查調 勞保年金所得替代率參數檔 檔
 * 
 * @author Noctis
 */
@Table("BAPAIRR")
public class Bapairr implements Serializable{
    
	@PkeyField("EFFYM")
	private String effYm;            // 施行年月
	@LogField("PAYCODE")
	private String payCode;          //給付別
	@LogField("INSAVG1")
	private BigDecimal insAvg1;      // 平均薪資級距1
	@LogField("INSAVG2")
	private BigDecimal insAvg2;      // 平均薪資級距2
	@LogField("INSAVG3")
    private BigDecimal insAvg3;      // 平均薪資級距3
	@LogField("INSAVG4")
    private BigDecimal insAvg4;      // 平均薪資級距4
	@LogField("IRRTYPEA1")
    private BigDecimal irrTypeA1;    // 所得替代率第一式A1
	@LogField("IRRTYPEA2")
    private BigDecimal irrTypeA2;    // 所得替代率第二式A2
	@LogField("IRRTYPEB1")
    private BigDecimal irrTypeB1;    // 所得替代率第一式B1
	@LogField("IRRTYPEB2")
    private BigDecimal irrTypeB2;    // 所得替代率第二式B2
	@LogField("IRRTYPEC1")
    private BigDecimal irrTypeC1;    // 所得替代率第一式C1
	@LogField("IRRTYPEC2")
    private BigDecimal irrTypeC2;    // 所得替代率第二式C2
	@LogField("IRRTYPED1")
    private BigDecimal irrTypeD1;    // 所得替代率第一式D1
	@LogField("IRRTYPED2")
    private BigDecimal irrTypeD2;    // 所得替代率第二式D2
	@LogField("CRTUSER")
    private String crtUser;          // 新增者代號
	@LogField("CRTTIME")
    private String crtTime;          // 新增日期時間
	@LogField("UPDUSER")
    private String updUser;          // 異動者代號
	@LogField("UPDTIME")
    private String updTime;          // 異動日期時間
    private String user;             // 異動人員
    private String date;             // 處理日期
    
    public static final int INS_AVG_Level_1 = 1;
    public static final int INS_AVG_Level_2 = 2;
    public static final int INS_AVG_Level_3 = 3;
    public static final int INS_AVG_Level_4 = 4;
    

	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getEffYm() {
		return effYm;
	}
	public void setEffYm(String effYm) {
		this.effYm = effYm;
	}
	public BigDecimal getInsAvg1() {
		return insAvg1;
	}
	public void setInsAvg1(BigDecimal insAvg1) {
		this.insAvg1 = insAvg1;
	}
	public BigDecimal getInsAvg2() {
		return insAvg2;
	}
	public void setInsAvg2(BigDecimal insAvg2) {
		this.insAvg2 = insAvg2;
	}
	public BigDecimal getInsAvg3() {
		return insAvg3;
	}
	public void setInsAvg3(BigDecimal insAvg3) {
		this.insAvg3 = insAvg3;
	}
	public BigDecimal getInsAvg4() {
		return insAvg4;
	}
	public void setInsAvg4(BigDecimal insAvg4) {
		this.insAvg4 = insAvg4;
	}
	public BigDecimal getIrrTypeA1() {
		return irrTypeA1;
	}
	public void setIrrTypeA1(BigDecimal irrTypeA1) {
		this.irrTypeA1 = irrTypeA1;
	}
	public BigDecimal getIrrTypeA2() {
		return irrTypeA2;
	}
	public void setIrrTypeA2(BigDecimal irrTypeA2) {
		this.irrTypeA2 = irrTypeA2;
	}
	public BigDecimal getIrrTypeB1() {
		return irrTypeB1;
	}
	public void setIrrTypeB1(BigDecimal irrTypeB1) {
		this.irrTypeB1 = irrTypeB1;
	}
	public BigDecimal getIrrTypeB2() {
		return irrTypeB2;
	}
	public void setIrrTypeB2(BigDecimal irrTypeB2) {
		this.irrTypeB2 = irrTypeB2;
	}
	public BigDecimal getIrrTypeC1() {
		return irrTypeC1;
	}
	public void setIrrTypeC1(BigDecimal irrTypeC1) {
		this.irrTypeC1 = irrTypeC1;
	}
	public BigDecimal getIrrTypeC2() {
		return irrTypeC2;
	}
	public void setIrrTypeC2(BigDecimal irrTypeC2) {
		this.irrTypeC2 = irrTypeC2;
	}
	public BigDecimal getIrrTypeD1() {
		return irrTypeD1;
	}
	public void setIrrTypeD1(BigDecimal irrTypeD1) {
		this.irrTypeD1 = irrTypeD1;
	}
	public BigDecimal getIrrTypeD2() {
		return irrTypeD2;
	}
	public void setIrrTypeD2(BigDecimal irrTypeD2) {
		this.irrTypeD2 = irrTypeD2;
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
	
	public int getInsAvgLevel() {
        if (insAvg4.intValue() > 0) {
            return INS_AVG_Level_4;
        }
        else if (insAvg3.intValue() > 0) {
            return INS_AVG_Level_3;
        }
        else if (insAvg2.intValue() > 0) {
            return INS_AVG_Level_2;
        }
        else {
            return INS_AVG_Level_1;
        }
    }

}
