package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 資料庫處理程序明細檔(BAPROCEDUREDTL)
 * 
 * @author KIYOMI
 */
public class Baproceduredtl implements Serializable {
    private String procedureName;     // 程式名稱
    private String parameterName;     // 參數名稱
    private String paramDesc;         // 參數說明
    private String dataType;    
    private BigDecimal dataLength;
    private String inOut;
    private String seqNo;
    private String creUser;
    private String creDatetime;
    
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
	
	public String getParamDesc() {
		return paramDesc;
	}
	
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public BigDecimal getDataLength() {
        return dataLength;
    }

    public void setDataLength(BigDecimal dataLength) {
        this.dataLength = dataLength;
    }
    
    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
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
    
}
