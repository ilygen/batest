package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 核定通知書維護作業
 * 
 * @author Noctis
 */
public class BaletterCodeCase implements Serializable {
	private String code1; //代碼
	private String codeName1; //代碼名稱
	private String code2; //代碼
	private String codeName2; //代碼名稱
	private String code3; //代碼
	private String codeName3; //代碼名稱
	private String code4; //代碼
	private String codeName4; //代碼名稱
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCodeName1() {
		return codeName1;
	}
	public void setCodeName1(String codeName1) {
		this.codeName1 = codeName1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getCodeName2() {
		return codeName2;
	}
	public void setCodeName2(String codeName2) {
		this.codeName2 = codeName2;
	}
	public String getCode3() {
		return code3;
	}
	public void setCode3(String code3) {
		this.code3 = code3;
	}
	public String getCodeName3() {
		return codeName3;
	}
	public void setCodeName3(String codeName3) {
		this.codeName3 = codeName3;
	}
	public String getCode4() {
		return code4;
	}
	public void setCode4(String code4) {
		this.code4 = code4;
	}
	public String getCodeName4() {
		return codeName4;
	}
	public void setCodeName4(String codeName4) {
		this.codeName4 = codeName4;
	}
	
	
}
