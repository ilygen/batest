package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

public class BaUserEmailCase implements Serializable {
	private String payCode; //給付別
	private String userName; //承辦人姓名
	private String emailAddr; //EMAIL位置
	
	public String getPayCode() {
		return payCode;
	}
	
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmailAddr() {
		return emailAddr;
	}
	
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	
}
