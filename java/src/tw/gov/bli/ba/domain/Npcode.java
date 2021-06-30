package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 國保給付核定檔
 * 
 * @author Noctis
 */
@Table("NPCODE")
public class Npcode implements Serializable {
    private String codeNo;// 學校代碼
    private String codeName;// 學校名稱
    
    public String getCodeString() {
		return codeNo+" "+codeName;
	}
    
	public String getCodeNo() {
		return codeNo;
	}
	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
    
    
}
