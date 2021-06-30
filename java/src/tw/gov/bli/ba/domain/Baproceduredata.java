package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 資料庫處理程序明細檔(BAPROCEDUREDATA)
 * 
 * @author KIYOMI
 */
public class Baproceduredata implements Serializable {
    private String baJobId;
    private String procedureName;     // 程式名稱
    private String seqNo;
    private String parameterName;     // 參數名稱
    private String paramValue;        // 參數值
    private String creUser;
    private String creDatetime;
    private String inOut;
    private String dataType;    
 
    public String getBaJobId() {
        return baJobId;
    }

    public void setBaJobId(String baJobId) {
        this.baJobId = baJobId;
    }    
    
	public String getprocedureName() {
		return procedureName;
	}
	
	public void setprocedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	
    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }	
	
	public String getParamValue() {
		return paramValue;
	}
	
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
    
    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
    public String getCreUser() {
        return creUser;
    }

    public void setCreUser(String creUser) {
        this.creUser = creUser;
    }
    
    public String getCreDatetime() {
        return creDatetime;
    }

    public void setCreDatetime(String creDatetime) {
        this.creDatetime = creDatetime;
    }	

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }
    
    public String getdataType() {
        return dataType;
    }

    public void setdataType(String dataType) {
        this.dataType = dataType;
    }       
}
