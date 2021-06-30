package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 查調 平均薪資月數 檔
 * 
 * @author Noctis
 */
@Table("BAPAAVGMON")
public class Bapaavgmon implements Serializable{
	@PkeyField("EFFYEAR")
	private String effYear;      // 施行年度
	@LogField("APPMONTH")
	private String appMonth;     // 採計年月
	@LogField("CRTUSER")
	private String crtUser;      // 新增者代號
	@LogField("CRTTIME")
    private String crtTime;      // 新增日期時間
	@LogField("UPDUSER")
    private String updUser;      // 異動者代號
	@LogField("UPDTIME")
    private String updTime;      // 異動日期時間
    private String user;         // 異動人員
    private String date;         // 處理日期
    
	public String getEffYear() {
		return effYear;
	}
	public void setEffYear(String effYear) {
		this.effYear = effYear;
	}
	public String getAppMonth() {
		return appMonth;
	}
	public void setAppMonth(String appMonth) {
		this.appMonth = appMonth;
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

}
