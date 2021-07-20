package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 扣減參數參數檔 (<code>BAPACUT</code>)
 * 
 * @author Ocean Cheng
 */
@Table("BAPACUT")
public class Bapacut implements Serializable {
	@PkeyField("PAYCODE")
    private String payCode; 	// 給付別
    private String cutTyp; 	// 扣減項目代碼
    private	String paramName;   // 扣減項目名稱
    private String cutSeq; 		// 扣減項目順序
    private String updUser; 	// 異動者代號
    private String updTime; 	// 異動日期時間

    
    public Bapacut() {
    }
    
    public String getPayCode() {
        return payCode;
    }
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getCutSeq() {
        return cutSeq;
    }
    public void setCutSeq(String cutSeq) {
        this.cutSeq = cutSeq;
    }

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getCutTyp() {
		return cutTyp;
	}

	public void setCutTyp(String cutTyp) {
		this.cutTyp = cutTyp;
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

}

