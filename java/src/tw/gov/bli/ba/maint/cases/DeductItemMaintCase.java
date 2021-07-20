package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Case for 扣減參數維護作業
 * 
 * @author Ocean Cheng
 */
public class DeductItemMaintCase implements Serializable {
	private String payCode; 	// 給付別
    private String cutTyp; 	// 扣減項目代碼
    private	String paramName;   // 扣減項目名稱
    private String cutSeq; 		// 扣減項目順序
    private String cutTypName; 	// 扣減項目畫面資料處理
    private String updUser; 	// 異動者代號
    private String updTime; 	// 異動日期時間
    private List<DeductItemMaintCase> data;
    private String condition1; // 條件一


    private String[] condition1Array; // 條件一 Array

    public void setCondition1Array(String[] condition1Array) {
        this.condition1Array = condition1Array;
        condition1 = StringUtils.join(condition1Array, ",");
    }
   
    public void setCondition1(String condition1) {
        this.condition1 = condition1;
        condition1Array = StringUtils.split(condition1, ",");
    }

    /**
     * 建構子
     */
    public DeductItemMaintCase() {
    }

	public void setCutTypName(String cutTypName) {
		this.cutTypName = cutTypName;
	}

	public String getCutTypName() {
		return cutTyp + "-" + paramName;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getCutSeq() {
		return cutSeq;
	}

	public void setCutSeq(String cutSeq) {
		this.cutSeq = cutSeq;
	}

	public String getCutTyp() {
		return cutTyp;
	}

	public void setCutTyp(String cutTyp) {
		this.cutTyp = cutTyp;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public List<DeductItemMaintCase> getData() {
		return data;
	}

	public void setData(List<DeductItemMaintCase> data) {
		this.data = data;
	}

	
	
}
